package io.github.rokaideveloper.controller;

import com.rokaidev.fasterapi.annotation.FasterFilter;
import com.rokaidev.fasterapi.web.FasterFilterQuery;
import io.github.rokaideveloper.model.Product;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Custom controller demonstrating lenient (non-strict) filter validation.
 *
 * When strict=false, invalid filters are silently ignored instead of
 * throwing an exception. This is useful when you want to accept
 * additional query parameters without failing validation.
 */
@RestController
@RequestMapping("/lenient-products")
public class LenientFilterController {

    private final SimpleJpaRepository<Product, Long> repository;

    public LenientFilterController(EntityManager entityManager) {
        JpaEntityInformation<Product, ?> entityInfo =
                JpaEntityInformationSupport.getEntityInformation(Product.class, entityManager);
        this.repository = new SimpleJpaRepository<>(entityInfo, entityManager);
    }

    /**
     * Demonstrates lenient validation (strict=false).
     * Invalid fields or operators will be silently ignored.
     * Only valid filters will be applied.
     */
    @GetMapping
    public List<Product> filterProductsLenient(
            @FasterFilter(
                    entity = Product.class,
                    fields = {"name", "price"},
                    strict = false
            ) FasterFilterQuery<Product> query) {
        return query.findAll();
    }
}
