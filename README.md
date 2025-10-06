# Project Overview
This Java Swing-based hotel management application provides functionalities for managing users, rooms, room types, and reservations. The system uses JSON-like repositories for data persistence and emphasizes modular, object-oriented design to facilitate extensibility and maintainability. Core functionalities include creating, updating, deleting, and viewing users, rooms, room types, and reservations. The UI is managed through a Mediator pattern implemented by the UIController and Window interfaces, ensuring centralized communication between different UI components.

The system also demonstrates the use of the Factory and Mediator patterns in repository classes for generating unique IDs and creating new entities dynamically.

## Table of contents
* [Key Functionalities](#key-functionalities)
* [Key Components](#key-components)
* [Design Patterns](#design-patterns)
* [Technologies and Dependencies](#technologies-and-dependencies)
* [Future Enhancements](#future-enhancements)
* [Conclusion](#conclusion)

## Key Functionalities
### 1. User Management:
* Register new users with username and password `UserManagerImpl`.
* Login functionality with admin check and user validation.
* Admins can delete users, and all their reservations are automatically removed.
### 2. Room and Room Type Management:
* Create, update, and delete rooms `RoomManagerImpl` and room types `RoomTypeManagerImpl`.
* Rooms have a status `AVAILABLE` or `BOOKED` managed by `RoomStatusService`.
* Admins can view all rooms and room types, and associate rooms with specific types.
### 3. Reservation Management:
* Users can book rooms and cancel reservations `BookingManagerImpl`.
* Admins can delete any reservation and manage all bookings.
* Reservation history can be retrieved per user.
### 4. UI Management:
* The UI is modular, built using Java Swing, with different panels for login, registration, user dashboard, and admin dashboard.
* Mediator Pattern: `UIController` and `MainWindow` act as mediators, handling communication between panels and centralizing control of UI state and transitions.
### 5. Repository-Based Entity Creation (Factory-Like Pattern):
* All core entities (`User`, `Room`, `RoomType`, `Reservation`) are created through repository services, which generate unique IDs and persist objects.
* This imitates a database-like behavior with ID generation and centralized persistence.
```java
// Example from UserManagerImpl:
int userId = userRepoService.generateNextId();
User currentUser = new UserImpl(userId, username, password);
userRepoService.createValue(currentUser);
System.out.println(userId);
User registeredUser = userRepoService.findById(userId);
```
* Example with create a room object 
```java
// Room creation example from AdminMenuPanel:
RoomType roomType = (RoomType) roomTypeJComboBox.getSelectedItem();
double roomPrice = Double.parseDouble(pricePerNight.getText());
double roomFee = Double.parseDouble(cancelFee.getText());
roomManager.createNewRoom(roomType, roomPrice, roomFee);
```
```java
// Generic repository pseudo-Factory logic:
public void createValue(T entity) {
    // Persist entity to in-memory store or file
if (newInstance == null || existsById(newInstance.getId())) {
    System.out.println(typeName() + " cannot be null or already existed!");
    return;
}
    entityMap.put(newInstance.getId(), newInstance);
    persistToFile();
    generateNextId(newInstance.getId());
    System.out.println("New " + typeName() + " is added!");
}

public int generateNextId() {
    setNewId(newId + 1);
    return newId;
}
```
* This approach ensures `unique IDs` and centralized entity creation, demonstrating a lightweight Factory Pattern in practice without a full database backend.

## Key Components
### 1. Repository Services:
* `RepoService<T>`: Generic repository handling creation, updating, deletion, and retrieval of entities.
* ID Generation: Ensures unique IDs for users, rooms, room types, and reservations, following a `Factory-like approach` with `ObjectProvider<T>`:
```java
public interface ObjectProvider<T> {

    /**
     * Creates an object from a given set of string data values.
     *
     * @param data the array containing object properties as strings
     * @return the reconstructed object instance
     */
    T getObjectFromData(String[] data);
}
```
* For example how this factory pattern create one intance be extracting data from file:
```java
    protected void persistToFile() {
        try (PrintWriter writer = new PrintWriter(Configurations.FILE_ROOT_PATH + repositoryFileName)) {
            for (T entity : entityMap.values()) {
                writer.print(entity.textFormat());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while writing to the file: " +
                    Configurations.FILE_ROOT_PATH + repositoryFileName, e);
        }
    }

@Override
    public Reservation getObjectFromData(String[] data) {
        Integer id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        Integer userId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        Integer roomId = Integer.parseInt(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        LocalDate arrivalData = LocalDate.parse(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);
        LocalDate departureDate = LocalDate.parse(data[4].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);
        boolean isCanceled = data.length == 6 && Boolean.parseBoolean(data[5].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(id);
        return new Reservation(id, userId, roomId, arrivalData, departureDate, isCanceled);
    }
```
### 2. Managers:
* ``UserManagerImpl``, ``RoomManagerImpl``, ``RoomTypeManagerImpl``, ``BookingManagerImpl``: Encapsulate business logic for `CRUD` operations.
* Validation, status updates, and entity relationships are handled centrally.
### 3. UI Components:
* Abstract UI components are derived from `AbstractsUIElement`.
* Panels like `LoginPanel`, `RegisterPanel`, `AdminMenuPanel`, and `UserUIElement` provide concrete user interfaces.
* Dialogs for reservations and entity creation are integrated seamlessly.

## Design Patterns
### 1. Factory Pattern:
* Used in repository services to dynamically create entities and generate unique IDs.
```java
int roomTypeId = roomTypeRepository.generateNextId();
roomTypeRepository.createValue(new RoomTypeImpl(roomTypeId, name, amenities, maximumOccupancy));
```
* This ensures consistent entity creation and centralizes ID management.
### 2. Mediator Pattern:
* `UIController` acts as a mediator for all UI components:
- Controls which panel is displayed (`MainWindow`).
- Handles user navigation between login, registration, and main dashboards.
- Prevents direct coupling between individual panels.
```java
public void showLoginPanel() {
    mainWindow.showPanel(UIElement.LOGIN.getTypeAsString());
}
```
* All UI panels communicate via `UIController` instead of directly invoking each other, simplifying maintenance and extensibility.
```java
public interface UIController {

    /**
     * Registers a UI component (panel, dialog, etc.) to the main application window.
     *
     * @param component the UI component type to register
     */
    void registerComponents(UIElement component);

    /**
     * Displays the login panel.
     */
    void showLoginPanel();

    /**
     * Displays the registration panel.
     */
    void showRegisterPanel();

    /**
     * Displays the main user panel for the given user.
     * @param userId the ID of the user whose data should be loaded
     */
    void showMainPanel(Integer userId);

    /**
     * Displays the admin panel view.
     */
    void showAdminPanel();

    /**
     * Creates and initializes all repository instances.
     * Repositories are responsible for handling persistent data access and storage.
     */
    void createRepositoryInstances();

    /**
     * Creates and initializes all manager instances.
     * Managers act as business logic layers built on top of repository services.
     */
    void createManagerInstances();
}
```

## Technologies and Dependencies
* Java 17 (or compatible)
* Java Swing for UI components
* Repository-based persistence (file-backed or in-memory) from scratch
* Object-Oriented Programming best practices (encapsulation, inheritance, interfaces)
* Design Patterns: Factory (ID generation & entity creation), Mediator (UI management)

## Future Enhancements
* Add persistent JSON or database storage for users, rooms, and reservations.
* Introduce advanced filtering and searching for rooms and reservations.
* Integrate notifications for reservation confirmations.
* Extend user roles and permissions (e.g., staff, manager).

## Conclusion
This hotel management application demonstrates clean OOP principles, modular architecture, and the application of design patterns for a maintainable and extensible system. Its use of Swing, repository services, and mediator-driven UI ensures separation of concerns while keeping the system adaptable for future growth.

## Contact
If you’d like to discuss my work, provide feedback, or explore potential opportunities, feel free to reach out to me at kristiyan18kiko@abv.com.

Thank you for your time and interest in reviewing my repository!
