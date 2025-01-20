# ForoHubApi  - API REST para aplicaciones de foros ⚙💻⌨️❔⁉️💬💭🗯️
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
   - Crear Tablas: Crear la base de datos: Utiliza las migraciones de Flyway para crear las tablas de la base de datos.

4. Configurar la aplicación
   - Propiedades de la aplicación: Edita el archivo application.properties (o application.yml) para configurar la conexión a la base de datos y otras propiedades.

5. Ejecutar la aplicación:
  ```ts
    # Usando Maven
    mvn spring-boot:run
  ```

## Funcionalidades ️
  ### Usuarios:

  Registro

  Autenticación
 
  ### Tópicos:
  Creación
  Lectura
  Actualización
  Eliminación
  Búsqueda y filtrado
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