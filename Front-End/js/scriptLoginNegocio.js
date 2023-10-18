
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
    const tipoComida = "";


    const formData = new FormData();
    formData.append("nombre", nombreInput.value);
    formData.append("email", emailInput.value);
    formData.append("contraseña", contraseñaInput.value);
    formData.append("domicilio", domicilioInput.value);
    formData.append("telefono", telefonoInput.value);
    formData.append("file", imagenInput.files[0]);

    const pizzaCheckbox = document.getElementById("pizzaBox");
    const hamburguesaBox = document.getElementById("hamburguesaBox");
    const empanadasBox = document.getElementById("empanadasBox");
    const heladoBox = document.getElementById("heladoBox");
    const sushiBox = document.getElementById("sushiBox");
    const lomosBox = document.getElementById("lomosBox");
    const parrillaBox = document.getElementById("parrillaBox");
    const pastasBox = document.getElementById("pastasBox");
    const vegetarianoBox = document.getElementById("vegetarianoBox");
    const sanguchesBox = document.getElementById("sanguchesBox");

    if (pizzaCheckbox.checked) {
        tipoComida = pizzaCheckbox.value + " ";
    }

    if (hamburguesaBox.checked) {
        tipoComida = hamburguesaBox.value + " ";
    }

    if (empanadasBox.checked) {
        tipoComida = empanadasBox.value + " ";
    }

    if (heladoBox.checked) {
        tipoComida = heladoBox.value + " ";
    }

    if (sushiBox.checked) {
        tipoComida = sushiBox.value + " ";
    }

    if (lomosBox.checked) {
        tipoComida = lomosBox.value + " ";
    }

    if (parrillaBox.checked) {
        tipoComida = parrillaBox.value + " ";
    }

    if (pastasBox.checked) {
        tipoComida = pastasBox.value + " ";
    }

    if (vegetarianoBox.checked) {
        tipoComida = vegetarianoBox.value + " ";
    }

    if (sanguchesBox.checked) {
        tipoComida = sanguchesBox.value + " ";
    }

    formData.append("tipoDeComida", tipoComida);

    fetch('http://localhost:8080/restaurante', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar la imagen');
            }
            window.location.href = 'mainMenu.html';
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
            //Tambien se puede recuperar momentaneamente la imagen y los datos desde la db para usarlos durante la sesion y despues descartarlos
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