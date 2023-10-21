// Carga de los detalles de productos para el pago

let productos = [];

document.addEventListener('DOMContentLoaded', function () {
    // Obtener los parametros de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const detallesCodificados = urlParams.get('detalles');
    const totalCodificado = urlParams.get('total');

    // Decodifica los detalles y el total
    const detallesProductos = JSON.parse(decodeURIComponent(detallesCodificado));
    const totalPagar = decodeURIComponent(totalCodificado);

    let contenedor = document.getElementById("detalle-producto");

    detallesProductos.forEach(pedido => {
        // Creamos el div que contiene cada producto
        let info = document.createElement("div");
        info.className = "info-producto";

        let img = document.createElement("img");
        img.src = pedido.imagen;

        let nombre = document.createElement("span");
        nombre.textContent = pedido.nombre;

        let cantidad = document.createElement("span");
        cantidad.textContent = pedido.cantidad;

        let precio = document.createElement("span");
        precio.textContent = pedido.precio;

        info.appendChild(img);
        info.appendChild(nombre);
        info.appendChild(precio);

        contenedor.appendChild(info);

        // Agrega el producto a la lista 'productos'
        productos.push({
            nombre: pedido.nombre,
            precio: pedido.precio,
            imagen: pedido.imagen
        });
    });

    const tipoEnvioSelect = document.getElementById("tipoEnvio");
    const tipoEnvioValor = tipoEnvioSelect.value;

    var descuento = 1;
    if (tipoEnvioValor === "Delivery") {
        const domicilio = document.getElementById("domicilio");
        domicilio.hidden = false;
        const botonEncargo = document.getElementById("botonEncargo");
        botonEncargo.hidden = false;
    } else {
        const botonMercadoPago = document.getElementById("botonMercadoPago");
        botonMercadoPago.hidden = false;
        let descuento = 0.9;
    }

    let total = document.createElement("div");
    // Si es delivery no pasa nada, si es retiro le hago el 10 de descuento
    total.textContent = parseFloat(totalPagar * parseFloat(descuento));
});

// Falta la logica del pago y el exito del mismo, que una vez que funcione se deberia enviar el pedido al restaurante

function enviarPedidoARestaurante() {
    // Obtengo todos los productos
    const productos = productos;

    var fecha = new Date();

    var año = fecha.getFullYear();
    var mes = (fecha.getMonth() + 1).toString().padStart(2, '0'); // Sumamos 1 al mes porque los meses comienzan desde 0
    var dia = fecha.getDate().toString().padStart(2, '0');
    var horas = fecha.getHours().toString().padStart(2, '0');
    var minutos = fecha.getMinutes().toString().padStart(2, '0');

    var fechaFormateada = `${año}/${mes}/${dia} ${horas}:${minutos}`;

    // Obtengo el email del cliente
    function getCookie(cookieName) {
        const cookies = document.cookie.split(';');
        for (const cookie of cookies) {
            const [name, value] = cookie.trim().split('=');
            if (name === cookieName) {
                return decodeURIComponent(value);
            }
        }
        return null;
    }

    const emailCliente = getCookie('email');

    // Obtengo el ID del negocio desde la URL
    const urlActual = window.location.href;
    const regex = /id\/(\d+)\/pago/;
    const idRestaurante = urlActual.match(regex)[1]; // [1] para obtener el grupo capturado

    const tipoEnvioSelect = document.getElementById("tipoEnvio");
    const tipoEnvioValor = tipoEnvioSelect.value;

    const pedido = {
        tipoEnvio: tipoEnvioValor,
        fecha: fechaFormateada,
        email: emailCliente,
        productos: productos
    };

    fetch("/restaurante/id/" + idRestaurante + "/pedido", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(pedido)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al enviar datos al restaurante (${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            // mostrar un mensaje de éxito
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

