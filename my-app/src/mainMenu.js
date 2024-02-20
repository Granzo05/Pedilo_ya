import React from 'react';

function MainMenu() {
    return (
        <div class="options">
            <div class="option active"
                style={{ backgroundImage: 'url(https://cdn.sanity.io/images/jsdrzfkj/production-esmx/5e2316cc629ede9cd6646163efeafc5486161751-6240x4160.jpg?w=800&h=533&fit=crop)' }}

                id="hamburguesas" onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="28" height="28"
                            src="https://img.icons8.com/external-line-zulfa-mahendra/48/external-burguer-food-delivery-line-zulfa-mahendra.png"
                            alt="external-burguer-food-delivery-line-zulfa-mahendra" />
                    </div>
                    <div class="info">
                        <div class="main">HAMBURGUESAS</div>
                        <div class="sub">¡Encuentra la que gustes!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(https://media.minutouno.com/p/7b22d01bdaf2dfb7c979c5b2e993b6c1/adjuntos/150/imagenes/038/105/0038105149/610x0/smart/super-pancho.jpg);' }}

                id="panchos" onclick="toggleImage(this)">
                <div class="label">
                    <div class="icon">
                        <img width="28" height="28" src="https://img.icons8.com/ios/50/hot-dog.png" alt="hot-dog" />
                    </div>
                    <div class="info">
                        <div class="main">PANCHOS</div>
                        <div class="sub">¡Encuentra tu favorito!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(https://www.clarin.com/img/2022/05/18/f91_3iwyd_1200x630__1.jpg);' }}

                id="empanadas" onclick="toggleImage(this)">
                <div class="label">
                    <div class="icon">
                        <img width="35" height="35"
                            src="https://img.icons8.com/external-flaticons-lineal-color-flat-icons/64/external-empanada-world-cuisine-flaticons-lineal-color-flat-icons-3.png"
                            alt="external-empanada-world-cuisine-flaticons-lineal-color-flat-icons-3" />
                    </div>
                    <div class="info">
                        <div class="main">EMPANADAS</div>
                        <div class="sub">¡Las mejores están aquí!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(https://media.istockphoto.com/id/938742222/photo/cheesy-pepperoni-pizza.jpg?s=612x612&w=0&k=20&c=D1z4xPCs-qQIZyUqRcHrnsJSJy_YbUD9udOrXpilNpI=);' }}
                id="pizzas" onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="27" height="27" src="https://img.icons8.com/ios/50/pizza.png" alt="pizza" />
                    </div>
                    <div class="info">
                        <div class="main">PIZZAS</div>
                        <div class="sub">¿Buscas algo italiano? ¡Te presentamos las mejores pizzas!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(https://saboresmendoza.com/wp-content/uploads/2019/09/lomos-1.jpg);' }}

                id="lomos" onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="https://img.icons8.com/ios/50/steak-medium.png"
                            alt="steak-medium" />
                    </div>
                    <div class="info">
                        <div class="main">LOMOS</div>
                        <div class="sub">¡Los mejores de la ciudad!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/helado.jpg);' }} id="helado"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-helado-50.png" alt="helado" />
                    </div>
                    <div class="info">
                        <div class="main">HELADO</div>
                        <div class="sub">¡El postre más rico!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/asado.jpg);' }} id="parrilla"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-parrilla-60.png" alt="carne" />
                    </div>
                    <div class="info">
                        <div class="main">PARRILLA</div>
                        <div class="sub">¡Ta para un asadaso!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/pastas.png);' }} id="pastas"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-pastas-100.png" alt="pasta" />
                    </div>
                    <div class="info">
                        <div class="main">PASTAS</div>
                        <div class="sub">¡Pero que rico!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/sanguche.jpeg);' }} id="sanguche"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-sándwich-50.png" alt="sanguche" />
                    </div>
                    <div class="info">
                        <div class="main">SANGUCHES</div>
                        <div class="sub">¡Ese viene completo
                            Jamón y queso y pan
                            Que lindo que es mirarte
                            Con tu milanga, Hernán!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/sushi.jpg);' }} id="sushi"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-sushi-50.png" alt="sushi" />
                    </div>
                    <div class="info">
                        <div class="main">SUSHI</div>
                        <div class="sub">¡No seas tan trolo man!</div>
                    </div>
                </div>
            </div>
            <div class="option"
                style={{ backgroundImage: 'url(/Front-End/css/img/vegetariano.jpg);' }} id="vegetariano"
                onclick="toggleImage(this)">
                <div class="label" onclick="toggleImage(this)">
                    <div class="icon">
                        <img width="31" height="31" src="/Front-End/css/img/icons8-ensalada-50.png" alt="ensalada" />
                    </div>
                    <div class="info">
                        <div class="main">VEGETARIANO</div>
                        <div class="sub">¡Ah bue lo que nos falta!</div>
                    </div>
                </div>
            </div>
        </div>
    );
}

var primerClic = null;

function toggleImage(divId) {
    const div = document.getElementById(divId);

    if (div.classList.contains("enlarged")) {
        switch (divId) {
            case "hamburguesas":
                console.log('Redirecting to hamburguesas');
                break;
            case "panchos":
                console.log('Redirecting to panchos');
                break;
            case "empanadas":
                console.log('Redirecting to empanadas');
                break;
            case "pizzas":
                console.log('Redirecting to pizzas');
                break;
            case "lomos":
                console.log('Redirecting to lomos');
                break;
            case "helado":
                console.log('Redirecting to helado');
                break;
            case "parrilla":
                console.log('Redirecting to parrilla');
                break;
            case "pastas":
                console.log('Redirecting to pastas');
                break;
            case "sushi":
                console.log('Redirecting to sushi');
                break;
            case "vegetariano":
                console.log('Redirecting to vegetariano');
                break;
            case "sanguche":
                console.log('Redirecting to sanguche');
                break;
        }
    } else if (primerClic === null && divId === "hamburguesas") {
        console.log('Redirecting to hamburguesas');
    } else {
        div.classList.add("enlarged");
    }
}

export default MainMenu;
