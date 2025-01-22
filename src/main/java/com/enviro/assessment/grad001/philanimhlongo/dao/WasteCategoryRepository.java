package com.enviro.assessment.grad001.philanimhlongo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

public interface WasteCategoryRepository extends JpaRepository<WasteCategory,Integer> {
    @Query("SELECT MAX(w.id) FROM WasteCategory w")
    Integer findLatestId();
}
