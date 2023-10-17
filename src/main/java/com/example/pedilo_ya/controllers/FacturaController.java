package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.repositories.FacturaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
public class FacturaController {
    private final FacturaRepository facturaRepository;

    public FacturaController(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    // Realiza un get completo de todas las facturas
    @GetMapping("/cliente/factura")
    public List<Factura> getFacturas() {
        return facturaRepository.findAll();
    }

    // Busca por id de factura
    @GetMapping("/cliente/factura/{id}")
    public ResponseEntity<Factura> getFacturaPorId(@PathVariable Long id) {
        Optional<Factura> facturaEncontrada = facturaRepository.findById(id);
        if (facturaEncontrada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Factura factura = facturaEncontrada.get();
        return ResponseEntity.ok(factura);
    }

    // Busca las facturas asociadas a un id de cliente
    @GetMapping("/cliente/factura/{idCliente}")
    public List<Factura> getFacturasIdCliente(@PathVariable Long id) {
        return facturaRepository.findByIdCliente(id);
    }

    @PostMapping("/cliente/factura")
    public ResponseEntity<String> crearFactura(@RequestBody Factura facturaDetails) {
        Optional<Factura> user = facturaRepository.findById(facturaDetails.getId());
        if (user.isEmpty()) {
            facturaRepository.save(facturaDetails);
            return new ResponseEntity<>("La factura ha sido añadida correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("La factura ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cliente/factura/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Long id, @RequestBody Factura facturaDetails) {
        Optional<Factura> facturaEncontrada = facturaRepository.findById(id);
        if (facturaEncontrada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Factura factura = facturaEncontrada.get();
        Class<?> facturaClass = factura.getClass();
        Class<?> facturaDetailsClass = facturaDetails.getClass();

        for (Field field : facturaClass.getDeclaredFields()) {
            field.setAccessible(true);
            String nombre = field.getName();
            try {
                Field userDetailsField = facturaDetailsClass.getDeclaredField(nombre);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(facturaDetails);
                if (newValue != null && !newValue.equals(field.get(factura))) {
                    field.set(factura, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Factura facturaFinal = facturaRepository.save(factura);
        return ResponseEntity.ok(facturaFinal);
    }

    @DeleteMapping("/cliente/factura/{id}")
    public ResponseEntity<?> borrarFactura(@PathVariable Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        if (factura.isEmpty()) {
            return new ResponseEntity<>("La factura ya ha sido borrada previamente", HttpStatus.BAD_REQUEST);
        }
        facturaRepository.delete(factura.get());
        return new ResponseEntity<>("La factura ha sido correctamente", HttpStatus.ACCEPTED);
    }
}
