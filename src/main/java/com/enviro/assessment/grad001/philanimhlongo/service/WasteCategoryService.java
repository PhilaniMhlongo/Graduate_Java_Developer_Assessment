package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;

import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

public interface WasteCategoryService {
    List<WasteCategory> findAll();
    WasteCategory findById(int theId);
    WasteCategory save(WasteCategory thewWasteCategory);
    void deleteById(int theId);
    WasteCategory update(WasteCategory theWasteCategory);
    Integer latestId();
}
