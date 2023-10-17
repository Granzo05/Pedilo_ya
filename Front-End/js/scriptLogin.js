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

