package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.controllers.EncryptMD5.Encrypt;
import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import com.example.pedilo_ya.repositories.RestauranteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private static String extractBase64Data(String base64Image) {
        // Extrae los datos base64 de la cadena (ignorando el encabezado 'data:image/png;base64,')
        int commaIndex = base64Image.indexOf(",");
        return base64Image.substring(commaIndex + 1);
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
    public ResponseEntity<String> crearRestaurante(@RequestParam("file") MultipartFile file, @RequestParam("nombre") String nombre, @RequestParam("email") String email, @RequestParam("contraseña") String contraseña, @RequestParam("domicilio") String domicilio, @RequestParam("telefono") long telefono) throws IOException {
        Optional<Restaurante> rest = restauranteRepository.findByEmail(email);
        if (rest.isEmpty()) {
            Restaurante restauranteDetails = new Restaurante();
            restauranteDetails.setNombre(nombre);
            restauranteDetails.setEmail(email);
            restauranteDetails.setContraseña(Encrypt.encryptPassword(contraseña));
            restauranteDetails.setDomicilio(domicilio);
            restauranteDetails.setTelefono(telefono);
            restauranteDetails.setImagen(file.getBytes());

            restauranteRepository.save(restauranteDetails);
            return new ResponseEntity<>("El restaurante ha sido añadido correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al registrar el restaurante: el correo electrónico ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/restaurante/login")
    public ResponseEntity<Restaurante> buscarRestaurante(@RequestBody Restaurante restauranteDetails) {
        Optional<Restaurante> restauranteOptional = restauranteRepository.findByEmailAndPassword(restauranteDetails.getEmail(), Encrypt.encryptPassword(restauranteDetails.getContraseña()));
        if (restauranteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Restaurante restaurante = restauranteOptional.get();
        return ResponseEntity.ok(restaurante);
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
