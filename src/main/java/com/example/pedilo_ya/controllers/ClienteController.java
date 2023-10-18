package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.controllers.EncryptMD5.Encrypt;
import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import com.example.pedilo_ya.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Optional;

@RestController
public class ClienteController {
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/cliente")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(clienteDetails.getEmail());
        if (cliente.isEmpty()) {
            clienteDetails.setContraseña(Encrypt.encryptPassword(clienteDetails.getContraseña()));
            clienteRepository.save(clienteDetails);
            return new ResponseEntity<>("El usuario ha sido añadido correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/cliente/login")
    public ResponseEntity<Cliente> buscarCliente(@RequestBody Cliente clienteDetails) {
        // Recibo un email y una password desde el cliente, esa pass la encripto para ver si coincide con la guardada
        Optional<Cliente> clienteOptional = clienteRepository.findByEmailAndPassword(clienteDetails.getEmail(), Encrypt.encryptPassword(clienteDetails.getContraseña()));
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        Class<?> clienteClass = cliente.getClass();
        Class<?> clienteDetailsClass = clienteDetails.getClass();

        for (Field field : clienteClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Field clienteDetailsField = clienteDetailsClass.getDeclaredField(fieldName);
                clienteDetailsField.setAccessible(true);
                Object newValue = clienteDetailsField.get(clienteDetails);
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
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity<>("El usuario no existe o ya ha sido borrado", HttpStatus.BAD_REQUEST);
        }
        clienteRepository.delete(cliente.get());
        return new ResponseEntity<>("El usuario ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
