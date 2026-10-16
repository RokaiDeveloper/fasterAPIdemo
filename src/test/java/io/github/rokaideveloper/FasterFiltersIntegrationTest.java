package io.github.rokaideveloper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rokaideveloper.config.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class FasterFiltersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        dataLoader.run();
    }

    // === Auto-generated CRUD endpoint filter tests ===

    @Test
    public void testFilter_NoFilters_ReturnsAll() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void testFilter_EqualityOperator() throws Exception {
        mockMvc.perform(get("/products?name=Laptop Pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Laptop Pro")));
    }

    @Test
    public void testFilter_ExplicitEqOperator() throws Exception {
        mockMvc.perform(get("/products?name__eq=Laptop Pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Laptop Pro")));
    }

    @Test
    public void testFilter_LikeOperator() throws Exception {
        mockMvc.perform(get("/products?name__like=Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFilter_LikeOperatorPartialMatch() throws Exception {
        mockMvc.perform(get("/products?name__like=Book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFilter_GreaterThanOperator() throws Exception {
        mockMvc.perform(get("/products?price__gt=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[?(@.price > 100)]", hasSize(greaterThan(0))));
    }

    @Test
    public void testFilter_LessThanOperator() throws Exception {
        mockMvc.perform(get("/products?price__lt=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testFilter_GreaterThanOrEqualOperator() throws Exception {
        mockMvc.perform(get("/products?price__gte=599.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFilter_LessThanOrEqualOperator() throws Exception {
        mockMvc.perform(get("/products?price__lte=49.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testFilter_BoundaryValue_EqualToBoundary() throws Exception {
        mockMvc.perform(get("/products?price__gte=29.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void testFilter_BoundaryValue_JustAboveBoundary() throws Exception {
        mockMvc.perform(get("/products?price__gt=29.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    public void testFilter_CombinedFilters() throws Exception {
        mockMvc.perform(get("/products?name__like=Laptop&price__gt=500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFilter_BooleanField() throws Exception {
        mockMvc.perform(get("/customers?active=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFilter_BooleanFieldFalse() throws Exception {
        mockMvc.perform(get("/customers?active=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testFilter_IntegerField() throws Exception {
        mockMvc.perform(get("/products?stock__gte=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    public void testFilter_NoResults() throws Exception {
        mockMvc.perform(get("/products?name=NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // === Pagination and Sorting with Filters ===

    @Test
    public void testFilter_WithPagination() throws Exception {
        mockMvc.perform(get("/products?price__gt=0&page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalElements", is(8)))
                .andExpect(jsonPath("$.totalPages", is(2)));
    }

    @Test
    public void testFilter_WithSorting() throws Exception {
        mockMvc.perform(get("/products?sort=price,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price", is(19.99)))
                .andExpect(jsonPath("$[7].price", is(2999.99)));
    }

    @Test
    public void testFilter_WithPaginationAndSorting() throws Exception {
        mockMvc.perform(get("/products?page=0&size=5&sort=price,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].price", is(2999.99)))
                .andExpect(jsonPath("$.content[4].price", is(49.99)));
    }

    @Test
    public void testFilter_CombinedWithPaginationAndSorting() throws Exception {
        mockMvc.perform(get("/products?price__gt=30&page=0&size=3&sort=price,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements", greaterThan(0)));
    }

    // === Custom @FasterFilter endpoint tests ===

    @Test
    public void testCustomFilter_ValidField() throws Exception {
        mockMvc.perform(get("/custom-products?name=Laptop Pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Laptop Pro")));
    }

    @Test
    public void testCustomFilter_InvalidField_Strict() throws Exception {
        mockMvc.perform(get("/custom-products?description=High-performance"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("não é permitido")));
    }

    @Test
    public void testCustomFilter_InvalidOperator_Strict() throws Exception {
        mockMvc.perform(get("/custom-products?name__invalid=value"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("não suportado")));
    }

    @Test
    public void testCustomFilter_LikeOnNumericField_Strict() throws Exception {
        mockMvc.perform(get("/custom-products?price__like=100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("só pode ser usado em campos do tipo String")));
    }

    @Test
    public void testCustomFilter_ComparisonOnStringField_Strict() throws Exception {
        mockMvc.perform(get("/custom-products?name__gt=A"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("só pode ser usado em campos numéricos")));
    }

    @Test
    public void testCustomFilter_InvalidNumericValue_Strict() throws Exception {
        mockMvc.perform(get("/custom-products?price__gt=not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("inválido")));
    }

    @Test
    public void testCustomFilter_WithPagination() throws Exception {
        mockMvc.perform(get("/custom-products/paginated?price__gt=0&page=0&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements", is(8)));
    }

    // === Lenient validation tests ===

    @Test
    public void testLenientFilter_InvalidField_Ignored() throws Exception {
        // Should not throw error, just ignore the invalid field
        mockMvc.perform(get("/lenient-products?description=High-performance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void testLenientFilter_InvalidOperator_Ignored() throws Exception {
        // Should not throw error, just ignore the invalid operator
        mockMvc.perform(get("/lenient-products?name__invalid=value"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void testLenientFilter_ValidFieldStillWorks() throws Exception {
        mockMvc.perform(get("/lenient-products?name=Laptop Pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Laptop Pro")));
    }

    @Test
    public void testLenientFilter_CombinedValidAndInvalid() throws Exception {
        // Valid filter should work, invalid one ignored
        mockMvc.perform(get("/lenient-products?name__like=Laptop&invalidField=value"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
