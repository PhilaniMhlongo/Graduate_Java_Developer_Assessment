package com.enviro.assessment.grad001.philanimhlongo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;

@SpringBootTest
public class WasteCategoryServiceTest {

    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    private WasteCategoryService wasteCategoryService;

    private WasteCategory testCategory;

    @BeforeEach
    public void setUp() {
        // Clean up before each test
        wasteCategoryRepository.deleteAll();
        
        // Create test data
        testCategory = new WasteCategory("Plastic", "All types of plastic waste");
        
        // Add a guideline
        DisposalGuideline guideline = new DisposalGuideline("Clean and sort plastics by type");
        testCategory.addDisposalGuideline(guideline);
        
        // Add a recycling tip
        RecyclingTip tip = new RecyclingTip("Remove caps and labels before recycling");
        testCategory.addRecyclingTip(tip);
        
        // Save the category
        testCategory = wasteCategoryRepository.save(testCategory);
    }

    @Test
    public void findAllWasteCategories() {
        List<WasteCategory> categories = wasteCategoryService.findAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.size() >= 1);
    }

    @Test
    public void findWasteCategoryById() {
        WasteCategory foundCategory = wasteCategoryService.findById(testCategory.getId());
        assertNotNull(foundCategory);
        assertEquals("Plastic", foundCategory.getName());
        assertEquals("All types of plastic waste", foundCategory.getDescription());
    }

    @Test
    public void createWasteCategory() {
        WasteCategory newCategory = new WasteCategory("Glass", "All types of glass waste");
        WasteCategory savedCategory = wasteCategoryService.save(newCategory);
        
        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals("Glass", savedCategory.getName());
        assertEquals("All types of glass waste", savedCategory.getDescription());
    }

    @Test
    public void updateWasteCategory() {
        testCategory.setDescription("Updated plastic waste description");
        WasteCategory updatedCategory = wasteCategoryService.save(testCategory);
        
        assertNotNull(updatedCategory);
        assertEquals("Updated plastic waste description", updatedCategory.getDescription());
    }

    @Test
    public void deleteWasteCategory() {
        // Get the ID before deletion
        int categoryId = testCategory.getId();
        
        // Delete the category
        wasteCategoryService.deleteById(categoryId);
        
        // Verify deletion
        Optional<WasteCategory> deletedCategory = wasteCategoryRepository.findById(categoryId);
        assertFalse(deletedCategory.isPresent());
    }


}