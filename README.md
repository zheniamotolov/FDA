# FDA Drug Information Service

This service provides access to drug information sourced from the [FDA Drug API](https://api.fda.gov/drug/drugsfda.json).

## Getting Started

These instructions will get the project up and running on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following tools installed:

- Java JDK 17 or later
- Maven 3.6 or later

### Running the Project

To run the project, follow these steps:

1. **Build the project:**

    ```sh
    mvn clean install
    ```

    This command will compile the code, run tests, and package the project.

2. **Run the application:**

    ```sh
    java -jar target/FdaApplication.jar
    ```
    Or, directly with Maven:
    
    ```sh
    mvn spring-boot:run
    ```

    The service should now be up and running on `http://localhost:8080`.

### Documentation

For API documentation and interactive testing, visit the Swagger UI:

- Swagger - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


### Postman Collection

For testing and exploring the API via Postman, use the following collection:

- [FDA Drug Information Service Postman Collection](https://api.postman.com/collections/3208254-1d75df9e-8305-4ee0-bcf9-5993a24ed38b?access_key=PMAT-01HM3FGY47RZSNTWFMZ4X737VF)

Import the collection into your Postman application to begin making requests.

### Running Tests

```sh
mvn test
