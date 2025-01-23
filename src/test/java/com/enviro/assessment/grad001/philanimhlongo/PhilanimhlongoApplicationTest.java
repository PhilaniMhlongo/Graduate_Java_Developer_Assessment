package com.enviro.assessment.grad001.philanimhlongo;


import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.enviro.assessment.grad001.philanimhlongo.dao.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.philanimhlongo.dao.RecyclingTipRepository;
import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;
import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.service.DisposalGuidelineService;
import com.enviro.assessment.grad001.philanimhlongo.service.RecyclingTipService;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;

import jakarta.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;

@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class PhilanimhlongoApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DisposalGuidelineRepository disposalGuidelineRepository;

    @Autowired
    private RecyclingTipRepository recyclingTipRepository;


    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;


    @Autowired
    private DisposalGuidelineService disposalGuidelineService;

    @Autowired
    private RecyclingTipService recyclingTipService;

    @Autowired
    private WasteCategoryService wasteCategoryService;

    private WasteCategory testCategory;
    

    @Value("${sql.script.create.category}")
    private String sqlAddCategory;

    @Value("${sql.script.create.disposal.guideline}")
    private String sqlAddGuideline;

    @Value("${sql.script.create.recycling.tip}")
    private String sqlAddTip;


    @Value("${sql.script.delete.category}")
    private String sqlDeleteCategory;

    @Value("${sql.script.create.disposal.guideline}")
    private String sqlDeleteGuideline;

    @Value("${sql.script.create.recycling.tip}")
    private String sqlDeleteTip;

@BeforeEach
    @Transactional
    public void setupDatabase() {
        // Clean up existing data
        disposalGuidelineRepository.deleteAll();
        recyclingTipRepository.deleteAll();
        wasteCategoryRepository.deleteAll();

        // Create test category first
        testCategory = new WasteCategory("Plastics", "Materials made of synthetic or semi-synthetic organic compounds that can be molded into solid objects");
        testCategory = wasteCategoryRepository.save(testCategory);
    }

    @AfterEach
    @Transactional
    public void cleanupDatabase() {
        disposalGuidelineRepository.deleteAll();
        recyclingTipRepository.deleteAll();
        wasteCategoryRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testCategoryInitialization() {
        assertNotNull(testCategory.getId(), "Category ID should not be null");
        assertTrue(testCategory.getId() > 0, "Category ID should be positive");
        
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(testCategory.getId());
        assertTrue(retrieved.isPresent(), "Should be able to retrieve test category");
        assertEquals("Plastics", retrieved.get().getName(), "Category name should match");
    }

    @Test
    @Transactional
    public void testCreateCategory() {
        WasteCategory newCategory = new WasteCategory("Batteries", 
            "Disposable and rechargeable batteries of various types");
        WasteCategory saved = wasteCategoryService.save(newCategory);
        
        assertNotNull(saved.getId(), "Saved category should have an ID");
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent(), "Should be able to retrieve saved category");
        assertEquals("Batteries", retrieved.get().getName(), "Category name should match");
    }

    @Test
    @Transactional
    public void testAddDisposalGuideline() {
        // First refresh the test category from the database
        WasteCategory category = wasteCategoryRepository.findById(testCategory.getId()).get();
        
        // Create and set up the guideline
        DisposalGuideline guideline = new DisposalGuideline();
        guideline.setGuideline("Avoid disposing of non-recyclable plastics.");
        
        // Use the convenience method from WasteCategory to maintain bidirectional relationship
        category.addDisposalGuideline(guideline);
        
        // Save the category which will cascade to the guideline
        wasteCategoryRepository.save(category);
        
        // Clear and refresh from database to ensure we see the actual persisted state
        wasteCategoryRepository.flush();
        // wasteCategoryRepository.clear();
        
        // Verify the relationship
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(category.getId());
        assertTrue(retrieved.isPresent(), "Category should exist");
        assertFalse(retrieved.get().getDisposalGuidelines().isEmpty(), 
                    "Category should have guidelines");
        
        DisposalGuideline retrievedGuideline = retrieved.get().getDisposalGuidelines().get(0);
        assertEquals("Avoid disposing of non-recyclable plastics.", retrievedGuideline.getGuideline(),
                    "Guideline text should match");
        assertEquals(category.getId(), retrievedGuideline.getWasteCategory().getId(),
                    "Guideline should reference correct category");
    }

    @Test
    @Transactional
    public void testAddRecyclingTip() {
        // First refresh the test category from the database
        WasteCategory category = wasteCategoryRepository.findById(testCategory.getId()).get();
        
        // Create and set up the recycling tip
        RecyclingTip tip = new RecyclingTip();
        tip.setTip("Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!");
        
        // Use the convenience method from WasteCategory to maintain bidirectional relationship
        category.addRecyclingTip(tip);
        
        // Save the category which will cascade to the tip
        wasteCategoryRepository.save(category);
        
        // Clear and refresh from database to ensure we see the actual persisted state
        wasteCategoryRepository.flush();
        // wasteCategoryRepository.clear();
        
        // Verify the relationship
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(category.getId());
        assertTrue(retrieved.isPresent(), "Category should exist");
        assertFalse(retrieved.get().getRecyclingTips().isEmpty(), 
                    "Category should have recycling tips");
        
        RecyclingTip retrievedTip = retrieved.get().getRecyclingTips().get(0);
        assertEquals("Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!", retrievedTip.getTip(),
                    "Tip text should match");
        assertEquals(category.getId(), retrievedTip.getWasteCategory().getId(),
                    "Tip should reference correct category");
    }

    @Test
    @Transactional
    public void testUpdateCategory() {
        String newName = "Batteries";
        testCategory.setName(newName);
        WasteCategory updated = wasteCategoryService.update(testCategory);
        
        assertEquals(newName, updated.getName(), "Category name should be updated");
        
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(testCategory.getId());
        assertTrue(retrieved.isPresent(), "Updated category should exist");
        assertEquals(newName, retrieved.get().getName(), "Retrieved category should have updated name");
    }

    @Test
    @Transactional
    public void testDeleteCategory() {
        wasteCategoryRepository.delete(testCategory);
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(testCategory.getId());
        assertFalse(retrieved.isPresent(), "Category should be deleted");
    }

    @Test
    @Transactional
    public void testCreateRecyclingTip() {
        RecyclingTip tip = new RecyclingTip("Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics.");
        tip.setWasteCategory(testCategory);
        RecyclingTip saved = recyclingTipRepository.save(tip);
        
        assertNotNull(saved.getId(), "Saved tip should have an ID");
        assertEquals("Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics.", saved.getTip(), "Tip content should match");
        assertEquals(testCategory.getId(), saved.getWasteCategory().getId(), "Category reference should match");
    }



    @Test
    @Transactional
    public void testUpdateRecyclingTip() {
        // Create initial tip
        RecyclingTip tip = new RecyclingTip("Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics.");
        tip.setWasteCategory(testCategory);
        RecyclingTip saved = recyclingTipRepository.save(tip);
        
        // Update tip
        String updatedContent = "Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!";
        saved.setTip(updatedContent);
        recyclingTipRepository.save(saved);
        
        // Verify update
        Optional<RecyclingTip> retrieved = recyclingTipRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent(), "Updated tip should exist");
        assertEquals(updatedContent, retrieved.get().getTip(), "Tip content should be updated");
    }

    @Test
    @Transactional
    public void testDeleteRecyclingTip() {
        // Create a tip
        RecyclingTip tip = new RecyclingTip("Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!");
        tip.setWasteCategory(testCategory);
        RecyclingTip saved = recyclingTipRepository.save(tip);
        
        // Verify it exists
        assertTrue(recyclingTipRepository.findById(saved.getId()).isPresent(), 
                  "Tip should exist before deletion");
        
        // Delete the tip
        recyclingTipRepository.delete(saved);
        
        // Verify it's deleted
        assertFalse(recyclingTipRepository.findById(saved.getId()).isPresent(), 
                   "Tip should not exist after deletion");
        
        // Verify category still exists
        Optional<WasteCategory> category = wasteCategoryRepository.findById(testCategory.getId());
        assertTrue(category.isPresent(), "Category should still exist after tip deletion");
    }

    @Test
    @Transactional
    public void testMultipleRecyclingTipsForCategory() {
        // Add multiple tips to the same category
        RecyclingTip tip1 = new RecyclingTip("Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics.");
        RecyclingTip tip2 = new RecyclingTip("Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!");
        RecyclingTip tip3 = new RecyclingTip("Use a washing bag for synthetic clothes and avoid microbeads in personal care products to reduce microplastic pollution.");
        
        testCategory.addRecyclingTip(tip1);
        testCategory.addRecyclingTip(tip2);
        testCategory.addRecyclingTip(tip3);
        
        WasteCategory savedCategory = wasteCategoryRepository.save(testCategory);
        
        // Verify all tips were saved
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(savedCategory.getId());
        assertTrue(retrieved.isPresent(), "Category should exist");
        assertEquals(3, retrieved.get().getRecyclingTips().size(), 
                    "Category should have exactly 3 tips");
        
        // Verify content of tips
        List<String> tipContents = retrieved.get().getRecyclingTips()
            .stream()
            .map(RecyclingTip::getTip)
            .toList();
        
        assertTrue(tipContents.contains("Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics."), "Should contain first tip");
        assertTrue(tipContents.contains("Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!"), "Should contain second tip");
        assertTrue(tipContents.contains("Use a washing bag for synthetic clothes and avoid microbeads in personal care products to reduce microplastic pollution."), "Should contain third tip");
    }

    @Test
    @Transactional
    public void testCreateDisposalGuideline() {
        DisposalGuideline guideline = new DisposalGuideline(
            "Avoid disposing of non-recyclable plastics.");
        guideline.setWasteCategory(testCategory);
        DisposalGuideline saved = disposalGuidelineRepository.save(guideline);
        
        assertNotNull(saved.getId(), "Saved guideline should have an ID");
        assertEquals("Avoid disposing of non-recyclable plastics.", 
                    saved.getGuideline(), "Guideline content should match");
        assertEquals(testCategory.getId(), saved.getWasteCategory().getId(), 
                    "Category reference should match");
    }


    @Test
    @Transactional
    public void testUpdateDisposalGuideline() {
        // Create initial guideline
        DisposalGuideline guideline = new DisposalGuideline(
            "Clean and dry plastic items before disposing of them.");
        guideline.setWasteCategory(testCategory);
        DisposalGuideline saved = disposalGuidelineRepository.save(guideline);
        
        // Update guideline
        String updatedContent = "Avoid disposing of non-recyclable plastics.";
        saved.setGuideline(updatedContent);
        disposalGuidelineRepository.save(saved);
        
        // Verify update
        Optional<DisposalGuideline> retrieved = disposalGuidelineRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent(), "Updated guideline should exist");
        assertEquals(updatedContent, retrieved.get().getGuideline(), 
                    "Guideline content should be updated");
    }

    @Test
    @Transactional
    public void testDeleteDisposalGuideline() {
        // Create a guideline
        DisposalGuideline guideline = new DisposalGuideline(
            "Break down the box completely flat");
        guideline.setWasteCategory(testCategory);
        DisposalGuideline saved = disposalGuidelineRepository.save(guideline);
        
        // Verify it exists
        assertTrue(disposalGuidelineRepository.findById(saved.getId()).isPresent(), 
                  "Guideline should exist before deletion");
        
        // Delete the guideline
        disposalGuidelineRepository.delete(saved);
        
        // Verify it's deleted
        assertFalse(disposalGuidelineRepository.findById(saved.getId()).isPresent(), 
                   "Guideline should not exist after deletion");
        
        // Verify category still exists
        Optional<WasteCategory> category = wasteCategoryRepository.findById(testCategory.getId());
        assertTrue(category.isPresent(), "Category should still exist after guideline deletion");
    }

    @Test
    @Transactional
    public void testMultipleDisposalGuidelinesForCategory() {
        // Add multiple guidelines to the same category
        DisposalGuideline guideline1 = new DisposalGuideline(
            "Break down the box completely flat");
        DisposalGuideline guideline2 = new DisposalGuideline(
            "Remove any tape, staples, or plastic materials");
        DisposalGuideline guideline3 = new DisposalGuideline(
            "Keep dry and clean");
        
        testCategory.addDisposalGuideline(guideline1);
        testCategory.addDisposalGuideline(guideline2);
        testCategory.addDisposalGuideline(guideline3);
        
        WasteCategory savedCategory = wasteCategoryRepository.save(testCategory);
        
        // Verify all guidelines were saved
        Optional<WasteCategory> retrieved = wasteCategoryRepository.findById(savedCategory.getId());
        assertTrue(retrieved.isPresent(), "Category should exist");
        assertEquals(3, retrieved.get().getDisposalGuidelines().size(), 
                    "Category should have exactly 3 guidelines");
        
        // Verify content of guidelines
        List<String> guidelineContents = retrieved.get().getDisposalGuidelines()
            .stream()
            .map(DisposalGuideline::getGuideline)
            .toList();
        
        assertTrue(guidelineContents.contains("Break down the box completely flat"), 
                  "Should contain first guideline");
        assertTrue(guidelineContents.contains("Remove any tape, staples, or plastic materials"), 
                  "Should contain second guideline");
        assertTrue(guidelineContents.contains("Keep dry and clean"), 
                  "Should contain third guideline");
    }

    @Test
    @Transactional
    public void testDisposalGuidelineWithSpecialCharacters() {
        String guidelineWithSpecialChars = "Special characters test: !@#$%^&*()_+ \n" +
            "Multiple lines and symbols: €£¥•";
        DisposalGuideline guideline = new DisposalGuideline(guidelineWithSpecialChars);
        guideline.setWasteCategory(testCategory);
        
        DisposalGuideline saved = disposalGuidelineRepository.save(guideline);
        
        Optional<DisposalGuideline> retrieved = disposalGuidelineRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent(), "Guideline should exist");
        assertEquals(guidelineWithSpecialChars, retrieved.get().getGuideline(), 
                    "Special characters should be preserved");
    }
}
