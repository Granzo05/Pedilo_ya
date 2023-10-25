//node server.js
const express = require('express');
const app = express();
const path = require('path');
const port = 3000;
const mercadopago = require("mercadopago");

// Define la carpeta raíz para servir archivos estáticos
app.use(express.static(path.join(__dirname)));

// CSS

app.get('/css/styleLogin.css', (req, res) => {
    res.sendFile(path.join(__dirname, '/css/styleLogin.css'));
});

app.get('/css/styleMainMenu.css', (req, res) => {
    res.sendFile(path.join(__dirname, '/css/styleMainMenu.css'));
});

app.get('/css/styleNegocio.css', (req, res) => {
    res.sendFile(path.join(__dirname, '/css/styleNegocio.css'));
});

// JS

app.get('/js/scriptLoginCliente.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/cliente/scriptLoginCliente.js'));
});

app.get('/js/scriptLoginNegocio.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/restaurante/scriptLoginNegocio.js'));
});

app.get('/js/scriptCargaRestaurantes.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(__dirname, '/js/restaurante/scriptCargaRestaurantes.js');
});

app.get('/js/scriptMainMenu.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/cliente/scriptMainMenu.js'));
});

app.get('/js/scriptPago.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptPago.js'));
});

app.get('/js/scriptPedidosRealizadosCliente.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join('/js/scriptPedidosRealizadosCliente.js'));
});

app.get('/js/scriptPedidosRealizadosNegocio.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/cliente/scriptPedidosRealizadosNegocio.js'));
});

app.get('/js/scriptPedidosEntrantes.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/restaurante/scriptPedidosEntrantes.js'));
});

app.get('/js/scriptPedido.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/pedidos/scriptPedido.js'));
});

app.get('/js/scriptPedidosRecibidosCocina.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/restaurante/scriptPedidosRecibidosCocina.js'));
});

app.get('/js/scriptCookie.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/cookies/scriptCookie.js'));
});

app.get('/js/scriptDescargaPDF.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/pdfs/scriptDescargaPDF.js'));
});

app.get('/js/scriptPedidosRecibidosNegocio.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/restaurante/scriptPedidosRecibidosNegocio.js'));
});

app.get('js/scriptStock.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/restaurante/scriptStock.js'));
});

// HTML

app.get('/login/cliente', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/login/loginCliente.html'));
});

app.get('/login/negocio', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/login/loginNegocio.html'));
});

app.get('/empanadas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/empanadas.html'));
});

app.get('/hamburguesas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/hamburguesas.html'));
});

app.get('/lomos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/lomos.html'));
});

app.get('/panchos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/panchos.html'));
});

app.get('/parrilla', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/parrilla.html'));
});

app.get('/pastas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/pastas.html'));
});

app.get('/pizzas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/pizzas.html'));
});

app.get('/sanguches', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/sanguches.html'));
});

app.get('/sushi', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/sushi.html'));
});

app.get('/vegetariano', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantesPorCategoriaComida/vegetariano.html'));
});

app.get('/accesoDenegado', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/accesoDenegado.html'));
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/clientes/mainMenu.html'));
});

app.get('/menu', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/clientes/mainMenu.html'));
});

app.get('/restaurante/id/:id/stock', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurante/stock.html'));
});

app.get('/restaurante/id/:id/pago', (req, res) => {
    const rutaArchivo = path.join(__dirname, `html/clientes/pago.html`);

    fs.access(rutaArchivo, fs.constants.R_OK, (err) => {
        if (err) {
            res.status(404).send('Página de pago no encontrada para este restaurante');
        } else {
            res.sendFile(rutaArchivo);
        }
    });
});

app.get('cliente/id/:id/pedidos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/clientes/pedidosRealizadosCliente.html'));
});

app.get('restaurante/id/:id/pedidos/entrantes', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurante/pedidosEntrantes.html'));
});

app.get('restaurante/id/:id/cocina/', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurante/cocina.html'));
});

app.get('restaurante/id/:id/pedidos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurante/pedidosRecibidosNegocio.html'));
});

// Creacion de paginas para cada negocio que se registra

const fs = require('fs');
const cheerio = require('cheerio');

app.post('/crear-pagina/:id', (req, res) => {
    const id = req.params.id;

    fs.readFile('html/plantillaNegocios.html', 'utf8', (err, data) => {
        if (err) {
            console.error('Error al cargar la plantilla:', err);
            return res.status(500).send('Error al cargar la plantilla');
        }

        // Ruta final del restaurante
        fs.writeFile(__dirname + "/html/restaurantesAutomaticos/" + id + ".html", data, (err) => {
            if (err) {
                console.error('Error al crear la página:', err);
                return res.status(500).send('Error al crear la página');
            }
            res.status(200).send('Página creada exitosamente');
        });
    });
});

app.get('/restaurante/id/:id', (req, res) => {
    const restauranteId = req.params.id;
    const rutaArchivo = path.join(__dirname, `/html/restaurantesAutomaticos/${restauranteId}.html`);

    fs.access(rutaArchivo, fs.constants.R_OK, (err) => {
        if (err) {
            // Manejar errores, por ejemplo, enviar una página de error 404
            res.status(404).send('Página no encontrada');
        } else {
            res.sendFile(rutaArchivo);
        }
    });
});

app.listen(port, () => {
    console.log(`Servidor corriendo en http://localhost:${port}`);
});

app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Algo salió mal en el servidor');
});

// MERCADOPAGO
/*
mercadopago.configure({
    access_token: 'TOKEN PRIVADO'
});


app.post('/mercadopago', (req, res) => {
    const productos = req.body;

    let items = [];

    productos.forEach(producto => {
        const nombre = producto.nombre;
        const precio = producto.precio;
        const cantidad = producto.cantidad;
        const imagen = producto.imagen;

        items.push({
            title: nombre,
            currency_id: 'BRL',
            picture_url: imagen,
            quantity: cantidad,
            unit_price: precio
        });
    });

    let preference = {
        items: items,
        back_urls: {
            success: 'https://www.success.com',
            failure: 'http://www.failure.com',
        },
        auto_return: 'approved',
        payment_methods: {
            excluded_payment_methods: [],
            excluded_payment_types: [],
            installments: 1
        }
    };

    mercadopago.preferences
        .create(preference)
        .then(function (response) {
            global.id = response.body.id;
        })
        .catch(function (error) {
            console.log(error);
        });

    res.status(200).send('OK');
});
*/