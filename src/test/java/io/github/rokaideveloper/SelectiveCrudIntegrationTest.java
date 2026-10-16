package io.github.rokaideveloper;

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

import static org.hamcrest.Matchers.containsString;
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
public class SelectiveCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    public void setup() throws Exception {
        dataLoader.run();
    }

    @Test
    public void testSelectiveCrud_GetEnabled() throws Exception {
        mockMvc.perform(get("/readonly-products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSelectiveCrud_PostDisabled() throws Exception {
        mockMvc.perform(post("/readonly-products")
                .contentType("application/json")
                .content("{\"name\":\"Test\",\"description\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSelectiveCrud_PutDisabled() throws Exception {
        mockMvc.perform(put("/readonly-products/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated\",\"description\":\"Updated\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSelectiveCrud_PatchDisabled() throws Exception {
        mockMvc.perform(patch("/readonly-products/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSelectiveCrud_DeleteDisabled() throws Exception {
        mockMvc.perform(delete("/readonly-products/1"))
                .andExpect(status().isNotFound());
    }
}
