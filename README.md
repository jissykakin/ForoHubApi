# ForoHubApi  - API REST para Foro ❔⁉️💬💭🗯️
¡Bienvenido al corazón de ForoHubApi! Este proyecto backend, construido con Java 17 y Spring Boot, es la base de una vibrante comunidad en línea donde usuarios de todos los niveles pueden compartir conocimientos, resolver dudas y conectarse con otros apasionados.

## ¿Qué es ForoHubApi? 🤔⌨️🛠️
ForoHubApi es una API REST robusta y escalable que permite a los usuarios:

- **Crear, actualizar y eliminar tópicos:** Comparte tus conocimientos y experiencias con la comunidad.
- **Buscar y filtrar tópicos:** Encuentra la información que necesitas de forma rápida y eficiente.
- **Responder a tópicos:** Participa en discusiones y construye una comunidad sólida.
- **Autenticarse y registrarse:** Crea una cuenta para acceder a todas las funcionalidades de la plataforma.

## Características Clave: 🆎🔤🔝

- **Arquitectura sólida:** Basada en Spring Boot y siguiendo los principios REST, garantiza un desarrollo eficiente y mantenible.
  - **Validaciones robustas:** Cada solicitud es validada para garantizar la integridad de los datos y prevenir errores comunes.
  - **Seguridad:** Implementación de un sistema de autenticación y autorización para proteger los datos de los usuarios.
  ![image](https://github.com/user-attachments/assets/4ed13a97-96c4-4116-83de-0f4709f04573)
  
  - **Escalabilidad:** Diseñada para crecer junto a tu comunidad, con la posibilidad de agregar nuevas funcionalidades en el futuro.
  - **Documentación detallada:** Gracias a SpringDoc y Swagger, podrás explorar la API de forma interactiva y comprender su funcionamiento.

## Cómo empezar 🚀
### Requisitos previos
- Java Development Kit (JDK) 17: Asegúrate de tener instalado el JDK 17 en tu sistema.
- Herramienta de construcción: Maven o Gradle.
- Base de datos: MySQL configurada y en funcionamiento.
- Editor de código: Tu editor favorito (VSCode, IntelliJ IDEA, etc.).

### Instalación
1. Clona este repositorio:

```ts
    git clone https://github.com/jissykakin/ForoHubApi.git
 ```

2. Configurar la base de datos:

   - Crear la base de datos en MySql.
       ```ts
         CREATE DATABASE IF NOT EXISTS `foro_hub_api` 
       ```
   - Crear Tablas: Utiliza las migraciones de Flyway para crear las tablas de la base de datos.

4. Configurar la aplicación
   - Propiedades de la aplicación: Edita el archivo application.properties (o application.yml) para configurar la conexión a la base de datos y otras propiedades.

5. Ejecutar la aplicación:
  ```ts
    # Usando Maven
    mvn spring-boot:run
  ```

## Documentació  ️

![image](https://github.com/user-attachments/assets/2749ab7e-d5da-44f2-ad06-e1100fc6965b)

  ### Usuarios:

🔗 1.  #### POST Auteticación User 🔓
```
http://localhost:8080/api/auth/login
```
Solicitud POST para generar el login de usuario se puede realizar colocando el usuario o el email y la password

Requerimientos:
```declarative 
    {
        "username": "test2@gmail.com",       
        "password": "12345678"
    }
```

![image](https://github.com/user-attachments/assets/3eb24ffb-aee3-49ee-9c60-a8b0def368d0)


Respuestas:
    Status 200:
    ![image](https://github.com/user-attachments/assets/3bd1f62e-7d3b-4787-8d62-d4d6368c30de)
    No Autorizado - Status 401:
    ![image](https://github.com/user-attachments/assets/4254eea6-b550-4929-876b-301e2eb55693)
    Validaciones - Status 400:
    ![image](https://github.com/user-attachments/assets/268dde14-a0db-4509-ad75-1173f3ff5db6)

🔗 2. #### POST Registrar Usuario 🔓

```declarative
    http://localhost:8080/api/auth/register
```
Solicitud POST para registar un usuario en el sistema, 

Requerimiento o parametros:

```declarative
{
	"username": "amerlano",
    "email":"test5@gmail.com",
    "password": "12345678",    
    "profession": "Medico General",
    "role": "ROLE_USER"
}
```
![image](https://github.com/user-attachments/assets/de93c9dd-6a57-4930-9898-df05fd4874bc)

Respuestas:
Creado - Status 201:
![image](https://github.com/user-attachments/assets/ba968947-6ef0-402c-9bdb-cc73c3f232e3)

Validaciones - Status 400:

![image](https://github.com/user-attachments/assets/1655b138-084f-4c87-8e01-8a8388339eac)

![image](https://github.com/user-attachments/assets/02beeb0b-0c0e-4c17-8d42-ae69112671db)

![image](https://github.com/user-attachments/assets/4643f246-b3d1-4af7-a0de-1e43230d21aa)

  ### Tópicos:
 
🔗 1. GET Listar Topicos 🔐

```
    http://localhost:8080/api/topics?year=2025&course=backend
```
Requerimientos o Parametros:

 - Authorization - Bearer Token
 - Query Params:   year, course
 - Parametros de paginacion:
```
{
  "page": 1,
  "size": 10,
  "sort": [
    "createdAt"
  ]
}
```

![image](https://github.com/user-attachments/assets/02212c5f-c271-4fa0-845e-f6bed70eef6f)

Respuestas:
Ok - Status 200:
 Sin parametros de query
![image](https://github.com/user-attachments/assets/21b2fc32-1079-4ee5-96dd-b305454465f0)

con parametro Curso o  con año:
![image](https://github.com/user-attachments/assets/4b4fe028-8297-4035-a570-b8116924a4cc)

Con parametro de año y curso:

![image](https://github.com/user-attachments/assets/c52b0eeb-6c31-4b29-92bd-0b17c26f4b44)

Forbidden - Status 403:

![image](https://github.com/user-attachments/assets/8fb80972-b67c-45f1-b39a-d3bf7d0a560d)

No autorizado - Status 401

![image](https://github.com/user-attachments/assets/f1a13384-b8f7-4fd0-8bc3-a33f23466d81)

  ### Respuestas:
  Asociación a tópicos


## Tecnologías Utilizadas: 🛠️⚙️

- **Java 17:** El lenguaje de programación más moderno y potente.
- **Spring Boot 3.4.1:** Framework para desarrollo de aplicaciones Java empresariales.
- **Lombok:** Simplifica el código con anotaciones para reducir la verbosidad.
- **Spring Web:** Proporciona las herramientas necesarias para crear APIs REST.
- **Spring Data JPA:** Facilita la interacción con la base de datos.
- **Flyway Migration:** Gestiona las migraciones de la base de datos de forma segura.
- **MySQL:** Base de datos relacional para almacenar los datos de la aplicación.
- **Spring Security:** Protege tu aplicación de ataques y garantiza la seguridad de los usuarios.
- **Swagger:** Genera una interfaz interactiva para documentar y probar la API.