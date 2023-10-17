package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import com.example.pedilo_ya.repositories.RestauranteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RestauranteController {
    private final RestauranteRepository restauranteRepository;

    public RestauranteController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    // Realiza un get completo de todas los restaurantes
    @GetMapping("/restaurante")
    public List<Restaurante> getRestaurantes() {
        return restauranteRepository.findAll();
    }

    // Busca por id de restaurante
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<Restaurante> getRestaurantePorId(@PathVariable Long id) {
        Optional<Restaurante> restauranteEncontrado = restauranteRepository.findById(id);
        if (restauranteEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurante restaurante = restauranteEncontrado.get();
        return ResponseEntity.ok(restaurante);
    }
    @PostMapping("/restaurante")
    public ResponseEntity<String> crearRestaurante(@RequestBody Restaurante restaurante) {
        Optional<Restaurante> rest = restauranteRepository.findById(restaurante.getId());
        if (rest.isEmpty()) {
            restauranteRepository.save(restaurante);
            return new ResponseEntity<>("La restaurante ha sido añadida correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La restaurante ya existe", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/restaurante/{comida}")
    public List<Restaurante> getRestaurantesPorTipoComida(@PathVariable String tipoComida) {
        List<Restaurante> restaurantesConTipoComida = new ArrayList<>();

        List<Restaurante> restaurantes = restauranteRepository.findAll();
        for (Restaurante rest : restaurantes) {
            String tipoComidas = rest.getTipoDeComida();
            // Quitar espacios y separar por cada tipo de comida
            tipoComidas = tipoComidas.replaceAll(" ", "");

            String[] comidas = tipoComidas.split(",");

            for (String comida : comidas) {
                if (comida.equals(tipoComida)) {
                    restaurantesConTipoComida.add(rest);
                }
            }
        }
        return restaurantesConTipoComida;
    }

    @PutMapping("/restaurante/{id}")
    public ResponseEntity<Restaurante> actualizarRestaurante(@PathVariable Long id, @RequestBody Restaurante rest) {
        Optional<Restaurante> restauranteEncontrado = restauranteRepository.findById(id);
        if (restauranteEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurante restaurante = restauranteEncontrado.get();
        Class<?> facturaClass = restaurante.getClass();
        Class<?> facturaDetailsClass = rest.getClass();

        for (Field field : facturaClass.getDeclaredFields()) {
            field.setAccessible(true);
            String nombre = field.getName();
            try {
                Field userDetailsField = facturaDetailsClass.getDeclaredField(nombre);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(rest);
                if (newValue != null && !newValue.equals(field.get(restaurante))) {
                    field.set(restaurante, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Restaurante restauranteFinal = restauranteRepository.save(restaurante);
        return ResponseEntity.ok(restauranteFinal);
    }

    @DeleteMapping("/restaurante/{id}")
    public ResponseEntity<?> borrarRestaurante(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if (restaurante.isEmpty()) {
            return new ResponseEntity<>("La restaurante ya ha sido borrada previamente", HttpStatus.BAD_REQUEST);
        }
        restauranteRepository.delete(restaurante.get());
        return new ResponseEntity<>("La restaurante ha sido correctamente", HttpStatus.ACCEPTED);
    }
}
