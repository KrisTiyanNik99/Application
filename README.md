# Project Overview
This JavaFX-based application is designed for product management, utilizing data from JSON files and displaying it in a table view interface, with a modular architecture aimed at enhancing object-oriented programming (OOP) skills. The system incorporates functionalities for loading, saving, manipulating, and sending product data via Gmail. The application is built with JavaFX for the UI, JSON for data management, Gmail API for email notifications, and Reflection API for dynamically managing product objects.

## Table of contents
* [Key Functionalities](#key-functionalities)
* [Key Components](#key-components)
* [Technologies and Dependencies](#technologies-and-dependencies)
* [Future Enhancements](#future-enhancements)
* [Conclusion](#conclusion)

## Key Functionalities
### 1. Dynamic Table Population:
* Based on user selection, the system loads the appropriate JSON file (mapped via `DataType`) and displays product data in a `dynamically` generated table. The columns are created based on the fields in the `Product` class using reflection.
### 2. Data Manipulation:
* Users can select products via checkboxes and perform actions like saving, deleting, or counting products displayed in the table. This functionality is handled by `TableManager`, while `FileUtils` manages file `I/O operations`.
### 3. File-Product Mapping:
* Each product type corresponds to a dedicated `JSON file`, which is read `dynamically` via `JsonReader`. This modular setup allows easy expansion—new product types can be added simply by creating a new JSON file and defining a new product class.
### 4. Validation and Error Handling:
* Validation mechanisms ensure data integrity, such as checking for consistent product types within a JSON file and ensuring the presence of required JSON parameters. Error handling in Gmail API calls is also managed to prevent disruptions in email notifications.
### 5. Email Notifications
* Users can send email `notifications` about product data using `Gmail API`. `GmailUtils` handles the email-sending logic, ensuring secure and error-free messaging via `OAuth`.

## Key Components
### 1. Product Management and Data Handling
* The core logic revolves around managing various product types (e.g., `VedenaProduct`, `BiroterapiyaProduct`, `ConsumerProduct`) that extend from the base `Product` class. Each product type is mapped to a corresponding JSON file through the `DataType` enum.
* JSON Parsing: The parsing logic is now split across classes such as `JsonReader` for reading JSON files and `JsonMapper` for `dynamically` mapping JSON data to Java objects, ensuring extensibility with minimal changes to core functionality.
### 2. Class Structure
* ``Product Class``: The abstract `Product class`, located in the "data_models" directory, defines common fields like `name`, `price`, `quantity`, `description`, and a `CheckBox` for UI selection.
* Product Types: Classes like `VedenaProduct`, `BiroterapiyaProduct`, and `ConsumerProduct` (found in the "data_models/models" package) inherit from the base `Product` class and may define additional fields or behaviors.
* DataType Enum: Maps each product type to its respective JSON file (e.g., `VEDENA` to `vedena.json`). This enum includes helper methods for parsing product types from strings and retrieving associated file directories.
### 3. UI Components
* The main table for displaying products is built with `DeliveryTableView`, an extension of `MainTableView`. This class is responsible for dynamically generating table columns based on product type and loading data from JSON files.
* SceneBuilder is used to build the UI layout in FXML files, allowing users to load data, save modifications, delete products, and interactively manage product details.
### 4. Functional Manager
The core functionality has been modularized into three key manager classes:
* FileUtils: Responsible for handling file operations, including saving and loading JSON files for products.
```java
    public static void saveAction(Product product, String fileName) {
        /*
            This method saves a finished product in the corresponding file in which we want to save it. We add it this
            way because we want the method to visually and permanently add the product to the data file that resides in
            the resources directory always. The file name must have it and the extension, otherwise the method will
            throw an exception.
         */

        JsonDataManager jsonDataManager = new JsonDataManager();
        jsonDataManager.saveInfoToJsonFile(product.toJsonFileFormat(), fileName);
    }
    public static void deleteProductFromTable(Product selectedProducts, TableView<Product> table) {
        table.getItems().removeAll(selectedProducts);
    }
```
* TableManager: Manages table-related functionalities, such as adding, removing, and counting products displayed in the table view.
```java
    public static void setAddingActionEventToCheckBox(CheckBox box, Product product,
                                                      TableView<Product> requestTable, Label productCounter) {

        box.setOnAction(event -> {
            boolean productExists = checkForMatch(product, requestTable);
            if (box.isSelected()) {
                addProductToTable(product, requestTable, productExists);
            } else {
                removeProductFromTable(product, requestTable);
                requestTable.refresh();
            }

            productCounter.setText(String.valueOf(requestTable.getItems().size()));
        });
    }
```
* UIUtils: Enhances the user experience by managing animations (e.g., slide animation for shopping cart) and UI interactions for a smoother workflow.
### 5. JSON Data Manager
The JSON management functionality is divided across several classes:
* JsonReader: Responsible for loading and parsing `JSON` files as `JSONObject` instances. It includes methods like `getJsonFileObject` for obtaining JSON file data and `findJsonArray` for locating JSON arrays within objects.
* Product Creation via `Reflection`: Dynamically creates product objects based on the data type and the `constructor parameters` (always take the constructor with the fewest parameters) found in the JSON file, allowing future product types to be integrated easily without changing existing logic.
* Data Validation: Validates the structure of the JSON files to ensure they conform to the expected format and have the correct parameters.
* JsonMapper: Uses reflection to dynamically map JSON data to Java objects based on class definitions. Key methods include `mapJsonArrayToList` (to map JSON arrays to Java lists) and `getJsonValuesForClass` (to retrieve class fields and map JSON values to them). This `flexibility` allows for easy addition of new product types and fields.
```java
    @Override
    public List<Product> getProductsFromJsonFile(String fileName) {
        String absolutePath = RESOURCE_DIR + fileName;

        JSONArray jsonArray = JsonReader.findJsonArray(JsonReader.getJsonFileObject(absolutePath));
        List<Product> products = fillListWithProducts(jsonArray);

        return products;
    }
```
### 6. Reflection and Dynamic Object Creation
* The ProductFactory class uses reflection to create instances of product classes based on JSON data. It scans available constructors in each class to match parameters in JSON, ensuring correct instantiation.
```java
    public static Product createProductObject(DataType type, List<Object> elements) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> productClass = getClassByType(type);

        return ReflectionUtils.createObject(productClass, elements);
    }
```
* The ReflectionUtils class includes utilities for working with class fields and constructors dynamically, enabling the seamless addition of new fields or products.
```java
    public static <T> T createObject(Class<?> clazz, List<Object> elements) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = clazz.getConstructors();
        /*
            Since we may have more than one constructor in the future, we want to be able to dynamically create an
            object of the Product class when the appropriate constructor (with the required number of parameters) is
            found to create an object of the Product class.
         */

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == elements.size()) {
                return (T) constructor.newInstance(elements.toArray());
            }
        }

        throw new IllegalArgumentException("There is no suitable constructor for this class with so many parameters." +
                System.lineSeparator() + "Cannot create class " + clazz);
    }
    
```
### 7. Gmail Integration
The Gmail integration module enables email notifications directly from the application:
* GmailConfigurationManager: Manages the configuration settings for Gmail, using a singleton pattern to ensure only one instance is created. Loads configuration settings from JSON files to enable secure, OAuth-based access.
```java
    public static void sendMail(String title, String textBody) throws Exception {
        // Build a new authorized API client service
        Gmail service = createGmailService();

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = getMimeMessage(title, textBody, session);

        // Encode and wrap the MIME message into a gmail message
        Message message = getMessage(email);

        try {
            // Create send message
            service.users().messages().send("me", message).execute();
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }
    @NotNull
    private static InputStream loadServiceConfiguration() throws FileNotFoundException {
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        return in;
    }
```
* GmailConfiguration: Contain all needed data for validation, authorization and sending mails. This class takes the date from "configuration.json" and contains all needed parameters and dependencies like tokens and e.t.
* GmailUtils: Handles the sending of emails through Gmail API. It constructs MIME messages, encodes them in Base64, and manages the email-sending process while handling potential errors with Google API responses.

## Technologies and Dependencies
* JavaFX: Used for building the graphical user interface.
* Gmail API: Utilized for sending email notifications.
* JSON Library: The `org.json` library is used for parsing and handling JSON data.
* Reflection API: Enables dynamic product creation and field mapping, allowing the system to adapt easily to new product types.
Maven Dependencies:
* `org.json` for JSON handling.
* `org.reflections` for using Java `Reflection APIs` to dynamically manage product types.
* `javax.mail` for email handling.
* `google-api-client`, `google-oauth-client`, and `google-api-services-gmail` for Gmail API integration.

## Future Enhancements
### 1. Extendable Product Types: The system is built for easy extension. Adding a new product type requires::
* Defining a new class that inherits from `Product`.
* Adding the new type to the `DataType` enum.
* Creating a corresponding JSON file.
### 2. Advanced JSON Parsing 
* Enhancements in the `JsonMapper` could include more sophisticated data type conversions and error handling, allowing for complex data structures and robust validation.
### 3. Additional Table Views and Data Types
* New table views and data types can be incorporated into the UI and managed dynamically using the existing `TableManager` and JSON processing classes.

## Conclusion
This application provides a robust and extendable framework for managing product data from JSON files, displaying it in a JavaFX table, and incorporating Gmail-based notifications. Its architecture leverages the power of reflection for dynamic object creation, JSON for flexible data management, and Gmail API for notification capabilities, making it a powerful tool for product management.

## Contact
If you’d like to discuss my work, provide feedback, or explore potential opportunities, feel free to reach out to me at kristiyan18kiko@gmail.com.
