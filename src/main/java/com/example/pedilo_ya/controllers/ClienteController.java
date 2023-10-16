package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    ArrayList<Cliente> clientes = new ArrayList();
    private final UserRepository userRepository;
    public ClienteController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public List<Cliente> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody Cliente clienteDetails){
        Optional<Cliente> user = userRepository.findByEmail(clienteDetails.getEmail());
        if(!user.isPresent()){
            userRepository.save(clienteDetails);
            return new ResponseEntity<>("El usuario ha sido añadido correctamente", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Cliente> updateUser(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
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
        Cliente savedCliente = userRepository.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<Cliente> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("El usuario no existe o ya ha sido borrado", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user.get());
        return new ResponseEntity<>("El usuario ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
