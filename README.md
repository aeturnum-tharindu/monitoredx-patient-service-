Patient Service API

A Spring Boot–based RESTful application that manages patient information.
It provides CRUD operations over /api/patients, backed by a PostgreSQL database.

| Technology            | Version | Description                                    |
| --------------------- | ------- | ---------------------------------------------- |
| **Java**              | 21      | Core language                                  |
| **Spring Boot**       | 3.5.7   | Application framework                          |
| **Gradle**            | 8.14.3  | Build and dependency management                |
| **PostgreSQL**        | 18      | Primary database                               |
| **Spring Data JPA**   | —       | ORM layer                                      |
| **JUnit 5 + Mockito** | —       | Unit and integration testing                   |

Before running this application, ensure you have:

Java 21 installed
Gradle 8.14.3+ installed or use the included Gradle wrapper (./gradlew)
PostgreSQL 18 running locally or accessible remotely

API Documentation
http://localhost:8080/monitoredx-patient-service/swagger-ui/index.html#/
