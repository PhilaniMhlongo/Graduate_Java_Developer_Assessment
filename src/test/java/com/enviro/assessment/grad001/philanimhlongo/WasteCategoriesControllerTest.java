package com.enviro.assessment.grad001.philanimhlongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class WasteCategoriesControllerTest {

    @PersistenceContext
    private EntityManager entityManager;

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
        // Clear any existing data
        entityManager.createQuery("DELETE FROM WasteCategory").executeUpdate();
        
        // Create and persist initial test data
        wasteCategory = new WasteCategory();
        wasteCategory.setName("Glass");
        wasteCategory.setDescription("Products made from silica including bottles, jars, and broken glass items");
        entityManager.persist(wasteCategory);
        entityManager.flush();
        entityManager.clear(); // Clear persistence context
        wasteCategory = entityManager.find(WasteCategory.class, wasteCategory.getId());
    }

    @Test
    public void getWasteCategoriesHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
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

        // Extract ID from response
        WasteCategory createdCategory = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            WasteCategory.class
        );

        // Verify using returned ID
        WasteCategory verifyCategory = wasteCategoryService.findById(createdCategory.getId());
        assertNotNull(verifyCategory, "Waste category should be valid.");
    }

    @Test
    public void updateWasteCategoryHttpRequest() throws Exception {
        // First create and persist a category we want to update
        WasteCategory initialCategory = new WasteCategory();
        initialCategory.setName("Original Glass");
        initialCategory.setDescription("Original description");
        entityManager.persist(initialCategory);
        entityManager.flush();
        
        // Get the ID of the persisted category
        Integer categoryId = initialCategory.getId();
        
        // Create update request
        WasteCategory updateCategory = new WasteCategory();
        updateCategory.setId(categoryId);
        updateCategory.setName("Updated Glass");
        updateCategory.setDescription("Updated glass description");
        
        // Perform update request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Glass"))
                .andExpect(jsonPath("$.description").value("Updated glass description"));
        
        // Verify the update in the database
        WasteCategory updatedCategory = wasteCategoryService.findById(categoryId);
        assertNotNull(updatedCategory, "Updated category should exist");
        assertEquals("Updated Glass", updatedCategory.getName(), "Name should be updated");
        assertEquals("Updated glass description", updatedCategory.getDescription(), "Description should be updated");
    }

    @Test
    @Transactional
    public void deleteWasteCategoryHttpRequest() throws Exception {
        // First persist a new category specifically for this test
        WasteCategory categoryToDelete = new WasteCategory();
        categoryToDelete.setName("Category to Delete");
        categoryToDelete.setDescription("This category will be deleted");
        entityManager.persist(categoryToDelete);
        entityManager.flush();
        
        Integer categoryId = categoryToDelete.getId();
        
        // Print initial state
        System.out.println("Before deletion - Category ID: " + categoryId);
        WasteCategory beforeDelete = wasteCategoryService.findById(categoryId);
        System.out.println("Category exists before deletion: " + beforeDelete);

        // Perform delete request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted category id - " + categoryId))
                .andReturn();
                
        System.out.println("Delete response: " + result.getResponse().getContentAsString());
        
        // Force a flush and clear of the persistence context
        entityManager.flush();
        entityManager.clear();
        
        // Try to find the category after deletion
        try {
            WasteCategory deletedCategory = wasteCategoryService.findById(categoryId);
            System.out.println("After deletion - Category still exists: " + deletedCategory);
            assertNull(deletedCategory, "Category should not exist after deletion");
        } catch (RuntimeException e) {
            System.out.println("After deletion - Exception occurred: " + e.getMessage());
            // This would actually be the expected behavior if findById throws exception for non-existent entities
        }
    }
    @Test
    public void getWasteCategoryByIdHttpRequest() throws Exception {
        // First verify the category exists
        WasteCategory category = wasteCategoryService.findById(wasteCategory.getId());
        assertNotNull(category, "Setup category should exist");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Glass"));
    }

    // @Test
    // public void getWasteCategoryNotFoundHttpRequest() throws Exception {
    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", 99))
    //             .andExpect(status().isNotFound())
    //             .andExpect(jsonPath("$.status").value(404))
    //             .andExpect(jsonPath("$.message").value("WasteCategory id not found - 99"));
    // }
}