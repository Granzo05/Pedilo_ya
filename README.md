# ApiRest-SpringBoot-Hibernate-JPA.
Una API REST implementada con Docker y MySQL, creando las llamadas HTTP con POSTMAN y verificando los cambios con H2 desde el navegador.
Tuve varias dificultades por la misma falta de experiencia, errores que me llevaron días pero finalmente logré que todo funcionara. Con mezcla de códigos de StackOverFlow, que me fueron ayudando a complementar mi código. 

Explicación de clases:

- UserRepository

Es una interfaz que ayuda a implementar los metodos de Jpa para poder hacer Querys, y facilitar la búsqueda de usuarios en la Base de Datos.

- User

Es la clase que tiene todos los métodos, y sus respectivos Getters & Setters

- UsersController

Es la clase que se encarga de los metodos HTTP, la cual se sirve de los métodos Jpa de la interfaz "UserRepository".
@RequestBody trae los valores ingresados en POSTMAN y el código en el método "createUser" hace un filtro por Emails, en caso de que haya uno cargado en la Base de datos tira un error 400 y un mensaje de error, en caso de que no exista el usuario arroja el código 201.

En el metódo PUT se vuelve un poco más complejo ya que me vi con el error de lógica el cual si o si me pedía ingresar todos los datos por más que solo quisiera modificar el Email, por lo tanto me serví de Reflection para buscar campo por campo y así ver cual varía comparando el User ya cargado con el UserDetail que es el objeto que envía POSTMAN. Asi solo si quiero cambiar el nombre o cual sea el atributo no me exije cambiar los demás. Lógicamente es obvio, pero fue un punto que me llevó su tiempo arreglar hasta que investigue sobre reflection

- ApplicationConfig

Despúes de la quemada de cabeza, y varios errores con Spring y Jpa fue una de las soluciones que encontré gracias a un usuario en Stack que tenia el mismo problema, aún no probé si es completamente necesaria la clase ya que solucioné una cascada de errores y fue bastante el código que termine modificando por lo que me queda pendiente ver la necesidad de esta clase.

- ApiRestApplication

La clase que contiene el Main, donde tambien tiene los respectivos anotadores para escanear los packages en busca de clases, me llevo tiempo solucionar los errores de esta clase ya que al leer la documentación entendí mal la forma en la que se debian buscar las clases entonces añadía el path completo, ejemplo "com.example.ApiREST.entities.User" y la clase al final no debía ser colocada, solo debia llamar a los packages y eso me costó bastante tiempo en un error bastante simple.

Asi es como funciona, de forma simple, esta Api el cual solo hace llamadas a la base de datos y se encarga de toda la lógica para ver si el usuario existe o no, hay varias funciones que se pueden agregar, como:

En los métodos getUsers y deleteUser, agregar un control de errores para manejar cualquier excepción que pueda ocurrir en tiempo de ejecución.

Crear un sistema de logeo por niveles de autoridad, lo que puede darte la posibilidad de editar usuarios o prohibirte lo mismo.

En el método PUT, colocar mensajes que avisen si el usuario ha sido actualizado con exito o no.
