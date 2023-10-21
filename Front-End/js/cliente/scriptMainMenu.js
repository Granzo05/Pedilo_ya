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
                window.location.href = "/hamburguesas";
                break;
            case "panchos":
                window.location.href = "/panchos";
                break;
            case "empanadas":
                window.location.href = "/empanadas";
                break;
            case "pizzas":
                window.location.href = "/pizzas";
                break;
            case "lomos":
                window.location.href = "/lomos";
                break;
            case "helado":
                window.location.href = "/helado";
                break;
            case "parrilla":
                window.location.href = "/parrilla";
                break;
            case "pastas":
                window.location.href = "/pastas";
                break;
            case "sushi":
                window.location.href = "/sushi";
                break;
            case "vegetariano":
                window.location.href = "/vegetariano";
                break;
            case "sanguche":
                window.location.href = "/sanguche";
                break;
        }
        //Si la pagina se abre por primera vez, hamburguesas va a esta ampliada por lo que es al pedo hacerle dos clic para entrar, con esto deberia enviarte a la pagina con un solo clic
    } else if (primerClic === null && divId === "hamburguesas") {
        window.location.href = "/hamburguesas.html";
    } else {
        div.classList.add("enlarged");
    }
    primerClic = "si";
}