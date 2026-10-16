package io.github.rokaideveloper.controller;

import com.rokaidev.fasterapi.annotation.FasterFilter;
import com.rokaidev.fasterapi.web.FasterFilterQuery;
import io.github.rokaideveloper.model.Product;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Custom controller demonstrating the use of @FasterFilter annotation
 * and FasterFilterQuery<T> facade for validated dynamic filtering.
 *
 * This shows how to use FasterFilters in custom endpoints beyond the
 * auto-generated CRUD endpoints.
 */
@RestController
@RequestMapping("/custom-products")
public class CustomProductController {

    private final SimpleJpaRepository<Product, Long> repository;

    public CustomProductController(EntityManager entityManager) {
        JpaEntityInformation<Product, ?> entityInfo =
                JpaEntityInformationSupport.getEntityInformation(Product.class, entityManager);
        this.repository = new SimpleJpaRepository<>(entityInfo, entityManager);
    }

    /**
     * Demonstrates the high-level FasterFilterQuery facade.
     * Only allows filtering on 'name' and 'price' fields.
     * Strict validation is enabled (default).
     */
    @GetMapping
    public List<Product> filterProducts(
            @FasterFilter(
                    entity = Product.class,
                    fields = {"name", "price", "stock"}
            ) FasterFilterQuery<Product> query) {
        return query.findAll();
    }

    /**
     * Demonstrates FasterFilterQuery with pagination.
     * Combines dynamic filtering with Spring Data pagination.
     */
    @GetMapping("/paginated")
    public Page<Product> filterProductsPaginated(
            @FasterFilter(
                    entity = Product.class,
                    fields = {"name", "price", "stock"}
            ) FasterFilterQuery<Product> query,
            Pageable pageable) {
        return query.findAll(pageable);
    }
}
