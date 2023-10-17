
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
    console.log(JSON.stringify(datosCliente));

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
            window.location.href = 'mainMenu.html';
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
        contraseña: contraseñaInput.value
    };

    // Validaciones aca
    console.log(JSON.stringify(datosCliente));

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