package com.enviro.assessment.grad001.philanimhlongo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;


public interface DisposalGuidelineRepository extends JpaRepository<DisposalGuideline,Integer>{
    List<DisposalGuideline> findByWasteCategoryId(int wasteCategoryId);
}
