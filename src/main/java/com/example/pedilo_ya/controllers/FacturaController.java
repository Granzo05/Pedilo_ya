package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Pedidos.DetallesPedido;
import com.example.pedilo_ya.entities.Pedidos.Pedido;
import com.example.pedilo_ya.repositories.FacturaRepository;
import com.example.pedilo_ya.repositories.PedidoRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@RestController
public class FacturaController {
    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;

    public FacturaController(FacturaRepository facturaRepository,
                             PedidoRepository pedidoRepository) {
        this.facturaRepository = facturaRepository;
        this.pedidoRepository = pedidoRepository;
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

    // Enviar Factura asociada al pedido como pdf
    @GetMapping("/factura/pedido/{idPedido}/pdf")
    public ResponseEntity<byte[]> generarFacturaPDF(@PathVariable Long idCliente, @PathVariable Long idPedido) {
        // Lógica para obtener el pedido y su factura desde la base de datos
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        // Crear un nuevo documento PDF
        Document document = new Document();

        // Crear un flujo de bytes para almacenar el PDF generado
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            double total = 0;
            // Agregar contenido al PDF
            document.add(new Paragraph("Factura del Pedido"));
            document.add(new Paragraph("Tipo: " + pedido.getFactura().getTipoFactura()));
            document.add(new Paragraph("Fecha: " + pedido.getFactura().getFecha()));
            document.add(new Paragraph("Cliente: " + pedido.getFactura().getCliente().getNombre() + " " + pedido.getFactura().getCliente().getApellido()));
            document.add(new Paragraph("Email: " + pedido.getFactura().getEmail()));
            document.add(new Paragraph("Domicilio del restaurante: " + pedido.getFactura().getFecha()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Detalles de la factura"));

            for (DetallesPedido facturaDetalle: pedido.getFactura().getDetallesPedido()) {
                document.add(new Paragraph("Menu: " + facturaDetalle.getMenu()));
                document.add(new Paragraph("Cantidad: " + facturaDetalle.getCantidad()));
                document.add(new Paragraph("Subtotal: " + facturaDetalle.getSubTotal()));
                total += facturaDetalle.getSubTotal();
            }
            document.add(new Paragraph("Total: " + total));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Obtener los bytes del PDF generado
        byte[] pdfBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
