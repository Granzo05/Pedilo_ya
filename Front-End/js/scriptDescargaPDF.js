function descargarFactura(idPedido, fechaPedido) {
    fetch("http://localhost:8080/factura/pedido/" + idPedido + "/pdf", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            return response.blob();
        })
        .then(blob => {
            // Blob y un enlace para descargar el PDF
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = "factura" + fechaPedido + ".pdf";
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url); // Liberar la URL del objeto Blob
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function descargarPedido(idPedido, fechaPedido) {
    fetch("http://localhost:8080/pedido/" + idPedido + "/pdf", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener datos (${response.status}): ${response.statusText}`);
            }
            return response.blob();
        })
        .then(blob => {
            // Blob y un enlace para descargar el PDF
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = "factura" + fechaPedido + ".pdf";
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url); // Liberar la URL del objeto Blob
        })
        .catch(error => {
            console.error("Error:", error);
        });
}