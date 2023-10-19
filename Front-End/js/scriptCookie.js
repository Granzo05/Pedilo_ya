// En caso que requiera privilegios la pagina deberia llamara a esta funcion como verificarCookies(true), si es una pagina donde puede ingresar el cliente deberia llamarla
// como verificarCookies();

function verificarCookies(privilegioNecesario = false) {
    // Función para obtener el valor de una cookie por su nombre
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

    // Verificar la cookie de usuario
    const usuario = getCookie('usuario');

    if (usuario) {
        // El usuario está autenticado, puedes realizar acciones específicas
    } else {
        // El usuario no está autenticado, puedes redirigirlo a la página de inicio de sesión
        window.location.href = 'login/loginCliente.html';
    }

    if (privilegioNecesario === true) {
        const privilegio = getCookie('privilegio');

        if (privilegio === 'negocio') {
            // El usuario es un usuario estándar y no deberia tener el ingreso permitido a las paginas de negocio o a sus funciones
        } else {
            // El usuario no tiene el tipo de privilegio adecuado, puedes redirigirlo a una página de acceso denegado
            window.location.href = 'Front-End/html/accesoDenegado.html';
        }
    }

}

