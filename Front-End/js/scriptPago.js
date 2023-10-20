document.addEventListener('DOMContentLoaded', function () {
    // Obtener los parametros de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const detallesCodificados = urlParams.get('detalles');
    const totalCodificado = urlParams.get('total');

    // Decodifica los detalles y el total
    const detallesProductos = JSON.parse(decodeURIComponent(detallesCodificado));
    const totalPagar = decodeURIComponent(totalCodificado);

    let contenedor = document.getElementById("detalle-producto");

    detallesProductos.forEach(pedido => {
        //Creamos el div que contiene cada menu
        let info = document.createElement("div");
        info.className = "info-producto";

        let img = document.createElement("img");
        img.src = pedido.imagen;

        let nombre = document.createElement("span");
        nombre.textContent = pedido.nombre;

        let precio = document.createElement("span");
        precio.textContent = pedido.precio;

        info.appendChild(img);
        info.appendChild(nombre);
        info.appendChild(precio);

        contenedor.appendChild(info);
    });

    console.log('Total a pagar:', totalPagar);
});
