gapi.load('auth2', function () {
    gapi.auth2.init({
        client_id: '61970632056-i6fn8g521t6pa2n8v6bjqiucsupdcmcr.apps.googleusercontent.com'
    });
});

const tipoI = document.getElementById("contraseñaLogin");
const tipoR = document.getElementById("contraseñaRegistracion");
const iconI = document.getElementById("ocultarI");
const iconR = document.getElementById("ocultarR");

iconI.addEventListener("click", e => {
    if (tipoI.type == "password") {
        tipoI.type = "text";
        iconI.classList.remove("bx-show-alt");
        iconI.classList.add("bx-hide");
    } else {
        tipoI.type = "password";
        iconI.classList.remove("bx-hide");
        iconI.classList.add("bx-show-alt");
    }
})

iconR.addEventListener("click", e => {
    if (tipoR.type == "password") {
        tipoR.type = "text";
        iconR.classList.remove("bx-show-alt");
        iconR.classList.add("bx-hide");
    } else {
        tipoR.type = "password";
        iconR.classList.remove("bx-hide");
        iconR.classList.add("bx-show-alt");
    }
})

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

    

function cargarUsuario() {
    const nombreInput = document.getElementById("nombreRegistracion");
    const emailInput = document.getElementById("emailRegistracion");
    const contraseñaInput = document.getElementById("contraseñaRegistracion");

    const nombre = nombreInput.value.trim();
    const nombreSplit = nombre.split(" ");
    let datosCliente = {
        nombre: nombreSplit[0],
        apellido: nombreSplit[1],
        email: emailInput.value,
        contraseña: contraseñaInput.value,
        privilegios: "cliente"
    };

    // Validaciones aca

    fetch('http://localhost:8080/cliente', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosCliente),
    })
        .then(response => {
            if (!response.ok) {
                //MOSTRAR CARTEL DE QUE HUBO ALGUN ERROR
                throw new Error('Usuario existente');
            }
            //ACA TENDRIAMOS QUE HACER UN CARTEL O ALGO DE INICIO EXITOSO O DE BIENVENIDA
            window.location.href = 'Front-End/html/mainMenu.html';
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function iniciarSesionUsuario() {
    const emailInput = document.getElementById("emailLogin");
    const contraseñaInput = document.getElementById("contraseñaLogin");

    let datosCliente = {
        email: emailInput.value,
        contraseña: contraseñaInput.value,
        privilegios: "cliente"
    };

    // Validaciones aca

    fetch('http://localhost:8080/cliente/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosCliente),
    })
        .then(response => {
            if (!response.ok) {
                //MOSTRAR CARTEL DE QUE HUBO ALGUN ERROR
                throw new Error('Usuario existente');
            }
            document.cookie = `usuario=${data.nombre}; privilegio=${data.privilegios}; expires=Sun, 31 Dec 2033 12:00:00 UTC; path=/`;

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