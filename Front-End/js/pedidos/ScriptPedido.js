function cargarMenu() {
    fetch("http://localhost:8080/restaurante/menu" + id, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            let contenedor = document.getElementById("container-items");

            data.forEach(menu => {
                //Creamos el div que contiene cada menu
                let carta = document.createElement("div");
                carta.className = "card";

                // Creamos el frente de la tarjeta que va a contener la imagen y el nombre
                let frenteCarta = document.createElement("div");
                frenteCarta.className = "front";

                const imgElement = document.createElement("img");
                imgElement.src = "data:image/png;base64," + menu.imagen64;

                let nombreMenu = document.createElement("h2");
                nombreMenu.textContent = menu.nombre;

                let precioMenu = document.createElement("h2");
                precioMenu.textContent = menu.precio;

                let añadirBoton = document.createElement("button");
                añadirBoton.textContent = "Añadir al pedido";
                añadirBoton.onclick = añadirProductoAlCarrito(this);

                frenteCarta.appendChild(imgElement);
                frenteCarta.appendChild(nombreMenu);
                frenteCarta.appendChild(precioMenu);

                // Creamos el dorso que va a contener los ingredientes y alguna descripcion quizas
                let dorsoCarta = document.createElement("div");
                dorsoCarta.className = "back";
                dorsoCarta.hidden = true;

                // Agregamos cada ingrediente
                let ingredientes = document.createElement("p");
                menu.ingredientes.forEach(ingrediente => {
                    ingredientes.textContent += "* " + ingrediente + "\n";
                });

                // Lo asignamos al dorso
                dorsoCarta.appendChild(ingredientes);

                // Asignamos ambas caras a la tarjeta
                carta.appendChild(frenteCarta);
                carta.appendChild(dorsoCarta);

                // Asignamos la carta completa al div
                contenedor.appendChild(carta);
            });
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function girarCarta(carta) {
    let cartaDiv = document.getElementById(carta.id);

    // Obtener el frente y el dorso de la tarjeta
    const front = cartaDiv.querySelector(".front");
    const back = cartaDiv.querySelector(".back");

    // Verificar si la tarjeta está volteada o no
    if (cartaDiv.classList.contains("flipped")) {
        front.style.display = "block";
        back.style.display = "none";
    } else {
        front.style.display = "none";
        back.style.display = "block";
    }

    // Alternar la clase "flipped" para girar la tarjeta
    cartaDiv.classList.toggle("flipped");
}

function añadirAlCarrito(button) {
    const tarjeta = button.parentElement;

    // Clonar la tarjeta
    const tarjetaCopia = tarjeta.cloneNode(true);

    const botonAñadir = tarjetaCopia.querySelector('button');
    botonAñadir.remove();

    const cantidad = tarjetaCopia.createElement('input');
    cantidad.className("cantidad");
    cantidad.type = 'number';
    cantidad.value = 1;

    const precio = tarjetaCopia.querySelector(".precio").textContent;

    const carrito = document.getElementById('tarjeta-carrito');
    carrito.appendChild(tarjetaCopia);

    const contador = document.getElementById('contador');
    contador.textContent = parseInt(contador.textContent) + 1;

    var totalPagar = document.querySelector('.total-pagar');
    var total = 0;

    if (totalPagar.textContent != "") {
        total = parseFloat(totalPagar.textContent.replace("$", "").replace(".", ""));
    }
    var precioActual = parseFloat(precio.replace("$", "").replace(".", "") * parseFloat(cantidad)) + parseFloat(total);
    totalPagar.textContent = precioActual;
}


function finalizarPedido() {
    const totalPagar = document.querySelector('.total-pagar').textContent;

    // tarjetas de productos en el carrito
    const tarjetasEnCarrito = document.querySelectorAll('#carrito .card');

    const detallesProductos = [];

    tarjetasEnCarrito.forEach(tarjeta => {
        const nombreProducto = tarjeta.querySelector('h2').textContent;
        const cantidadProducto = tarjeta.querySelector('.cantidad').textContent;
        const precioProducto = tarjeta.querySelector('.precio').textContent;
        const imagenProducto = tarjeta.querySelector('img');

        detallesProductos.push({ nombre: nombreProducto, precio: precioProducto, imagen: imagenProducto, cantidad: cantidadProducto });
    });

    // Codificar los detalles del carrito y el total
    const detallesCodificados = encodeURIComponent(JSON.stringify(detallesProductos));
    const totalCodificado = encodeURIComponent(totalPagar);

    // Redirigir a otra página con los detalles y el total como parámetros en la URL
    // ID del restaurante de la URL actual
    const urlActual = window.location.href;
    const restauranteId = urlActual.split('/').pop(); // ultimo segmento de la URL

    const redireccionURL = `/restaurante/id/${restauranteId}/pago?detalles=${detallesCodificados}&total=${totalCodificado}`;

    window.location.href = redireccionURL;
}

//TODO LO DEL MODAL

function abrirModal() {
    var modal = document.getElementById("miModal");
    modal.style.display = "block";
}

function cerrarModal() {
    var modal = document.getElementById("miModal");
    modal.style.display = "none";
}

function agregarMenu() {
    const nombreInput = document.getElementById("nombreMenu");
    const ingredientesInputs = document.querySelectorAll(".ingredienteMenu");
    const coccionInput = document.getElementById("coccionMenu");
    const tipoMenuSelect = document.getElementById("tipoMenu");
    const tipoMenuSeleccionado = tipoMenuSelect.value;
    const comensalesInput = document.getElementById("comensales");
    const precioInput = document.getElementById("precioMenu");
    const imagenInput = document.getElementById("imagenProducto");

    const formData = new FormData();
    formData.append("nombre", nombreInput.value);
    formData.append("tiempoCoccion", coccionInput.value);
    formData.append("tipo", tipoMenuSeleccionado);
    formData.append("comensales", comensalesInput);
    formData.append("precio", precioInput.value);
    formData.append("file", imagenInput.files[0]);

    ingredientesInputs.forEach((input, index) => {
        const ingrediente = input.value;
        const cantidad = cantidadesInputs[index].value;
        ingredientes.push({ nombre: ingrediente, cantidad: cantidad });
    });

    fetch('http://localhost:8080/restaurante/menu', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el menu');
            }
            alert("Menu cargado con exito");
            cargarMenu();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function añadirCampoIngrediente() {
    let containter = document.getElementById("ingrediente-containter");

    let inputIngrediente = document.createElement("input");
    inputIngrediente.type = "text";
    inputIngrediente.placeholder = "Ingrediente";
    inputIngrediente.className = "ingredienteMenu";

    let br = document.createElement("br");

    containter.appendChild(inputIngrediente);
    containter.appendChild(br);
}

window.addEventListener("click", function (event) {
    var modal = document.getElementById("miModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
});

/*
const btnCarrito = document.querySelector('.container-icon');
const containerCarrito = document.querySelector('.container-productosCarrito');

btnCarrito.addEventListener('click', e => {
    containerCarrito.classList.toggle('hidden-cart');
});
*/






