// En caso que requiera privilegios la pagina deberia llamara a esta funcion como verificarCookies(true), si es una pagina donde puede ingresar el cliente deberia llamarla
// como verificarCookies();

function verificarCookies(privilegioNecesario = false) {
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

    // Verificar la cookie de usuario y privilegio
    const usuario = getCookie('usuario');
    const privilegio = getCookie('privilegio');

    if (!usuario) {
        // El usuario no está autenticado
        window.location.href = 'login/loginCliente.html';
        return;
    }

    if (privilegioNecesario && privilegio !== 'negocio') {
        // El usuario no tiene el tipo de privilegio adecuado
        window.location.href = 'Front-End/html/accesoDenegado.html';
    }

    if (usuario !== "negocio") {
        const elementosAdmin = document.querySelectorAll(".admin");
        elementosAdmin.forEach(elemento => {
            elemento.style.display = "none";
        });
    }
}


