package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Restaurante.Menu.Ingrediente;
import com.example.pedilo_ya.entities.Restaurante.Menu.IngredienteMenu;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.entities.Restaurante.Menu.Stock;
import com.example.pedilo_ya.repositories.RestauranteRepository;
import com.example.pedilo_ya.repositories.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StockController {
    private final RestauranteRepository restauranteRepository;
    private final StockRepository stockRepository;

    public StockController(RestauranteRepository restauranteRepository,
                           StockRepository stockRepository) {
        this.restauranteRepository = restauranteRepository;
        this.stockRepository = stockRepository;
    }

    // Busca stock id de restaurante
    @GetMapping("/restaurante/id/{id}/stock")
    public ResponseEntity<Stock> getStockRestaurantePorId(@PathVariable Long id) {
        Optional<Stock> stockEncontrado = stockRepository.findByIdRestaurante(id);
        if (stockEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Stock stock = stockEncontrado.get();
        return ResponseEntity.ok(stock);
    }

    // Busca stock id de restaurante y nombre del producto
    @GetMapping("/restaurante/id/{id}/stock/{nombre}")
    public ResponseEntity<Stock> getStockRestaurantePorIdYNombre(@PathVariable Long id, @PathVariable String nombre) {
        Optional<Stock> stockEncontrado = stockRepository.findByNombreProductoYRestauranteId(nombre, id);
        if (stockEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Stock stock = stockEncontrado.get();
        return ResponseEntity.ok(stock);
    }

    // Busca stock id de restaurante y nombre del producto
    @GetMapping("/restaurante/id/{id}/stock/check")
    public ResponseEntity<String> getStockRestaurantePorIdYNombre(@PathVariable Long id, @RequestBody List<Menu> menus) {
        for (Menu menu : menus) {
            for (IngredienteMenu ingrediente : menu.getIngredientes()) {
                Optional<Stock> stockEncontrado = stockRepository.findByNombreProductoYRestauranteId(ingrediente.getNombre(), id);

                if (stockEncontrado.isPresent() && stockEncontrado.get().getCantidad() < ingrediente.getCantidad()) {
                    // Si es menor solo devuelve los menus que puede producir junto con un error
                    return new ResponseEntity<>("El stock no es suficiente", HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>("El stock es suficiente", HttpStatus.CREATED);
    }


    @PostMapping("/restaurante/id/{id}/stock/carga")
    public ResponseEntity<String> crearStock(@PathVariable long id,
                                             @RequestParam("nombre") String nombre,
                                             @RequestParam("cantidad") int cantidad,
                                             @RequestParam("medida") String medida,
                                             @RequestParam("costo") double costo) {
        Optional<Stock> stockEncontrado = stockRepository.findByNombreProductoYRestauranteId(nombre, id);
        // Si no existe stock de ese producto y restaurante se crea
        if (stockEncontrado.isEmpty()) {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNombre(nombre);
            ingrediente.setCosto(costo);

            Stock stock = new Stock();
            stock.setIdRestaurante(id);
            stock.setCantidad(cantidad);
            stock.setMedida(medida);
            stock.setIngrediente(ingrediente);

            stockRepository.save(stock);
            return new ResponseEntity<>("El stock ha sido añadido correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El stock ya existía", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/restaurante/id/{id}/stock/update")
    public ResponseEntity<Stock> actualizarStock(@PathVariable Long id, @RequestBody Stock stock) {
        Optional<Stock> stockEncontrado = stockRepository.findByNombreProductoYRestauranteId(stock.getIngrediente().getNombre(), id);
        if (stockEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Stock stockFinal = stockRepository.save(stockEncontrado.get());
        return ResponseEntity.ok(stockFinal);
    }

    @DeleteMapping("/restaurante/id/{id}/stock/delete/{nombre}")
    public ResponseEntity<?> borrarStock(@PathVariable Long id, @PathVariable String nombre) {
        Optional<Stock> stockEncontrado = stockRepository.findByNombreProductoYRestauranteId(nombre, id);
        if (stockEncontrado.isEmpty()) {
            return new ResponseEntity<>("El stock ya ha sido borrado previamente", HttpStatus.BAD_REQUEST);
        }
        stockRepository.delete(stockEncontrado.get());
        return new ResponseEntity<>("El stock ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
