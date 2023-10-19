# Pagina de comida.
Una página de pedidos conectada a una base de datos Oracle a través de una Api RESTful creada con Java.

Explicación de clases API:

- Encrypt: encriptación de contraseñas tanto para clientes como negocios a la hora de registrarte, utilizando criptografía MD5.

- Controladores: utilizando anotaciones de Spring, el cliente se encarga de comunicarse con cada una de las tablas de la base de datos a través de las rutas definidas para cada una de las entidades. Hay un controlador por cada entidad necesaria (Cliente, Restaurante, Empleado, Facturas y Menu de comida).

- Entidades: clases que diferencian cada uno de los objetos que utilizamos para interactuar en el cliente, así le damos cada atributo que será pedido en el cliente, enviado hacia la api, procesado y cargado en la base de datos.

- Repositorios: utilizando Jpa sirve para enviar los datos finales hacia la base de datos u obtenerlos dependiendo del tipo de llamada que se realiza desde el cliente. La funcionalidad mediante Querys ayuda a traer datos específicos de cada clase.

Explicacion de carpetas y elementos en el Front-End:
Aclaración: la comunicación hacia la api se realiza con Fetch desde javaScript, enviando y recibiendo consultas en JSON

- CSS:
  - img: almacen de imágenes.
  - styleLogin: estilos de ambos login(Cliente y negocio).
  - styleMainMenu: estilo para el menu donde se elije el tipo de comida a buscar.
- HTML:
  - login: estructuración de ambos logins, que utilizan para el estilo (styleLogin) y para las funcionalidades de javaScript los archivos scriptLoginCliente.js y scriptLoginNegocio.js para comunicarse con la api y permitir la registración o el logueo hacia el mainMenu.html;
  - RestaurantePorComida: luego de elegir en el mainMenu que tipo de comida se busca, estos htmls hacen una petición GET hacia la API donde reciben todos los restaurantes que contengan el tipo de comida buscada, por lo tanto cada html tiene la lógica para solicitar y mostrar en cuadros cada uno de los restaurantes que estén relacionados con esa especialidad. Para realizarla utilizan el archivo scriptCargaRestaurantes.js que envía el tipo de comida hacia la API, la cual buscar todos los restaurantes y los filtra uno por uno por el tipo de comida, en caso de haber coincidencias devuelve nombre e imagen del local para ir creando divs para cada uno de los resultados. Que luego al hacerles clic se envía hacia ese restaurante para ver los menus específicos.
  - js:
    - scriptCookie: maneja las cookies a la hora de iniciar sesión para que cuando el usuario cierre la página su sesión no se pierda, a su vez verifica los tipos de privilegios en caso de estar ingresando a páginas donde no deben tener ciertas funcionalidades a disposición, a diferencia del negocio que si podría modificar ciertas características de su propia página, el usuario solo podría elegir uno o varios menús, mientrñas que el negocio podria editar, borrar, agregar cada uno de ellos.
    - scriptMainMenu: encargado de que navegue hacia cada uno de los segmentos de comidas, al hacer clic en una imagén, esta se amplía mostrando el tipo de comida, donde al hacer clic nuevamente navega hacia la url que realiza un GET con los restaurantes de ese tipo de comida. Trabaja en conjunto con mainMenu.html
