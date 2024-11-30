# Store Management and Inventory Microservice

This project is a RESTful API microservice built with Java and Spring Boot, designed for managing store and inventory data in an e-commerce system. The service includes:

- **Store Management**: Allows users to create, update, and delete store information, such as store name, location, and phone number.


- **Stock Management**: Enables users to manage stock quantities for products across multiple stores. Features include retrieving stock information by store or product, and increasing or decreasing stock quantities based on incoming requests. Stock adjustments are tracked, validated, and updated accordingly.


- **Stock History Tracking**: Every change in stock (increase or decrease) is logged with details about the action, including the previous and new stock quantities. The stock history helps track inventory changes over time.


- **Order Management**: Users can place orders by specifying a list of products, and the service will allocate available stock from the stores. The service ensures products are available from the appropriate stores and tracks stock changes based on the order.


- Swagger Documentation: The API is fully documented using Swagger for easy testing and exploration of available endpoints.


## Prerequisites

- **Java 21**
- **Maven**

## Installation

1. Navigate to the project directory.
2. Run the following commands:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run


## Access Swagger
Once the application is running, you can access the Swagger API documentation by visiting the following URL:

[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

This will provide you with interactive documentation of all available API endpoints for easy exploration and testing.
