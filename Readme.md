# PROJECT "SHOP"

### DEVELOPMENT OF THE "SHOP" PROJECT

- Principles of application design. "Clean architecture".
- Project task.
- Description of project entities.
- Description of project layers.
- Testing of the created application.

### "CLEAN ARCHITECTURE"

"Clean architecture" is a philosophy of software design that divides components into specific layers.

### PROJECT TASK

Develop the "Shop" project, guided by the principles of "clean architecture".
The domain (subject) area of the application includes products, customers, and their baskets, into which customers can add selected products.

### DESCRIPTION OF PROJECT ENTITIES

#### PRODUCT

A product has the following properties:
- Integer unique identifier
- Logical parameter indicating whether the product is active
- String name
- Price (may have fractional values)

#### CUSTOMER

A customer has the following properties:
- Integer unique identifier
- Logical parameter indicating whether the customer is active
- String name
- Basket object

#### BASKET

A basket has the following properties:
- Integer unique identifier
- List of products contained in this basket

#### BASKET FUNCTIONALITY

- Add a product to the basket (if active)
- Get all products in the basket (active ones)
- Remove a product from the basket by its identifier
- Completely clear the basket (remove all products)
- Get the total cost of the basket (active products)
- Get the average cost of a product in the basket (from active products)

### DESCRIPTION OF PROJECT LAYERS

### First Layer. Domain.

Contains all classes describing project entities.

### Second Layer. Repositories.

The project must contain repositories for products and customers.

A repository is a class whose object serves to access the database. The physical storage of entity objects is carried out in the database itself.

**Repository functionality:**

- Save a new object in the database. Each new object should be automatically assigned an incrementing unique identifier (provided by the database functionality).
- Return a list of all objects.
- Return one object by its identifier.
- Modify one object by its identifier.
- Delete one object from the database by its identifier. Physical deletion should not occur; instead, the logical parameter of the object should simply be set to false. This functionality is necessary to enable the recovery of deleted objects.

### Third Layer. Services.

The project must contain product service and customer service.

A service is a class whose object provides all the business logic of the application related to the entities with which the application works.

For saving, getting, modifying, and deleting entities from the database, the service interacts with the corresponding repository.

**Product service functionality:**

- Save a product in the database (when saving, the product is automatically considered active).
- Return all products from the database (active ones).
- Return one product from the database by its identifier (if it is active).
- Modify one product in the database by its identifier.
- Delete a product from the database by its identifier.
- Delete a product from the database by its name.
- Restore a deleted product in the database by its identifier.
- Return the total number of products in the database (active ones).
- Return the total cost of all products in the database (active ones).
- Return the average cost of a product in the database (from active ones).

**Customer service functionality:**

- Save a customer in the database (when saving, the customer is automatically considered active).
- Return all customers from the database (active ones).
- Return one customer from the database by its identifier (if it is active).
- Modify one customer in the database by its identifier.
- Delete a customer from the database by its identifier.
- Delete a customer from the database by its name.
- Restore a deleted customer in the database by its identifier.
- Return the total number of customers in the database (active ones).
- Return the cost of the customer's basket by its identifier (if it is active).
- Return the average cost of a product in the customer's basket by its identifier (if it is active).
- Add a product to the customer's basket by their identifiers (if both are active).
- Remove a product from the customer's basket by their identifiers.
- Completely clear the customer's basket by its identifier (if it is active).

### Fourth Layer. Controllers.

The project must contain product controller and customer controller.

A controller is a class whose object receives requests that have come to the application from the client, decides what to do with them, takes necessary actions, and returns a response to the client.

For performing all necessary actions related to client requests, the controller interacts with the service.

**Controller functionality:**

Product and customer controllers must have methods implementing all the capabilities provided by the corresponding services.

### TESTING THE APPLICATION

The application can be tested using any third-party application that allows sending HTTP requests. For example, Postman.
