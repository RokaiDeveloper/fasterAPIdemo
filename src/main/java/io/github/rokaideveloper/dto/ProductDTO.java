package io.github.rokaideveloper.dto;

import com.rokaidev.fasterapi.annotation.EntityMapping;
import com.rokaidev.fasterapi.annotation.FasterCRUD;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Manual DTO demonstrating the use of @EntityMapping.
 * 
 * This DTO maps to the Product entity but exposes only a subset of fields.
 * Fields like sku, views, and category relationships are not exposed,
 * providing a clean API contract.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FasterCRUD(path = "/product-dtos", isDto = true)
@EntityMapping(entity = io.github.rokaideveloper.model.Product.class)
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    private Integer stock;
}
