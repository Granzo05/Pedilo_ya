gapi.load('auth2', function () {
    gapi.auth2.init({
        client_id: '61970632056-i6fn8g521t6pa2n8v6bjqiucsupdcmcr.apps.googleusercontent.com'
    });
});

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
        contraseña: contraseñaInput.value
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

function iniciarConGoogle(googleUser) {
    var profile = googleUser.getBasicProfile();
    const idToken = googleUser.getAuthResponse().id_token;

    const nombreInput = profile.getName();
    const emailInput = profile.getEmail();

    const nombre = nombreInput.value.trim();
    const nombreSplit = nombre.split(" ");
    let datosCliente = {
        nombre: nombreSplit[0],
        apellido: nombreSplit[1],
        email: emailInput.value,
    };

    fetch('/auth/google', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken: token }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al enviar el token (${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
    document.getElementById("nombreRegistracion").hidden = true;
    document.getElementById("emailRegistracion").hidden = true;
    document.getElementById("contraseñaRegistracion").hidden = true;
    document.getElementById("registrarseGoogle").hidden = false;
    esperarBotonInicio()
        .then(data => {
            // El servidor de google ha autenticado al usuario
            datosCliente.contraseña = document.getElementById("contraseñaRegistracion").value;
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
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function esperarBotonInicio() {
    return new Promise((resolve) => {
        const iniciarSesionButton = document.getElementById("registrarseGoogle");

        if (iniciarSesionButton) {
            iniciarSesionButton.addEventListener("click", () => {
                resolve();
            });
        } else {
            resolve();
        }
    });
}


function iniciarSesionUsuario() {
    const emailInput = document.getElementById("emailLogin");
    const contraseñaInput = document.getElementById("contraseñaLogin");

    let datosCliente = {
        email: emailInput.value,
        contraseña: contraseñaInput.value
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