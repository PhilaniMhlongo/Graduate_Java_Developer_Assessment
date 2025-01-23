package com.enviro.assessment.grad001.philanimhlongo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.service.RecyclingTipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class RecyclingTipControllerTest {

    private static final String BASE_URL = "/api/v1/tips";

    @Value("${sql.script.create.recycling.tip}")
    private String createTipSQL;

    @Value("${sql.script.create.waste.category}")
    private String createCategorySQL;

    @Value("${sql.script.delete.recycling.tip}")
    private String deleteTipSQL;

    @Value("${sql.script.delete.waste.category}")
    private String deleteCategorySQL;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RecyclingTipService recyclingTipService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RecyclingTip recyclingTip;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute(deleteTipSQL);
        jdbcTemplate.execute(deleteCategorySQL);
        jdbcTemplate.execute(createCategorySQL);
        jdbcTemplate.execute(createTipSQL);
        recyclingTip = recyclingTipService.findById(1);
    }

    @Test
    public void getRecyclingTipsHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tip").value("Rinse glass containers before recycling"));
    }

    @Test
    public void createRecyclingTipHttpRequest() throws Exception {
        RecyclingTip newTip = new RecyclingTip();
        newTip.setTip("Remove caps from plastic bottles");

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Remove caps from plastic bottles"));

        RecyclingTip verifyTip = recyclingTipService.findById(2);
        assertNotNull(verifyTip, "Recycling tip should be valid");
        assertEquals("Remove caps from plastic bottles", verifyTip.getTip());
    }

    @Test
    public void updateRecyclingTipHttpRequest() throws Exception {
        RecyclingTip updateTip = new RecyclingTip();
        updateTip.setId(1);
        updateTip.setTip("Updated recycling tip");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateTip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip").value("Updated recycling tip"));

        RecyclingTip updatedTip = recyclingTipService.findById(1);
        assertNotNull(updatedTip, "Updated tip should exist");
        assertEquals("Updated recycling tip", updatedTip.getTip());
    }

    @Test
    public void deleteRecyclingTipHttpRequest() throws Exception {
        RecyclingTip tipBeforeDelete = recyclingTipService.findById(1);
        assertNotNull(tipBeforeDelete, "Tip should exist before deletion");

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted category id - 1"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRecyclingTipByIdHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tip").value("Rinse glass containers before recycling"));
    }

    @Test
    public void createRecyclingTipWithInvalidDataHttpRequest() throws Exception {
        RecyclingTip invalidTip = new RecyclingTip();
        invalidTip.setTip(""); // Invalid empty tip

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTip)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNonExistentTipHttpRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllTipsEmptyDatabaseHttpRequest() throws Exception {
        jdbcTemplate.execute(deleteTipSQL);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
