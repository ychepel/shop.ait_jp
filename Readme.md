# Project "Shop"

## Overview
This project, guided by the principles of Clean Architecture, aims to develop a "Shop" application. It involves managing entities such as products and customers, who can add products to their shopping carts.

## Clean Architecture
Clean Architecture emphasizes separating software components into defined layers, ensuring that the system is organized according to dependencies rules.

## Features

### Entities
- **Product**:
    - Unique integer identifier
    - Boolean status indicating if the product is active
    - String name
    - Price (floating point supported)

- **Customer**:
    - Unique integer identifier
    - Boolean status indicating if the customer is active
    - String name
    - Shopping cart object

- **Shopping Cart**:
    - Unique integer identifier
    - List of products within the cart
    - Functionality to add and remove products if active

### Layers
1. **Domain Layer**: Contains all classes describing the entities.
2. **Repository Layer**: Manages access to the database, holding the physical storage of entity objects.
3. **Service Layer**: Implements all business logic of the application concerning the entities.
4. **Controller Layer**: Handles requests from clients, determines actions to be taken, and returns responses.

### Functionality
- Add, retrieve, modify, and logically delete entities from the database.
- Restore previously deleted entities to a "non-active" status.
- Controllers implement functionalities provided by their respective services.

## Testing
The application can be tested using any external tool capable of sending HTTP requests, such as Postman.

