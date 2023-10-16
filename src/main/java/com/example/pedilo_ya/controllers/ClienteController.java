package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/cliente")
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping("/cliente")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente clienteDetails) {
        Optional<Cliente> user = clienteRepository.findByEmail(clienteDetails.getEmail());
        if (user.isEmpty()) {
            clienteRepository.save(clienteDetails);
            return new ResponseEntity<>("El usuario ha sido añadido correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> updateUser(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> userOptional = clienteRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = userOptional.get();
        Class<?> userClass = cliente.getClass();
        Class<?> userDetailsClass = clienteDetails.getClass();

        for (Field field : userClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Field userDetailsField = userDetailsClass.getDeclaredField(fieldName);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(clienteDetails);
                if (newValue != null && !newValue.equals(field.get(cliente))) {
                    field.set(cliente, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Cliente savedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> borrarCliente(@PathVariable Long id) {
        Optional<Cliente> user = clienteRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("El usuario no existe o ya ha sido borrado", HttpStatus.BAD_REQUEST);
        }
        clienteRepository.delete(user.get());
        return new ResponseEntity<>("El usuario ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
