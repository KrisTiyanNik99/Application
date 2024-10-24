# Project Overview
This JavaFX-based application is designed for product management, utilizing data from JSON files and displaying it in a table view interface and it's builded for my current work. The system incorporates functionalities for loading, saving, and manipulating product data. The application architecture is built with a combination of the JavaFX framework for the user interface, JSON for data management, and Reflection API to dynamically handle product objects.

## Table of contents
* [Key Components](#key-components)
* [Key Functionalities](#key-functionalities)
* [Technologies and Dependencies](#technologies-and-dependencies)
* [Future Enhancements](#future-enhancements)
* [Conclusion](#conclusion)

## Key Components
### 1. Product Management and Data Handling
* The core logic revolves around handling different types of products (e.g., VedenaProduct, BiroterapiyaProduct, ConsumerProduct), which extend from the base Product class. Each product is associated with a type (DataType) that helps map the product to its corresponding JSON file.
* JSON Parsing: The JsonDataManager class is responsible for loading product data from JSON files and dynamically creating product objects using reflection. This ensures flexibility when adding new product types in the future without altering the core functionality.
### 2. Class Structure
* Product Class: This is an abstract base class (in "data_models" directory) for all products. It defines common fields like name, price, quantity, description, and a CheckBox (used for selecting products in the UI).
* Specific Products: Classes like VedenaProduct, BiroterapiyaProduct, and ConsumerProduct (in "models" package from "data_models" directory) extend the base Product class and may have additional specific attributes or behaviors.
* DataType Enum: Maps product types to their corresponding JSON files (e.g., VEDENA to vedena.json). It also includes helper methods to parse product types from strings and retrieve the file directory.
### 3. UI Components
* The main table for product display is built using DeliveryTableView, which extends MainTableView. This class is responsible for dynamically populating the table based on the product type retrieved from the JSON file.
* SceneBuilder is used for constructing the graphical interface in fxml file. The UI allows users to load product data by clicking buttons, save modifications, delete products, and manage them interactively.
### 4. Functional Manager
The FunctionManager class (in "data_manager" directory) encapsulates various utility methods, including:
* Checking Product Type: Ensures that products of a similar type are being processed together.
* Save and Delete Actions: Handles saving the current table view or deleting selected products from the table and other data structures.
* UI Interaction: Manages slide animations for the shopping cart menu and adding actions to checkboxes, making the UI more interactive and functional.
### 5. JSON Data Manager
The JsonDataManager class (in "data_manager" directory) is the core of data processing, implementing the JsonParser interface. It performs the following tasks:
* Reading JSON Files: Loads and parses product data from JSON files from the resource directory.
* Product Creation via Reflection: Dynamically creates product objects based on the data type and the constructor parameters (always take the constructor with fewest parameters) found in the JSON file, allowing future product types to be integrated easily without changing existing logic.
* Data Validation: Validates the structure of the JSON files to ensure they conform to the expected format and have the correct parameters.
### 6. Reflection and Dynamic Object Creation
* The ProductFactory class uses reflection to dynamically create instances of product classes based on the data type provided in the JSON file. It scans the constructors of each class to match the parameters from the JSON, ensuring that objects are instantiated correctly.
* It also provides methods to retrieve class fields and constructor parameters dynamically, ensuring flexibility when new fields are added or classes are modified.

## Key Functionalities
### 1. Dynamic Table Population:
* Based on the selected button, the system loads product data from the corresponding JSON file (mapped via DataType) and displays it in a table (DeliveryTableView). This includes creating table columns dynamically by matching the JSON data fields to the product class fields using reflection.
### 2. Data Manipulation:
* Users can select products via checkboxes, and perform actions such as saving, deleting, or counting products in the table. The FunctionManager provides the logic to handle these operations.
### 3. File-Product Mapping:
* Each product type corresponds to a JSON file, and these files are read dynamically via JsonDataManager. This allows for easy extension in the future, where adding a new product type only requires a new JSON file and an associated product class.
### 4. Validation and Error Handling:
* Validation mechanisms ensure data integrity, such as checking whether all products in a file are of the same type and whether JSON files contain valid parameters.

## Technologies and Dependencies
* JavaFX: Used for building the graphical user interface.
* JSON Library: The org.json library is used for parsing and handling JSON data.
* Reflection API: Enables dynamic product creation and field mapping, allowing the system to adapt easily to new product types.
Maven Dependencies:
* org.json for JSON handling.
* org.reflections for using Java Reflection APIs to dynamically manage product types.

## Future Enhancements
### 1. Extendable Product Types: The system is designed to be easily extendable. Adding a new product type would require:
* Creating a new class inheriting from Product.
* Adding the new type to the DataType enum.
* Creating a corresponding JSON file.
### 2. Enhanced JSON Parsing: 
The method for converting JSON parameters to appropriate object types (convertJsonParameterToValue) could be extended for more complex data types.
### 3. More Table Views and Data Types: 
In the future, additional views or data types can be incorporated into the UI and handled dynamically by leveraging the existing DeliveryTableView and JsonDataManager.

## Conclusion
This application offers a flexible, scalable framework for managing products from JSON data in a JavaFX table view. Its core strength lies in its use of reflection for dynamic object creation, ensuring future extensibility. The combination of JSON for data storage and JavaFX for the user interface makes this a powerful tool for managing various types of products efficiently.
