function cargarGrids(tipoComida) {
    fetch('http://localhost:8080/restaurante/' + tipoComida, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            let gridContainer = document.getElementById("grid-container");

            data.forEach(restaurante => {
                let gridItem = document.createElement("div");
                gridItem.className = "grid-item";
                const imgElement = document.createElement("img");
                imgElement.src = "data:image/png;base64," + restaurante.imagen64;

                let nombreRestaurante = document.createElement("h2");
                nombreRestaurante.textContent = restaurante.nombre;

                gridItem.appendChild(imgElement);
                gridItem.appendChild(nombreRestaurante);

                gridContainer.appendChild(gridItem);
            });

        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const btnCarrito = document.querySelector('.container-icon');
const containerCarrito = document.querySelector('.container-productosCarrito');

btnCarrito.addEventListener('click', e => {
    containerCarrito.classList.toggle('hidden-cart');
});
