package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Restaurante.Menu.EnumTipoMenu;
import com.example.pedilo_ya.entities.Restaurante.Menu.Ingrediente;
import com.example.pedilo_ya.entities.Restaurante.Menu.IngredienteMenu;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.repositories.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@RestController
public class MenuController {
    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Busca por id de menu
    @GetMapping("/restaurante/{id}/menu")
    public List<Menu> getMenusPorIdRestaurante(@PathVariable Long id) {
        return menuRepository.findByIdRestaurante(id);
    }

    @PostMapping("/restaurante/menu")
    public ResponseEntity<String> crearMenu(@RequestParam("file") MultipartFile file,
                                            @RequestParam("nombre") String nombre,
                                            @RequestParam("tiempoCoccion") int tiempoCoccion,
                                            @RequestParam("tipo") String tipoMenu,
                                            @RequestParam("comensales") int comensales,
                                            @RequestParam("precio") double precio,
                                            @RequestParam("ingredientes") List<IngredienteMenu> ingredientes) throws IOException {
        Optional<Menu> menu = menuRepository.findByName(nombre);
        if (menu.isEmpty()) {
            Menu menuDetails = new Menu();
            menuDetails.setNombre(nombre);
            menuDetails.setTiempoCoccion(tiempoCoccion);
            menuDetails.setTipo(EnumTipoMenu.valueOf(tipoMenu));
            menuDetails.setComensales(comensales);
            menuDetails.setPrecio(precio);

            List<IngredienteMenu> ingredientesMenu = new ArrayList<>();

            for (IngredienteMenu ingrediente : ingredientes) {
                IngredienteMenu ingr = new IngredienteMenu();
                ingr.setNombre(ingrediente.getNombre());
                ingr.setCantidad(ingrediente.getCantidad());
                ingredientesMenu.add(ingr);
            }

            menuDetails.setIngredientes(ingredientesMenu);

            // Separo la imagen en bytes
            menuDetails.setImagen(file.getBytes());

            menuRepository.save(menuDetails);
            return new ResponseEntity<>("El restaurante ha sido añadido correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al registrar el restaurante: el correo electrónico ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/restaurante/menu/{id}")
    public ResponseEntity<Menu> actualizarMenu(@PathVariable Long id, @RequestBody Menu rest) {
        Optional<Menu> menuEncontrado = menuRepository.findById(id);
        if (menuEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Menu menu = menuEncontrado.get();
        Class<?> menuClass = menu.getClass();
        Class<?> menuDetailClass = rest.getClass();

        for (Field field : menuClass.getDeclaredFields()) {
            field.setAccessible(true);
            String nombre = field.getName();
            try {
                Field userDetailsField = menuDetailClass.getDeclaredField(nombre);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(rest);
                if (newValue != null && !newValue.equals(field.get(menu))) {
                    field.set(menu, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Menu menuFinal = menuRepository.save(menu);
        return ResponseEntity.ok(menuFinal);
    }

    @DeleteMapping("/restaurante/menu/{id}")
    public ResponseEntity<?> borrarMenu(@PathVariable Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isEmpty()) {
            return new ResponseEntity<>("El menu ya ha sido borrado previamente", HttpStatus.BAD_REQUEST);
        }
        menuRepository.delete(menu.get());
        return new ResponseEntity<>("El menu ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
