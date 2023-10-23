let idRestaurante = 0;

function buscarStock(id) {
    idRestaurante = id;
    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + "/stock/", {
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
            let contenedorPrincipal = document.getElementById("stock");

            data.forEach(stock => {
                let contenedor = document.createElement("div");
                contenedor.className = "grid-item";

                let ingredienteNombre = document.createElement("h3");
                ingredienteNombre.textContent = stock.ingrediente.nombre;
                contenedor.appendChild(ingredienteNombre);

                let ingredienteCantidad = document.createElement("h3");
                ingredienteCantidad.textContent = stock.cantidad + stock.medida;
                contenedor.appendChild(ingredienteCantidad);

                let fechaLlegadaStockNuevo = document.createElement("h3");
                fechaLlegadaStockNuevo.textContent = stock.fechaLlegada.toLocaleString();
                contenedor.appendChild(fechaLlegadaStockNuevo);

                contenedorPrincipal.appendChild(contenedor);
            });

        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function agregarStock() {
    let nombreIngrediente = document.getElementById('nombreIngredienteAñadir').value;
    let cantidadIngrediente = document.getElementById('cantidadIngredienteAñadir').value;
    let medidaIngredienteSelect = document.getElementById('medidaIngredienteAñadir');
    let medidaSeleccionada = medidaIngredienteSelect.value;
    let costoIngrediente = document.getElementById('costoIngredienteAñadir').value;


    datosIngrediente = {
        nombre: nombreIngrediente,
        cantidad: cantidadIngrediente,
        medida: medidaSeleccionada,
        costo: costoIngrediente
    }
    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + '/stock/carga', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosIngrediente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            // AVISAR QUE FUE AÑADIDO CON EXITO

        })
        .catch(error => {
            console.error('Error:', error);
        });
}
let selectNombresActualizar = document.getElementById("select-nombre-ingrediente-actualizar");
let selectNombresBorrar = document.getElementById("select-nombre-ingrediente-borrar");

// Al seleccionar uno busco los demas datos de él en la base de datos y se lo asigno a los demas campos para que el usuario tenga informacion en tiempo real al editarlo
selectNombres.addEventListener("change", function () {
    let nombreProducto = selectNombres.value;

    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + '/stock/' + nombreProducto, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
        })
        .then(data => {
            let nombreIngrediente = document.getElementById('nombreIngredienteActualizar');
            nombreIngrediente.value = data.ingrediente.nombre;

            let cantidadIngrediente = document.getElementById('cantidadIngredienteActualizar');
            cantidadIngrediente.value = data.cantidad;

            let medidaIngrediente = document.getElementById('medidaIngredienteActualizar');
            medidaIngrediente.value = data.medida

            let costoIngrediente = document.getElementById('costoIngredienteActualizar');
            costoIngrediente.value = data.ingrediente.costo;
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

// Cargo el select del modal editar con los nombres de todos los ingredientes cargados en la base de datos
function cargarSelectNombreIngrediente() {
    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + '/stock', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
        })
        .then(data => {
            // Esto deberia completar los 2 modales con datos, ya que la llamada se hace igual para ambos, en vez de hacer una llamada por cada uno. Hago una sola llamada y completo ambos
            data.forEach(stock => {
                let optionSelect = document.createElement("option");
                optionSelect.value = stock.ingrediente.nombre;
                optionSelect.textContent = stock.ingrediente.nombre;
                selectNombresActualizar.appendChild(optionSelect);

                optionSelect = document.createElement("option");
                optionSelect.value = stock.ingrediente.nombre;
                optionSelect.textContent = stock.ingrediente.nombre;
                selectNombresBorrar.appendChild(optionSelect);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function updateStock() {
    let nombreIngrediente = document.getElementById('select-nombre-ingrediente-actualizar');
    let cantidadIngrediente = document.getElementById('cantidadIngredienteActualizar');
    let medidaIngrediente = document.getElementById('medidaIngredienteActualizar');
    let costoIngrediente = document.getElementById('costoIngredienteActualizar');


    datosIngrediente = {
        nombre: nombreIngrediente,
        cantidad: cantidadIngrediente,
        medida: medidaIngrediente,
        costo: costoIngrediente
    }
    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + '/stock/update/', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosIngrediente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            // AVISAR QUE FUE EDITADO

        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function deleteStock() {
    let nombreIngrediente = document.getElementById('select-nombre-ingrediente-borrar').value;

    fetch('http://localhost:8080/restaurante/id/' + idRestaurante + '/stock/delete' + nombreIngrediente, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            // AVISAR QUE FUE BORRADO
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function abrirModalAñadir() {
    var modal = document.getElementById("modal-añadir");
    modal.style.display = "block";
}

function cerrarModalAñadir() {
    var modal = document.getElementById("modal-añadir");
    modal.style.display = "none";
}

function abrirModalEditar() {
    cargarSelectNombreIngrediente()
    var modal = document.getElementById("modal-editar");
    modal.style.display = "block";
}

function cerrarModalEditar() {
    var modal = document.getElementById("modal-editar");
    modal.style.display = "none";
}

function abrirModalBorrar() {
    cargarSelectNombreIngrediente()
    var modal = document.getElementById("modal-borrar");
    modal.style.display = "block";
}

function cerrarModalBorrar() {
    var modal = document.getElementById("modal-borrar");
    modal.style.display = "none";
}