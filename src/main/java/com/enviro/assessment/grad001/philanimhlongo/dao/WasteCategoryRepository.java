package com.enviro.assessment.grad001.philanimhlongo.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

public interface WasteCategoryRepository extends JpaRepository<WasteCategory,Integer> {
    
}
