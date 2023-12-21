# AuthService-Java-21
A microservice that implements Java 17 and Spring Boot 3.1.5 to efficiently manage user authentication and authorization.

## Config

### application.properties

For project configuration, it is advisable to refer to the application.properties.example file. This will assist in customizing the application.properties file, allowing the developer to tailor the project configuration to their requirements. During this setup, it is essential to configure the credentials for the H2 database instance and specify the port to be used in the environment.

```yml
#H2 Database Configuration
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:<DATABASE_NAME>
spring.datasource.username=<USER>
spring.datasource.password=<PASSWORD>
spring.h2.console.enabled=true

# Port Configuration
server.port=<PORT>
```

### .env
Additionally, we will configure the .env file, which will contain the environment variables. In this case, we will manage the secret key in this file, which will be used in the signing of the generated tokens.