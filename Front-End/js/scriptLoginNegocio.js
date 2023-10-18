
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
    formData.append("imagen", imagenInput.files[0]);

    // Validaciones aca

    fetch('http://localhost:8080/restaurante', {
        method: 'POST',
        body: formData, 
    })
        .then(response => {
            if (!response.ok) {
                //MOSTRAR CARTEL DE QUE HUBO ALGUN ERROR
                throw new Error('Error al registrar');
            }
            // Redirigir a una página de éxito
            window.location.href = 'exito.html';
        })
        .catch(error => {
            console.error('Error:', error);
        });

}

function iniciarSesionUsuario() {
    const emailInput = document.getElementById("emailLogin");
    const contraseñaInput = document.getElementById("contraseñaLogin");

    let datosLocal = {
        email: emailInput.value,
        contraseña: contraseñaInput.value
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
                throw new Error('');
            }
            //ACA TENDRIAMOS QUE HACER UN CARTEL O ALGO DE INICIO EXITOSO O DE BIENVENIDA
            window.location.href = 'mainMenu.html';
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}