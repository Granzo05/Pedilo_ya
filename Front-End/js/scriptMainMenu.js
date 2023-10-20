$(".option").click(function () {
    $(".option").removeClass("active");
    $(this).addClass("active");
});

//Funcion para que cuando una imagen sea clickeada una vez y se agrande, al volver a clickear para navegar te envie a su respectiva pagina
var primerClic = null;

function toggleImage(div) {
    const divId = div.id;

    if (div.classList.contains("enlarged")) {
        switch (divId) {
            case "hamburguesas":
                window.location.href = "restaurantePorComida/hamburguesas.html";
                break;
            case "panchos":
                window.location.href = "restaurantePorComida/panchos.html";
                break;
            case "empanadas":
                window.location.href = "restaurantePorComida/empanadas.html";
                break;
            case "pizzas":
                window.location.href = "restaurantePorComida/pizzas.html";
                break;
            case "lomos":
                window.location.href = "restaurantePorComida/lomos.html";
                break;
            case "helado":
                window.location.href = "restaurantePorComida/helado.html";
                break;
            case "parrilla":
                window.location.href = "restaurantePorComida/parrilla.html";
                break;
            case "pastas":
                window.location.href = "restaurantePorComida/pastas.html";
                break;
            case "sushi":
                window.location.href = "restaurantePorComida/sushi.html";
                break;
            case "vegetariano":
                window.location.href = "restaurantePorComida/vegetariano.html";
                break;
            case "sanguche":
                window.location.href = "restaurantePorComida/sanguche.html";
                break;
        }
        //Si la pagina se abre por primera vez, hamburguesas va a esta ampliada por lo que es al pedo hacerle dos clic para entrar, con esto deberia enviarte a la pagina con un solo clic
    } else if (primerClic === null && divId === "hamburguesas") {
        window.location.href = "restaurantePorComida/hamburguesas.html";
    } else {
        div.classList.add("enlarged");
    }
    primerClic = "si";
}