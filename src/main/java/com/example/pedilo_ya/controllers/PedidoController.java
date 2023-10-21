package com.example.pedilo_ya.controllers;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Cliente.PedidoCliente;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.DetalleFactura;
import com.example.pedilo_ya.entities.Pedidos.DetallesPedido;
import com.example.pedilo_ya.entities.Pedidos.EnumTipoEnvio;
import com.example.pedilo_ya.entities.Pedidos.Pedido;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import com.example.pedilo_ya.repositories.ClienteRepository;
import com.example.pedilo_ya.repositories.DetallesPedidoRepository;
import com.example.pedilo_ya.repositories.PedidoRepository;
import com.example.pedilo_ya.repositories.RestauranteRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PedidoController {
    private final PedidoRepository pedidoRepository;
    private final DetallesPedidoRepository detallesPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoController(PedidoRepository pedidoRepository, DetallesPedidoRepository detallesPedidoRepository,
                            ClienteRepository clienteRepository,
                            RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallesPedidoRepository = detallesPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
    }
    @GetMapping("/cliente/id/{idCliente}/pedidos")
    public List<Pedido> getPedidosPorCliente(@PathVariable Long idCliente) {
        List<Pedido> pedidos = pedidoRepository.findByIdCliente(idCliente);
        return pedidos;
    }

    @GetMapping("/restaurante/id/{idNegocio}/pedidos")
    public List<Pedido> getPedidosPorNegocio(@PathVariable Long idNegocio) {
        List<Pedido> pedidos = pedidoRepository.findByIdNegocio(idNegocio);
        return pedidos;
    }
    //Funcion para cargar pdfs
    @GetMapping("/pedido/{idPedido}/pdf")
    public ResponseEntity<byte[]> generarPedidoPDF(@PathVariable Long idCliente, @PathVariable Long idPedido) {
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
            document.add(new Paragraph("Información del Pedido"));
            document.add(new Paragraph("Fecha: " + pedido.getFechaPedido()));
            document.add(new Paragraph("Domicilio: " + pedido.getDomicilio()));
            document.add(new Paragraph("Tipo de Envío: " + pedido.getTipoEnvio()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Detalles de la factura"));

            for (DetallesPedido detalle: detallesPedidoRepository.findByIdPedido(pedido.getId())) {
                document.add(new Paragraph("Menu: " + detalle.getMenu()));
                document.add(new Paragraph("Cantidad: " + detalle.getCantidad()));
                document.add(new Paragraph("Subtotal: " + detalle.getSubTotal()));
                total += detalle.getSubTotal();
            }
            document.add(new Paragraph("Total: " + total));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Obtener los bytes del PDF generado
        byte[] pdfBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=pedido.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
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

            // Agregar contenido al PDF
            document.add(new Paragraph("Factura del Pedido"));
            document.add(new Paragraph("Tipo: " + pedido.getFactura().getTipoFactura()));
            document.add(new Paragraph("Fecha: " + pedido.getFactura().getFecha()));
            document.add(new Paragraph("Cliente: " + pedido.getFactura().getCliente().getNombre() + " " + pedido.getFactura().getCliente().getApellido()));
            document.add(new Paragraph("Email: " + pedido.getFactura().getEmail()));
            document.add(new Paragraph("Domicilio: " + pedido.getFactura().getFecha()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Detalles de la factura"));
            for (DetalleFactura facturaDetalle: pedido.getFactura().getDetallesFactura()) {
                document.add(new Paragraph("Detalles: " + facturaDetalle.getDetalles()));
            }
            // Aquí puedes agregar más información del pedido y la factura

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
    @PostMapping("/restaurante/id/{id}/pedido")
    public ResponseEntity<String> crearPedido(@PathVariable Long id, @RequestBody PedidoCliente pedidoRequest) {
        List<Menu> menus = pedidoRequest.getMenus();
        String emailCliente = pedidoRequest.getEmail();
        Date fecha = pedidoRequest.getFecha();
        EnumTipoEnvio tipoEnvio = pedidoRequest.getTipoEnvio();

        // Buscar si el cliente existe
        Optional<Cliente> cliente = clienteRepository.findByEmail(emailCliente);

        if (cliente.isEmpty()) {
            return new ResponseEntity<>("La cliente no está registrado", HttpStatus.BAD_REQUEST);
        }

        Cliente clienteFinal = new Cliente(cliente.get().getId(), cliente.get().getNombre(), cliente.get().getApellido(), cliente.get().getDomicilio());

        // Buscar si el restaurante existe
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        if (restaurante.isEmpty()) {
            return new ResponseEntity<>("La restaurante no está registrado", HttpStatus.BAD_REQUEST);
        }

        Restaurante restauranteFinal = new Restaurante(restaurante.get().getId(), restaurante.get().getDomicilio(), restaurante.get().getNombre(), restaurante.get().getTelefono());

        // Vemos los detalles

        List<DetallesPedido> detalles = new ArrayList<>();

        for (Menu menu: menus){
            DetallesPedido detalle = new DetallesPedido();
            detalle.setMenu(menu);
            detalles.add(detalle);
        }

        Pedido pedido = new Pedido();
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setFechaPedido(fecha);
        pedido.setRestaurante(restauranteFinal);
        pedido.setTelefono(cliente.get().getTelefono());
        pedido.setCliente(clienteFinal);
        pedido.setDetallesPedido(detalles);


        pedidoRepository.save(pedido);
        return new ResponseEntity<>("La pedido ha sido cargado correctamente", HttpStatus.CREATED);
    }
    @PutMapping("/cliente/pedido/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        Optional<Pedido> pedidoEncontrada = pedidoRepository.findById(id);
        if (pedidoEncontrada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pedido pedido = pedidoEncontrada.get();
        Class<?> pedidoClass = pedido.getClass();
        Class<?> pedidoDetailsClass = pedidoDetails.getClass();

        for (Field field : pedidoClass.getDeclaredFields()) {
            field.setAccessible(true);
            String nombre = field.getName();
            try {
                Field userDetailsField = pedidoDetailsClass.getDeclaredField(nombre);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(pedidoDetails);
                if (newValue != null && !newValue.equals(field.get(pedido))) {
                    field.set(pedido, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        Pedido pedidoFinal = pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedidoFinal);
    }

    @DeleteMapping("/cliente/pedido/{id}")
    public ResponseEntity<?> borrarPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return new ResponseEntity<>("La pedido ya ha sido borrada previamente", HttpStatus.BAD_REQUEST);
        }
        pedidoRepository.delete(pedido.get());
        return new ResponseEntity<>("La pedido ha sido correctamente", HttpStatus.ACCEPTED);
    }
}
