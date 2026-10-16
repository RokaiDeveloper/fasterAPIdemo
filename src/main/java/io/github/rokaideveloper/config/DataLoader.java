package io.github.rokaideveloper.config;

import io.github.rokaideveloper.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final EntityManager entityManager;

    public DataLoader(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists to avoid duplicate inserts
        Long existingCategories = entityManager.createQuery("SELECT COUNT(c) FROM Category c", Long.class).getSingleResult();
        if (existingCategories > 0) {
            System.out.println("=== Data already exists, skipping DataLoader ===");
            return;
        }

        // Load categories
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Electronic devices and accessories");
        entityManager.persist(electronics);

        Category clothing = new Category();
        clothing.setName("Clothing");
        clothing.setDescription("Apparel and fashion items");
        entityManager.persist(clothing);

        Category books = new Category();
        books.setName("Books");
        books.setDescription("Books and publications");
        entityManager.persist(books);

        // Load products with various price ranges for filter testing
        Product p1 = new Product();
        p1.setName("Laptop Pro");
        p1.setDescription("High-performance laptop");
        p1.setPrice(new BigDecimal("2999.99"));
        p1.setStock(50);
        p1.setCategory(electronics);
        entityManager.persist(p1);

        Product p2 = new Product();
        p2.setName("Budget Laptop");
        p2.setDescription("Affordable laptop for everyday use");
        p2.setPrice(new BigDecimal("599.99"));
        p2.setStock(100);
        p2.setCategory(electronics);
        entityManager.persist(p2);

        Product p3 = new Product();
        p3.setName("Wireless Mouse");
        p3.setDescription("Ergonomic wireless mouse");
        p3.setPrice(new BigDecimal("29.99"));
        p3.setStock(200);
        p3.setCategory(electronics);
        entityManager.persist(p3);

        Product p4 = new Product();
        p4.setName("Gaming Keyboard");
        p4.setDescription("Mechanical gaming keyboard");
        p4.setPrice(new BigDecimal("149.99"));
        p4.setStock(75);
        p4.setCategory(electronics);
        entityManager.persist(p4);

        Product p5 = new Product();
        p5.setName("T-Shirt");
        p5.setDescription("Cotton t-shirt");
        p5.setPrice(new BigDecimal("19.99"));
        p5.setStock(150);
        p5.setCategory(clothing);
        entityManager.persist(p5);

        Product p6 = new Product();
        p6.setName("Jeans");
        p6.setDescription("Denim jeans");
        p6.setPrice(new BigDecimal("79.99"));
        p6.setStock(80);
        p6.setCategory(clothing);
        entityManager.persist(p6);

        Product p7 = new Product();
        p7.setName("Java Programming Book");
        p7.setDescription("Learn Java programming");
        p7.setPrice(new BigDecimal("49.99"));
        p7.setStock(60);
        p7.setCategory(books);
        entityManager.persist(p7);

        Product p8 = new Product();
        p8.setName("Spring Boot Guide");
        p8.setDescription("Complete Spring Boot guide");
        p8.setPrice(new BigDecimal("39.99"));
        p8.setStock(90);
        p8.setCategory(books);
        entityManager.persist(p8);

        // Load customers
        Customer c1 = new Customer();
        c1.setName("John Doe");
        c1.setEmail("john.doe@example.com");
        c1.setPhone("5511999999999");
        c1.setCpf("12345678901");
        c1.setActive(true);
        entityManager.persist(c1);

        Customer c2 = new Customer();
        c2.setName("Jane Smith");
        c2.setEmail("jane.smith@example.com");
        c2.setPhone("5511888888888");
        c2.setCpf("98765432109");
        c2.setActive(true);
        entityManager.persist(c2);

        Customer c3 = new Customer();
        c3.setName("Inactive Customer");
        c3.setEmail("inactive@example.com");
        c3.setPhone("5511777777777");
        c3.setCpf("11122233344");
        c3.setActive(false);
        entityManager.persist(c3);

        // Load read-only entity data
        ReadOnlyEntity roe1 = new ReadOnlyEntity();
        roe1.setName("System Configuration");
        roe1.setDescription("Global system settings");
        entityManager.persist(roe1);

        ReadOnlyEntity roe2 = new ReadOnlyEntity();
        roe2.setName("Default Settings");
        roe2.setDescription("Default application settings");
        entityManager.persist(roe2);

        entityManager.flush();
        entityManager.clear();

        System.out.println("=== Data loaded successfully ===");
        System.out.println("Categories: 3");
        System.out.println("Products: 8");
        System.out.println("Customers: 3");
        System.out.println("ReadOnlyEntities: 2");
    }
}
