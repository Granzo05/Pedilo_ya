//node server.js
const express = require('express');
const app = express();
const path = require('path'); // Importa el módulo path
const port = 3000;

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

app.get('/login/js/scriptLoginCliente.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptLoginCliente.js'));
});

app.get('/login/js/scriptLoginNegocio.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptLoginCliente.js'));
});

app.get('/js/scripCargaRestaurantes.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scripCargaRestaurantes.js'));
});

app.get('/menu/js/scriptMainMenu.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptMainMenu.js'));
});

app.get('/pago/js/scriptPago.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptPago.js'));
});

app.get('/pedidos/js/scriptPedidosRealizados.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptPedidosRealizados.js'));
});

app.get('/pedidos/js/scriptCookies.js', (req, res) => {
    res.set('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '/js/scriptCookies.js'));
});


// HTML

app.get('/login/cliente', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/login/loginCliente.html'));
});

app.get('/login/negocio', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/login/loginNegocio.html'));
});

app.get('/empanadas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/empanadas.html'));
});

app.get('/hamburguesas', (req, res) => {
    res.sendFile(path.join('html/restaurantePorComida/hamburguesas.html'));
});

app.get('/lomos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/lomos.html'));
});

app.get('/panchos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/panchos.html'));
});

app.get('/parrilla', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/parrilla.html'));
});

app.get('/pastas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/pastas.html'));
});

app.get('/pizzas', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/pizzas.html'));
});

app.get('/sanguches', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/sanguches.html'));
});

app.get('/sushi', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/sushi.html'));
});

app.get('/vegetariano', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/restaurantePorComida/vegetariano.html'));
});

app.get('/accesoDenegado', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/accesoDenegado.html'));
});

app.get('/menu', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/mainMenu.html'));
});

app.get('/pago', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/pago.html'));
});

app.get('/pedidos', (req, res) => {
    res.sendFile(path.join(__dirname, 'html/pedidosRealizados.html'));
});

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
        fs.writeFile(__dirname + "html/restaurantes/${id}.html", data, (err) => {
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
    const rutaArchivo = path.join(__dirname, `html/restaurantes/${restauranteId}.html`);

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

