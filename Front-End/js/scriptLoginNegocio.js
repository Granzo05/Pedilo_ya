
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

    fetch('http://localhost:8080/restaurante', {
        method: 'POST',
        body: formData,
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

            const url = `http://localhost:3000/restaurante_${data.id}.html`;

            // Crea la página HTML personalizada en el servidor
            fetch(`http://localhost:3000/crear-pagina/${data.id}`, {
                method: 'POST',
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al crear la página');
                    }

                    // Redirige al negocio a su nueva página
                    window.location.href = url;
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