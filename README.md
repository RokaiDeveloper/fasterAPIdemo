# FasterAPI Demo

This repository demonstrates the comprehensive features of the [FasterAPI](https://github.com/RokaiDeveloper/FasterAPI) library. FasterAPI is a Spring Boot starter designed to accelerate API development with automatic CRUD operations, dynamic filtering, sorting, pagination, and more.

## 🎯 Purpose

This project serves as a reference implementation showcasing FasterAPI capabilities with practical examples. It demonstrates **71.6% of public features** (48 out of 67) with full test coverage.

## 🚀 Tech Stack

- **Java 22**
- **Spring Boot 3.3.5**
- **FasterAPI Spring Boot Starter 1.0.4**
- **SpringDoc OpenAPI** (Swagger UI)
- **H2 Database** (in-memory for development)
- **Lombok**
- **Maven**

## 📦 Features Demonstrated

### Core Features
- **Automatic CRUD Operations** - GET, POST, PUT, PATCH, DELETE without boilerplate
- **Selective CRUD** - Enable/disable individual operations per entity
- **Dynamic Filtering** - Filter by any field with operators (eq, like, gt, lt, gte, lte)
- **Sorting** - Single and multi-field sorting (asc/desc)
- **Pagination** - Native Spring Data pagination support
- **Field Protection** - @ReadOnly and @JsonIgnore annotations
- **DTO Support** - Automatic (with @JsonIgnore) and manual (with @EntityMapping)
- **Validation** - Jakarta Validation annotations
- **OpenAPI Documentation** - Auto-generated Swagger UI
- **Custom Controllers** - @FasterFilter annotation for custom endpoints
- **FasterFilterQuery<T>** - High-level facade for filtered queries

### Advanced Features
- **Strict Validation** - HTTP 400 for invalid filters
- **Lenient Validation** - Silently ignore invalid filters
- **Filter Combinations** - Multiple criteria with AND logic
- **Boundary Value Testing** - Correct gt/gte/lt/lte behavior
- **Type Validation** - Operator compatibility with field types
- **JPA Relationships** - @ManyToOne and @OneToMany
- **Enum Fields** - Order.OrderStatus
- **Timestamp Fields** - BaseEntity with @CreationTimestamp

## 🏗️ Project Structure

```
src/main/java/io/github/rokaideveloper/
├── FasterApiDemoApplication.java    # Main application class
├── config/
│   └── DataLoader.java               # Test data initialization
├── controller/
│   ├── CustomProductController.java  # @FasterFilter demo (strict)
│   └── LenientFilterController.java  # @FasterFilter demo (lenient)
├── dto/
│   └── ProductDTO.java               # Manual DTO with @EntityMapping
└── model/
    ├── BaseEntity.java              # Base entity with timestamps
    ├── Category.java                # Category entity
    ├── Customer.java                # Customer entity
    ├── Order.java                   # Order entity (with enum)
    ├── OrderItem.java               # Order item entity
    ├── Product.java                 # Product entity
    └── ReadOnlyEntity.java          # Selective CRUD demo

src/test/java/io/github/rokaideveloper/
├── FasterFiltersIntegrationTest.java    # Comprehensive filter tests
├── SelectiveCrudIntegrationTest.java    # CRUD operation control tests
└── DtoMappingIntegrationTest.java       # DTO mapping tests
```

## 🛠️ Getting Started

### Prerequisites

- Java 17 or higher (demo uses Java 22)
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/RokaiDeveloper/fasterAPIdemo.git
cd fasterAPIdemo
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Accessing the API

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console

### Running Tests

```bash
mvn test
```

Test suites:
- `FasterFiltersIntegrationTest` - All filter operators and validation
- `SelectiveCrudIntegrationTest` - CRUD operation control
- `DtoMappingIntegrationTest` - Manual DTO mapping

## 📚 Usage Examples

### Basic CRUD Operations

```bash
# Create a new product
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":999.99,"stock":50}'

# Get all products
curl http://localhost:8080/products

# Get product by ID
curl http://localhost:8080/products/1

# Update a product (PUT - full replacement)
curl -X PUT http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop Pro","price":1099.99,"stock":45}'

# Partial update (PATCH)
curl -X PATCH http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{"price":1199.99}'

# Delete a product
curl -X DELETE http://localhost:8080/products/1
```

### Dynamic Filtering

```bash
# Equality filter (implicit eq)
curl http://localhost:8080/products?name=Laptop%20Pro

# Explicit eq operator
curl http://localhost:8080/products?name__eq=Laptop%20Pro

# LIKE operator (partial match)
curl http://localhost:8080/products?name__like=Laptop

# Greater than
curl http://localhost:8080/products?price__gt=100

# Less than
curl http://localhost:8080/products?price__lt=1000

# Greater than or equal
curl http://localhost:8080/products?price__gte=599.99

# Less than or equal
curl http://localhost:8080/products?price__lte=599.99

# Combine filters (AND logic)
curl http://localhost:8080/products?name__like=Laptop&price__gt=500

# Filter on boolean field
curl http://localhost:8080/customers?active=true

# Filter on integer field
curl http://localhost:8080/products?stock__gte=100
```

### Pagination and Sorting

```bash
# Pagination
curl http://localhost:8080/products?page=0&size=10

# Response includes: content, totalElements, totalPages, number, size, first, last

# Sorting (ascending)
curl http://localhost:8080/products?sort=price,asc

# Sorting (descending)
curl http://localhost:8080/products?sort=price,desc

# Multiple sort fields
curl http://localhost:8080/products?sort=name,asc&sort=price,desc

# Combine filters, pagination, and sorting
curl http://localhost:8080/products?price__gt=50&page=0&size=5&sort=price,asc
```

### Custom @FasterFilter Endpoints

```bash
# Custom endpoint with field whitelisting (strict validation)
curl http://localhost:8080/custom-products?name=Laptop%20Pro

# Custom endpoint with pagination
curl http://localhost:8080/custom-products/paginated?price__gt=100&page=0&size=5

# Lenient validation - invalid fields ignored
curl http://localhost:8080/lenient-products?invalidField=value
```

### DTO Endpoints

```bash
# Manual DTO endpoint - only exposes DTO fields
curl http://localhost:8080/product-dtos

# Create via DTO
curl -X POST http://localhost:8080/product-dtos \
  -H "Content-Type: application/json" \
  -d '{"name":"New Product","price":99.99,"stock":50}'

# Note: sku, views, category are not exposed in DTO
```

### Selective CRUD

```bash
# Read-only entity - only GET enabled
curl http://localhost:8080/readonly-products

# POST returns 404 (disabled)
curl -X POST http://localhost:8080/readonly-products \
  -H "Content-Type: application/json" \
  -d '{"name":"Test"}'

# PUT returns 404 (disabled)
curl -X PUT http://localhost:8080/readonly-products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated"}'

# DELETE returns 404 (disabled)
curl -X DELETE http://localhost:8080/readonly-products/1
```

## 🔍 Filter Operators Reference

| Operator | Syntax | Description | Field Types |
|----------|--------|-------------|-------------|
| eq | `field=value` or `field__eq=value` | Exact equality | All types |
| like | `field__like=value` | Partial match (contains) | String only |
| gt | `field__gt=value` | Greater than | Numeric only |
| lt | `field__lt=value` | Less than | Numeric only |
| gte | `field__gte=value` | Greater than or equal | Numeric only |
| lte | `field__lte=value` | Less than or equal | Numeric only |

## ✅ Validation Examples

### Strict Validation (default)

```bash
# Invalid field - returns HTTP 400
curl http://localhost:8080/custom-products?invalidField=value
# Response: Campo 'invalidField' não é permitido para filtro. Campos permitidos: [name, price, stock]

# Invalid operator - returns HTTP 400
curl http://localhost:8080/custom-products?name__invalid=value
# Response: Operador 'invalid' não suportado. Operadores válidos: eq, like, gt, lt, gte, lte

# Type mismatch - returns HTTP 400
curl http://localhost:8080/custom-products?price__like=100
# Response: Operador 'like' só pode ser usado em campos do tipo String

# Invalid numeric value - returns HTTP 400
curl http://localhost:8080/custom-products?price__gt=not-a-number
# Response: Valor 'not-a-number' inválido para o campo 'price'
```

### Lenient Validation

```bash
# Invalid fields are silently ignored
curl http://localhost:8080/lenient-products?invalidField=value&name=Laptop
# Returns products matching 'name', ignores 'invalidField'
```

## 📊 Coverage Summary

- **Total Features Identified**: 67
- **Covered and Validated**: 48 (71.6%)
- **Not Covered**: 5 (7.5%) - Many-to-many relationships, date/enum filters, optimistic locking
- **Not Applicable**: 3 (4.5%) - Authentication, auditing (app responsibility)

## 🔗 Links

- [FasterAPI Framework](https://github.com/RokaiDeveloper/FasterAPI)
- [FasterAPI Documentation](https://github.com/RokaiDeveloper/FasterAPI/blob/main/README.md)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## 🤝 Contributing

Contributions welcome! If you find a FasterAPI feature that isn't demonstrated, please add it. Ensure tests are included for new features.

## 📄 License

This project is licensed under the MIT License.
