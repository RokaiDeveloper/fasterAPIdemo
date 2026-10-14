# FasterAPI Demo

This repository aims to explore and demonstrate **100% of the features and functions** provided by the [FasterAPI](https://github.com/rokai-developer/fasterapi) library. FasterAPI is a Spring Boot starter designed to accelerate API development with automatic CRUD operations, filtering, sorting, pagination, and more.

## 🎯 Purpose

The goal of this project is to serve as a comprehensive reference implementation showcasing every capability of the FasterAPI library. Each feature is demonstrated with practical examples to help developers understand how to leverage the library in their own projects.

## 🚀 Tech Stack

- **Java 22**
- **Spring Boot 3.3.5**
- **FasterAPI Spring Boot Starter 1.0.2**
- **SpringDoc OpenAPI** (Swagger UI)
- **H2 Database** (in-memory for development)
- **Lombok**
- **Maven**

## 📦 Features Demonstrated

This project covers the following FasterAPI capabilities:

- **Automatic CRUD Operations** - Create, Read, Update, Delete without boilerplate code
- **Dynamic Filtering** - Filter entities by any field using query parameters
- **Sorting** - Sort results by multiple fields
- **Pagination** - Built-in pagination support
- **Entity Relationships** - One-to-many, many-to-one, and many-to-many mappings
- **Validation** - Request validation using Spring Boot Validation
- **OpenAPI Documentation** - Auto-generated API documentation
- **Custom Queries** - Beyond standard CRUD operations

## 🏗️ Project Structure

```
src/main/java/io/github/rokaideveloper/
├── FasterApiDemoApplication.java    # Main application class
└── model/                           # Domain models
    ├── BaseEntity.java              # Base entity with common fields
    ├── Category.java                # Category entity
    ├── Customer.java                # Customer entity
    ├── Order.java                   # Order entity
    ├── OrderItem.java               # Order item entity
    └── Product.java                 # Product entity
```

## 🛠️ Getting Started

### Prerequisites

- Java 22 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/rokai-developer/fasterAPIdemo.git
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

### Running Tests

```bash
mvn test
```

Or use the provided test script:
```bash
./test-api.sh
```

## 📚 Usage Examples

### Basic CRUD Operations

```bash
# Create a new product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":999.99,"stock":50}'

# Get all products with pagination
curl http://localhost:8080/api/products?page=0&size=10

# Filter products
curl http://localhost:8080/api/products?name=Laptop&minPrice=500

# Sort products
curl http://localhost:8080/api/products?sort=price,desc

# Update a product
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop Pro","price":1099.99,"stock":45}'

# Delete a product
curl -X DELETE http://localhost:8080/api/products/1
```

## 🤝 Contributing

Contributions are welcome! If you find a FasterAPI feature that isn't demonstrated in this repository, please feel free to add it.

## 📄 License

This project is licensed under the MIT License.

## 🔗 Links

- [FasterAPI Documentation](https://github.com/rokai-developer/fasterapi)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

# Versão em Português

# FasterAPI Demo

Este repositório tem como objetivo explorar e demonstrar **100% das funcionalidades e recursos** fornecidos pela biblioteca [FasterAPI](https://github.com/rokai-developer/fasterapi). FasterAPI é um starter do Spring Boot projetado para acelerar o desenvolvimento de APIs com operações CRUD automáticas, filtragem, ordenação, paginação e muito mais.

## 🎯 Objetivo

O objetivo deste projeto é servir como uma implementação de referência abrangente, mostrando todas as capacidades da biblioteca FasterAPI. Cada recurso é demonstrado com exemplos práticos para ajudar os desenvolvedores a entender como aproveitar a biblioteca em seus próprios projetos.

## 🚀 Stack Tecnológico

- **Java 22**
- **Spring Boot 3.3.5**
- **FasterAPI Spring Boot Starter 1.0.2**
- **SpringDoc OpenAPI** (Swagger UI)
- **H2 Database** (em memória para desenvolvimento)
- **Lombok**
- **Maven**

## 📦 Recursos Demonstrados

Este projeto cobre as seguintes capacidades do FasterAPI:

- **Operações CRUD Automáticas** - Criar, Ler, Atualizar, Deletar sem código repetitivo
- **Filtragem Dinâmica** - Filtrar entidades por qualquer campo usando parâmetros de consulta
- **Ordenação** - Ordenar resultados por múltiplos campos
- **Paginação** - Suporte de paginação integrado
- **Relacionamentos de Entidades** - Mapeamentos um-para-muitos, muitos-para-um e muitos-para-muitos
- **Validação** - Validação de requisições usando Spring Boot Validation
- **Documentação OpenAPI** - Documentação de API gerada automaticamente
- **Consultas Personalizadas** - Além das operações CRUD padrão

## 🏗️ Estrutura do Projeto

```
src/main/java/io/github/rokaideveloper/
├── FasterApiDemoApplication.java    # Classe principal da aplicação
└── model/                           # Modelos de domínio
    ├── BaseEntity.java              # Entidade base com campos comuns
    ├── Category.java                # Entidade Category
    ├── Customer.java                # Entidade Customer
    ├── Order.java                   # Entidade Order
    ├── OrderItem.java               # Entidade OrderItem
    └── Product.java                 # Entidade Product
```

## 🛠️ Como Começar

### Pré-requisitos

- Java 22 ou superior
- Maven 3.6 ou superior

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/rokai-developer/fasterAPIdemo.git
cd fasterAPIdemo
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`

### Acessando a API

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Executando Testes

```bash
mvn test
```

Ou use o script de teste fornecido:
```bash
./test-api.sh
```

## 📚 Exemplos de Uso

### Operações CRUD Básicas

```bash
# Criar um novo produto
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":999.99,"stock":50}'

# Obter todos os produtos com paginação
curl http://localhost:8080/api/products?page=0&size=10

# Filtrar produtos
curl http://localhost:8080/api/products?name=Laptop&minPrice=500

# Ordenar produtos
curl http://localhost:8080/api/products?sort=price,desc

# Atualizar um produto
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop Pro","price":1099.99,"stock":45}'

# Deletar um produto
curl -X DELETE http://localhost:8080/api/products/1
```

## 🤝 Contribuindo

Contribuições são bem-vindas! Se você encontrar um recurso do FasterAPI que não esteja demonstrado neste repositório, sinta-se à vontade para adicioná-lo.

## 📄 Licença

Este projeto é licenciado sob a Licença MIT.

## 🔗 Links

- [Documentação do FasterAPI](https://github.com/rokai-developer/fasterapi)
- [Documentação do Spring Boot](https://spring.io/projects/spring-boot)
