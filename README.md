# E-Commerce API

**Ecommerce App** is a web application for online shopping. It allows users to browse products, add them to the cart, and complete purchases. Key features include product management, user accounts, and advanced search. The backend is built with Java and Spring Boot, with MySQL for the database.

## Introduction
This is a backend system for an **E-Commerce** application built with **Spring Boot**. It provides a RESTful API for managing users, products, orders, shopping carts, and authentication.

## Features
- User authentication with JWT-based login and registration
- Product management with CRUD operations
- Shopping cart functionality to add, update, and remove items
- Order processing, including order placement and tracking
- Role-based access control (RBAC)
- API documentation using Swagger UI

## Setup and Installation

### Prerequisites
- Java 21
- Maven
- MySQL

### Clone the Repository
```bash
git clone https://github.com/seifElsokary2003/ecommerce_app.git  
cd ecommerce_app  
```  

### Database Configuration
- Create a database in MySQL:
```sql
CREATE DATABASE ecommerce_db;  
```  
- Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db  
spring.datasource.username=your_username  
spring.datasource.password=your_password  
```  
**It is recommended to use environment variables for security instead of hardcoding credentials.**

### Run the Project
```bash
mvn clean install  
mvn spring-boot:run  
```  

### API Documentation
After running the application, access the API documentation:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Project Structure
- `entity/` - Database entities such as Product, User, Cart
- `repository/` - Spring Data JPA repositories
- `service/` - Business logic services
- `controller/` - REST API controllers
- `security/` - JWT authentication setup
- `config/` - Application configuration files
- `testing/serviceTest/` - JUnit test cases

## Running Tests
JUnit test cases are included for services. To run the tests:
```bash

mvn test  

```  

Test files are located in:  
`src/test/java/com/example/E_commerce/testing/serviceTest/`

## Future Enhancements
- Payment gateway integration
- AI-based product recommendations
- Product image upload

## Contributing
Contributions are welcome. Fork the repository and submit a pull request.
