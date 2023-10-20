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

                let añadirBoton = document.createElement("button");
                añadirBoton.textContent = "Añadir al pedido";
                añadirBoton.onclick = añadirProductoAlCarrito(this);

                frenteCarta.appendChild(imgElement);
                frenteCarta.appendChild(nombreMenu);

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
    // Obtén la tarjeta que contiene el botón
    const tarjeta = button.parentElement;

    // Clona la tarjeta
    const tarjetaCopia = tarjeta.cloneNode(true);

    // Obtén el precio desde la tarjeta copiada
    const precio = tarjetaCopia.querySelector(".precio").textContent;
    const precioNumerico = parseFloat(precio.replace("$", "").replace(".",","));

    // Agrega la tarjeta copiada al contenedor del carrito
    const carrito = document.getElementById('carrito');
    carrito.appendChild(tarjetaCopia);

    // Actualiza el contador de productos en el carrito
    const contador = document.getElementById('contador');
    contador.textContent = parseInt(contador.textContent) + 1;

    // Actualiza el total del carrito
    var totalPagar = document.querySelector('.total-pagar');
    totalPagar.textContent += precioNumerico;
}





