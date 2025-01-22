package com.enviro.assessment.grad001.philanimhlongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class WasteCategoriesControllerTest {

    @Value("${sql.script.create.category}")
    private String createCategorySQL;

    @Value("${sql.script.delete.category}")
    private String deleteCategorySQL;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Mock
    WasteCategoryService wasteCategoryServiceMock;

    @Autowired
    private WasteCategoryService wasteCategoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private WasteCategory wasteCategory;

    @BeforeEach
    public void setup() {
        // Clean up existing data
        jdbcTemplate.execute(deleteCategorySQL);
        
        // Create initial test data using SQL script
        jdbcTemplate.execute(createCategorySQL);
        
        // Retrieve the created category for test verification
        wasteCategory = wasteCategoryService.findById(1);
    }

    @Test
    public void getWasteCategoriesHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Glass"))
                .andExpect(jsonPath("$[0].description").value("Products made from silica including bottles, jars, and broken glass items"));
    }

    @Test
    public void createWasteCategoryHttpRequest() throws Exception {
        WasteCategory newCategory = new WasteCategory();
        newCategory.setName("Plastic");
        newCategory.setDescription("Products made from synthetic polymers");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Plastic"))
                .andExpect(jsonPath("$.description").value("Products made from synthetic polymers"))
                .andReturn();

        WasteCategory createdCategory = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            WasteCategory.class
        );

        WasteCategory verifyCategory = wasteCategoryService.findById(createdCategory.getId());
        assertNotNull(verifyCategory, "Waste category should be valid.");
        assertEquals("Plastic", verifyCategory.getName());
    }

    @Test
    public void updateWasteCategoryHttpRequest() throws Exception {
        WasteCategory updateCategory = new WasteCategory();
        updateCategory.setId(1);
        updateCategory.setName("Updated Glass");
        updateCategory.setDescription("Updated glass description");
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Glass"))
                .andExpect(jsonPath("$.description").value("Updated glass description"));
        
        WasteCategory updatedCategory = wasteCategoryService.findById(1);
        assertNotNull(updatedCategory, "Updated category should exist");
        assertEquals("Updated Glass", updatedCategory.getName());
        assertEquals("Updated glass description", updatedCategory.getDescription());
    }

    @Test
    public void deleteWasteCategoryHttpRequest() throws Exception {
        // Verify category exists before deletion
        WasteCategory categoryBeforeDelete = wasteCategoryService.findById(1);
        assertNotNull(categoryBeforeDelete, "Category should exist before deletion");
        assertEquals("Glass", categoryBeforeDelete.getName(), "Category should have expected name before deletion");

        // Perform delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted category id - 1"))
                .andReturn();

        List<WasteCategory> allCategories = wasteCategoryService.findAll();
        System.out.println("Categories before delete: " + allCategories);


        // // Verify category no longer exists (check database directly)
        // WasteCategory deletedCategory = wasteCategoryService.findById(1);
        // assertNull(deletedCategory, "Category should not exist after deletion");

        // Verify GET request returns 404 for deleted category
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", 1))
                .andExpect(status().isNotFound());
    }



    @Test
    public void getWasteCategoryByIdHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Glass"))
                .andExpect(jsonPath("$.description").value("Products made from silica including bottles, jars, and broken glass items"));
    }

    @Test
    public void getWasteCategoryNotFoundHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", 99))
                .andExpect(status().isNotFound());
    }
    @Test
public void createWasteCategoryWithInvalidDataHttpRequest() throws Exception {
    WasteCategory invalidCategory = new WasteCategory();
    // Not setting required fields

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidCategory)))
            .andExpect(status().isBadRequest());
}

@Test
public void updateNonExistentCategoryHttpRequest() throws Exception {
    WasteCategory nonExistentCategory = new WasteCategory();
    nonExistentCategory.setId(999);
    nonExistentCategory.setName("Non-existent");
    nonExistentCategory.setDescription("This category doesn't exist");

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nonExistentCategory)))
            .andExpect(status().isNotFound());
}

@Test
public void createDuplicateWasteCategoryHttpRequest() throws Exception {
    // First, create a category
    WasteCategory category = new WasteCategory();
    category.setName("Plastic");
    category.setDescription("Plastic waste");

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(category)))
            .andExpect(status().isOk());

    // Try to create another category with the same name
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(category)))
            .andExpect(status().isBadRequest());
}

    @Test
    public void updateCategoryWithInvalidDataHttpRequest() throws Exception {
        WasteCategory invalidUpdate = new WasteCategory();
        invalidUpdate.setId(1);
        invalidUpdate.setName(""); // Invalid empty name
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUpdate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNonExistentCategoryHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCategoriesEmptyDatabaseHttpRequest() throws Exception {
        // First delete all existing categories
        jdbcTemplate.execute(deleteCategorySQL);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}