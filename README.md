# **Citronix Farm Management System**

## **Introduction**
Citronix is a system designed for lemon farm management, covering key operations such as farm management, harvest tracking, and sales management. The project is developed using **Domain-Driven Design (DDD)** principles, ensuring clear domain separation and scalability.

The system includes three bounded contexts:
1. **Farm Management**
2. **Harvest Management**
3. **Sale Management**

---

## **Architecture**
The project uses a layered architecture based on **DDD principles**:

- **Bounded Contexts**
    - **Farm Management Context**
    - **Harvest Management Context**
    - **Sale Management Context**

- **Core Technologies:**
    - **Spring Boot**: REST API development
    - **MapStruct**: DTO-to-Entity conversion
    - **Lombok**: Simplifying entity boilerplate
    - **JUnit & Mockito**: Unit testing

---

## **Bounded Contexts**

### **1. Farm Management Context**
Manages the organizational structure of farms, fields, and trees.

#### **Entities**:
- **Farm**: Tracks farm details such as name, location, area, and creation date.
- **Field**: Represents fields within a farm, ensuring each field has a valid area.
- **Tree**: Tracks trees within fields, with attributes like planting date, age, and productivity.

#### **Aggregate Root**:
- **Farm** (manages relationships with `Field` and `Tree`).

#### **Key Features**:
- CRUD operations for farms, fields, and trees.
- Age and productivity calculation for trees.
- Validation:
    - Fields must have a minimum area of 0.1 hectares.
    - A farm can have at most 10 fields, and the sum of field areas must not exceed the farm's total area.

---

### **2. Harvest Management Context**
Handles tracking of harvests and detailed contributions from individual trees.

#### **Entities**:
- **Harvest**: Represents a seasonal harvest with attributes like season, harvest date, and total quantity.
- **HarvestDetail**: Tracks the quantity harvested from individual trees for a specific harvest.

#### **Aggregate Root**:
- **Harvest** (controls `HarvestDetail` relationships).

#### **Key Features**:
- CRUD operations for harvests.
- Seasonal constraints: Only one harvest per field per season.
- Detailed tracking of contributions from each tree.

---

### **3. Sale Management Context**
Focuses on the sale of harvested products and revenue calculations.

#### **Entities**:
- **Sale**: Represents a sale transaction, including date, quantity, unit price, client, and associated harvest.

#### **Aggregate Root**:
- **Sale** (linked to a `Harvest`).

#### **Key Features**:
- CRUD operations for sales.
- Revenue calculation:
    - **Revenue** = `Quantity Sold` * `Price Per Unit`.
- Validation to ensure sufficient quantity remains in the harvest before creating a sale.

---

## **Data Flow**

### **Inter-Context Relationships**
1. **Farm Management ↔ Harvest Management**:
    - `Farm` → Produces `Trees` → Contribute to `Harvests`.

2. **Harvest Management ↔ Sale Management**:
    - `Harvest` → Provides data for `Sale` (e.g., quantity available).

### **Diagram**
<img src="classeDiagram.png" alt="diagram" width="600">

## Technical Details
### Technology Stack
##### Backend:
Java, Spring Boot, JPA/Hibernate, MapStruct, Lombok.
##### Database:
PostgreSQL.
##### Testing Frameworks:
JUnit, Mockito.

### Project Layers
- **Controller Layer**: Handles incoming API requests.
- **Service Layer**: Contains business logic and DDD rules.
- **Repository Layer**: Manages database interactions.
- **Domain Layer**: Encapsulates entities, aggregates, and value objects.


## Developer
[Youssef Hihi](https://github.com/youssefhihi)