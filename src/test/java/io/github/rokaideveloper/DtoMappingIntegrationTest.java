package io.github.rokaideveloper;

import io.github.rokaideveloper.config.DataLoader;
import io.github.rokaideveloper.dto.ProductDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class DtoMappingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    public void setup() throws Exception {
        dataLoader.run();
    }

    @Test
    public void testDtoMapping_GetAll() throws Exception {
        mockMvc.perform(get("/product-dtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].name", notNullValue()))
                .andExpect(jsonPath("$[0].price", notNullValue()))
                .andExpect(jsonPath("$[0].stock", notNullValue()))
                // Verify that internal fields are not exposed
                .andExpect(jsonPath("$[0].sku").doesNotExist())
                .andExpect(jsonPath("$[0].views").doesNotExist())
                .andExpect(jsonPath("$[0].category").doesNotExist());
    }

    @Test
    public void testDtoMapping_Create() throws Exception {
        mockMvc.perform(post("/product-dtos")
                .contentType("application/json")
                .content("{\"name\":\"New Product\",\"description\":\"Test\",\"price\":99.99,\"stock\":50}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.sku").doesNotExist());
    }

    @Test
    public void testDtoMapping_Update() throws Exception {
        String response = mockMvc.perform(post("/product-dtos")
                .contentType("application/json")
                .content("{\"name\":\"Test Product\",\"price\":50.00,\"stock\":10}"))
                .andReturn().getResponse().getContentAsString();

        com.fasterxml.jackson.databind.JsonNode jsonNode = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response);
        Long id = jsonNode.get("id").asLong();

        mockMvc.perform(put("/product-dtos/" + id)
                .contentType("application/json")
                .content("{\"name\":\"Updated Product\",\"price\":75.00,\"stock\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(75.00)));
    }

    @Test
    public void testDtoMapping_PartialUpdate() throws Exception {
        String response = mockMvc.perform(post("/product-dtos")
                .contentType("application/json")
                .content("{\"name\":\"Test Product\",\"price\":50.00,\"stock\":10}"))
                .andReturn().getResponse().getContentAsString();

        com.fasterxml.jackson.databind.JsonNode jsonNode = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response);
        Long id = jsonNode.get("id").asLong();

        mockMvc.perform(patch("/product-dtos/" + id)
                .contentType("application/json")
                .content("{\"price\":60.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.price", is(60.00)));
    }

    @Test
    public void testDtoMapping_Delete() throws Exception {
        String response = mockMvc.perform(post("/product-dtos")
                .contentType("application/json")
                .content("{\"name\":\"To Delete\",\"price\":10.00,\"stock\":5}"))
                .andReturn().getResponse().getContentAsString();

        com.fasterxml.jackson.databind.JsonNode jsonNode = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response);
        Long id = jsonNode.get("id").asLong();

        mockMvc.perform(delete("/product-dtos/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDtoMapping_FiltersWork() throws Exception {
        mockMvc.perform(get("/product-dtos?price__gt=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }
}
