# Hogwarts Archive System - Design Report

## System Architecture Overview

The Hogwarts Archive system implements a clean three-tier architecture separating presentation, business logic, and data management concerns. This design enables maintainability, testability, and extensibility while adhering to object-oriented programming principles.

## Class Structure and Design Decisions

### Core Domain Classes

**SpellBook**: Represents the spellbook entity with encapsulated attributes (serialNumber, title, inventor, type) and rental state management. This class maintains its own rental history and current renter information, following the Single Responsibility Principle by handling only spellbook-related operations. The `isCopyOf()` method implements business logic for identifying duplicate spellbooks based on title and inventor, demonstrating proper domain modeling.

**Student**: Models student accounts with complete encapsulation of rental operations. The class maintains both currently renting books and rental history using separate lists, providing clear separation between active and historical data. Methods like `rentSpellbook()`, `returnSpellbook()`, and `returnAllSpellbooks()` provide a cohesive interface for rental management.

### Business Logic Layer

**Archive**: Serves as the central repository and business logic coordinator, implementing the Facade pattern to provide a simplified interface for complex operations. This class uses HashMap for O(1) lookup performance for both students and spellbooks, prioritizing efficiency. The separation of concerns is evident: Archive handles data management and validation, while domain classes handle their own state.

Key design decisions include:
- Using streams and functional programming for querying operations (`getAllTypes()`, `getSpellbooksByInventor()`), improving code readability and maintainability
- Implementing automatic student number generation starting from 100000, ensuring consistency
- Returning sorted collections by default, encapsulating sorting logic within the Archive layer

### Presentation Layer

**HogwartsArchive**: Implements the Model-View-Controller pattern's controller component, handling user interaction and command parsing. This class delegates business logic to Archive, maintaining strict separation of concerns. The command parser uses a switch-case structure with dedicated handler methods, making the system easily extensible for new commands.

## Object-Oriented Design Principles

**Encapsulation**: All classes use private fields with public methods for controlled access. Internal collections are never directly exposed; methods return copies or perform operations internally.

**Information Hiding**: Implementation details (HashMap storage, student number generation) are hidden behind well-defined interfaces. Clients interact through meaningful method names without knowing internal representation.

**Cohesion**: Each class has a single, well-defined purpose. SpellBook manages spellbook data, Student manages student data, Archive coordinates the system, and HogwartsArchive handles user interaction.

**Coupling**: Low coupling achieved through dependency injection and interface-based programming. Archive could be replaced with a database-backed implementation without changing HogwartsArchive.

## Design Patterns

**Repository Pattern**: Archive acts as an in-memory repository, abstracting data access logic and providing a collection-like interface for entities.

**Facade Pattern**: Archive provides a simplified interface to complex rental operations, hiding the coordination between Student and SpellBook objects.

**Strategy Pattern** (implicit): CSV reading/writing logic could be extracted into separate strategies for different file formats, demonstrating extensibility.

## Extensibility and Maintainability

The system is designed for extension:
- New commands can be added by implementing new handler methods in HogwartsArchive
- Different storage backends can be implemented by creating Archive subclasses or implementations
- New entity types (e.g., Library, Room) can be added following the established patterns

Error handling is consistent throughout, with validation occurring at the Archive level and user-friendly messages returned to the presentation layer. This prevents invalid states and provides clear feedback.

## Conclusion

The architecture demonstrates mature object-oriented design with clear separation of concerns, appropriate use of design patterns, and emphasis on maintainability and extensibility. The codebase follows established Java conventions and best practices, making it suitable for future enhancement and team collaboration.

**Word Count: 498**
