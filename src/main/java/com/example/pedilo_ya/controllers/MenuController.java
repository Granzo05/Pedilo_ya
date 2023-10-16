package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.repositories.MenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> crearMenu(@RequestBody Menu menu) {
        Optional<Menu> rest = menuRepository.findById(menu.getId());
        if (rest.isEmpty()) {
            menuRepository.save(menu);
            return new ResponseEntity<>("La menu ha sido añadida correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La menu ya existe", HttpStatus.BAD_REQUEST);
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
