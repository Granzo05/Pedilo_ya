function cargarPedidos(idRestaurante) {
    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + "/pedidos/entrantes", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            let contenedorPrincipal = document.getElementById("pedidos");

            data.forEach(pedido => {
                let contenedor = document.createElement("div");
                contenedor.className = "grid-item";

                let tipoEnvio = document.createElement("h3");
                tipoEnvio.textContent = pedido.tipoEnvio;
                contenedor.appendChild(tipoEnvio);

                // Si hubo envido el domicilio deberia estar, si fue retiro en tienda no
                if (pedido.domicilio != null) {
                    let domicilio = document.createElement("h3");
                    domicilio.textContent = pedido.domicilio;
                    contenedor.appendChild(domicilio);
                }

                pedido.detalles.forEach(detalle => {
                    let menu = document.createElement("p");
                    menu.textContent = detalle.menu;
                    contenedor.appendChild(menu);

                    let cantidad = document.createElement("p");
                    cantidad.textContent = detalle.cantidad;
                    contenedor.appendChild(cantidad);
                });

                let buttonAceptar = document.createElement("button");
                buttonOk.onclick = function () {
                    aceptarPedido(idRestaurante, pedido.cliente.email);
                }

                let buttonRechazar = document.createElement("button");
                buttonOk.onclick = function () {
                    // Validar que haya un motivo
                    let motivoRechazo = document.createElement("input");

                    rechazarPedido(pedido.id, pedido.cliente.email, motivoRechazo.value);
                }

                contenedorPrincipal.appendChild(contenedor);
            });

        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const nodemailer = require('nodemailer');
const transporter = nodemailer.createTransport({
    service: 'Gmail',
    auth: {
        user: "pediloyaContact@gmail.com",
        pass: 'pediloya123456789'
    }
});

function aceptarPedido(idPedido, idRestaurante, emailCliente) {
    let formData = {
        restaurante: idRestaurante,
        estadoPedido: "aceptado"
    }

    fetch('http://localhost:8080/pedido/update/' + idPedido, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            enviarCorreoExitoso(emailCliente);
            fetch('http://localhost:8080/pedido/update/' + idPedido, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
                    }
                    enviarCorreoExitoso(emailCliente);

                })
                .catch(error => {
                    console.error('Error:', error);
                });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Función para rechazar el pedido
function rechazarPedido(idPedido, emailCliente, motivoRechazo) {
    enviarCorreoRechazo(emailCliente, motivoRechazo);

    fetch('http://localhost:8080/pedido/delete/' + idPedido, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Función para enviar un correo electrónico de rechazo
function enviarCorreoRechazo(emailCliente, motivoRechazo) {
    const mailOptions = {
        from: "pediloyaContact@gmail.com",
        to: emailCliente,
        subject: 'Rechazo de Pedido',
        text: `Lamentablemente, su pedido ha sido rechazado. \nMotivo: ${motivoRechazo}`
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.error('Error al enviar el correo:', error);
        } else {
            console.log('Correo enviado: ' + info.response);
        }
    });
}

// Función para enviar un correo electrónico de rechazo
function enviarCorreoExitoso(emailCliente) {
    const mailOptions = {
        from: "pediloyaContact@gmail.com",
        to: emailCliente,
        subject: 'Pedido aceptado',
        text: `El restaurante aceptó el pedido`
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.error('Error al enviar el correo:', error);
        } else {
            console.log('Correo enviado: ' + info.response);
        }
    });
}

