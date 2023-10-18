package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Restaurante.Empleado;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import com.example.pedilo_ya.repositories.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
public class EmpleadoController {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    // Realiza un get completo de todas los restaurantes
    @GetMapping("/empleado")
    public List<Empleado> getRestaurantes() {
        return empleadoRepository.findAll();
    }

    // Busca por id de empleado
    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> getEmpleadoPorId(@PathVariable Long id) {
        Optional<Empleado> empleadoEncontrado = empleadoRepository.findById(id);
        if (empleadoEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = empleadoEncontrado.get();
        return ResponseEntity.ok(empleado);
    }

    @PostMapping("/empleado")
    public ResponseEntity<String> crearEmpleado(@RequestBody Empleado empleado) {
        Optional<Empleado> emplead = empleadoRepository.findById(empleado.getId());
        if (emplead.isEmpty()) {
            empleadoRepository.save(empleado);
            return new ResponseEntity<>("La empleado ha sido añadida correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La empleado ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Restaurante emplead) {
        Optional<Empleado> empleadoEncontrado = empleadoRepository.findById(id);
        if (empleadoEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleado empleado = empleadoEncontrado.get();
        Class<?> empleadoClass = empleado.getClass();
        Class<?> empleadoDetailsClass = emplead.getClass();

        for (Field field : empleadoClass.getDeclaredFields()) {
            field.setAccessible(true);
            String nombre = field.getName();
            try {
                Field userDetailsField = empleadoDetailsClass.getDeclaredField(nombre);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(emplead);
                if (newValue != null && !newValue.equals(field.get(empleado))) {
                    field.set(empleado, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Empleado restauranteFinal = empleadoRepository.save(empleado);
        return ResponseEntity.ok(restauranteFinal);
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> borrarEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isEmpty()) {
            return new ResponseEntity<>("La empleado ya ha sido borrada previamente", HttpStatus.BAD_REQUEST);
        }
        empleadoRepository.delete(empleado.get());
        return new ResponseEntity<>("La empleado ha sido correctamente", HttpStatus.ACCEPTED);
    }
}
