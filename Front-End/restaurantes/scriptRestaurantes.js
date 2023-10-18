function cargarGrids(tipoComida) {
    fetch('http://localhost:8080/restaurante/' + tipoComida, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('');
            }
            return response.json();
        })
        .then(data => {
            let gridContainer = document.getElementById("gridRestaurantepizza");

            data.forEach(restaurante => {
                // Crea un nuevo elemento de la cuadrícula
                let gridItem = document.createElement("div");
                gridItem.className = "grid-item";

                // Crea una imagen y establece la fuente
                let img = document.createElement("img");
                img.src = restaurante.imagen; 

                let nombreRestaurante = document.createElement("p");
                nombreRestaurante.textContent = restaurante.nombre; 
                gridItem.appendChild(img);
                gridItem.appendChild(nombreRestaurante);

                gridContainer.appendChild(gridItem);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
