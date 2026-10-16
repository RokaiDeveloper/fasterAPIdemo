package io.github.rokaideveloper.model;

import com.rokaidev.fasterapi.annotation.FasterCRUD;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity demonstrating selective CRUD operations.
 * Only GET operations are enabled - POST, PUT, PATCH, DELETE are disabled.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FasterCRUD(
    path = "/readonly-products",
    enableGet = true,
    enablePost = false,
    enablePut = false,
    enablePatch = false,
    enableDelete = false
)
public class ReadOnlyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}
