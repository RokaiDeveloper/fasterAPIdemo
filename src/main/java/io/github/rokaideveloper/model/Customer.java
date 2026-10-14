package io.github.rokaideveloper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rokaidev.fasterapi.annotation.FasterCRUD;
import com.rokaidev.fasterapi.annotation.ReadOnly;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FasterCRUD(path = "/customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 11, max = 14, message = "Phone must be between 11 and 14 characters")
    private String phone;

    @JsonIgnore
    private String password;

    @ReadOnly
    private String customerCode;

    @Column(unique = true)
    private String cpf;

    private Boolean active = true;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
