
const btnIniciarConCuenta = document.getElementById("iniciarSesionDatos"),
    btnRegistrarDatos = document.getElementById("registrarDatos"),
    formRegistrar = document.querySelector(".registrar"),
    formIniciar = document.querySelector(".iniciar");

btnRegistrarDatos.addEventListener("click", e => {
    formIniciar.classList.add("ocultar");
    formRegistrar.classList.remove("ocultar")
})

btnIniciarConCuenta.addEventListener("click", e => {
    formRegistrar.classList.add("ocultar");
    formIniciar.classList.remove("ocultar")
})

function cargarNegocio() {
    const nombreInput = document.getElementById("nombreRegistracion");
    const emailInput = document.getElementById("emailRegistracion");
    const contraseñaInput = document.getElementById("contraseñaRegistracion");
    const domicilioInput = document.getElementById("domicilioRegistracion");
    const telefonoInput = document.getElementById("telefonoRegistracion");
    const imagenInput = document.getElementById("imagenRegistracion");

    const formData = new FormData();
    formData.append("nombre", nombreInput.value);
    formData.append("email", emailInput.value);
    formData.append("contraseña", contraseñaInput.value);
    formData.append("domicilio", domicilioInput.value);
    formData.append("telefono", telefonoInput.value);
    formData.append("file", imagenInput.files[0]);

    const checkboxes = document.querySelectorAll(".tipo-comida-checkbox");
    let tipoComida = "";

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            tipoComida += checkbox.value + " ";
        }
    });

    formData.append("tipoDeComida", tipoComida);
    formData.append("privilegios", "negocio");

    // Creamos el restaurante en la db
    fetch('http://localhost:8080/restaurante', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                //MOSTRAR CARTEL DE QUE HUBO ALGUN ERROR
                throw new Error('Restaurante existente');
            }
            return response.json(); // Parsea la respuesta JSON para obtener el ID del cliente
        })
        // Con la respuesta de la db la enviamos al server.js y creamos el html para la pagina del negocio
        .then(data => {
            const datosAEnviar = {
                id: data.id,
                nombre: data.nombre
            };

            console.log(JSON.stringify(datosAEnviar));
            fetch('http://localhost:3000/crear-pagina/' + data.id, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' 
                },
                body: JSON.stringify(datosAEnviar)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al crear la página');
                    }
                    //Si la pagina se creo bien terminamos de asignar la url al negocio

                    // Asigna el ID del negocio a la cookie
                    document.cookie = `usuario=${data.id}; privilegio=${data.privilegios}; expires=Sun, 31 Dec 2033 12:00:00 UTC; path=/`;

                    const url = `http://localhost:3000/restaurante/id/${data.id}`;

                    // Ahora actualizamos ese restaurante asignandole la url propia
                    fetch('http://localhost:8080/restaurante/' + data.id, {
                        method: 'POST',
                        body: url,
                    })
                        .then(response => {
                            // abre la pagina del negocio
                            window.location.href = url;
                        })
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function iniciarSesionNegocio() {
    const emailInput = document.getElementById("emailLogin");
    const contraseñaInput = document.getElementById("contraseñaLogin");

    let datosLocal = {
        email: emailInput.value,
        contraseña: contraseñaInput.value,
        privilegios: "negocio"
    };

    // Validaciones aca

    fetch('http://localhost:8080/restaurante/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosLocal),
    })
        .then(response => {
            if (!response.ok) {
                //MOSTRAR CARTEL DE QUE HUBO ALGUN ERROR
                throw new Error('Usuario existente');
            }
            return response.json(); // Parsea la respuesta JSON para obtener el ID del cliente
        })
        .then(data => {
            // Asigna el ID del cliente a la cookie
            document.cookie = `usuario=${data.id}; privilegio=${data.privilegios}; expires=Sun, 31 Dec 2033 12:00:00 UTC; path=/`;

            // Redirige al usuario al menú principal
            window.location.href = 'mainNegocio.html';
        })
        .catch(error => {
            console.error('Error:', error);
        });
}