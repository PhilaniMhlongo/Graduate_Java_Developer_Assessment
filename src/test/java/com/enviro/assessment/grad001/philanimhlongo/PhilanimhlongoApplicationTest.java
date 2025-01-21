package com.enviro.assessment.grad001.philanimhlongo;


import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.jdbc.core.JdbcTemplate;

@ActiveProfiles("test")
@SpringBootTest
public class PhilanimhlongoApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.script.create.category}")
    private String createCategoryScript;


    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute(createCategoryScript);
    }

    @Test
    public void findAllWasteCategories() {
        List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT * FROM waste_category");

        assertNotNull(categories, "Categories should not be null");
        assertFalse(categories.isEmpty(), "Categories list should not be empty");
        Map<String, Object> firstCategory = categories.get(0);
        assertEquals("Glass", firstCategory.get("name"), "First category name should be 'Glass'");
}
}
