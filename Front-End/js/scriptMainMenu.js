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
                window.location.href = "restaurantes/hamburguesas.html";
                break;
            case "panchos":
                window.location.href = "restaurantes/panchos.html";
                break;
            case "empanadas":
                window.location.href = "restaurantes/empanadas.html";
                break;
            case "pizzas":
                window.location.href = "restaurantes/pizzas.html";
                break;
            case "lomos":
                window.location.href = "restaurantes/lomos.html";
                break;
        }
    //Si la pagina se abre por primera vez, hamburguesas va a esta ampliada por lo que es al pedo hacerle dos clic para entrar, con esto deberia enviarte a la pagina con un solo clic
    } else if (primerClic === null && divId === "hamburguesas") {
        window.location.href = "restaurantes/hamburguesas.html";
    } else {
        div.classList.add("enlarged");
    }
    primerClic = "si";
}