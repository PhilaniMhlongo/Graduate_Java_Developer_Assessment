package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;

import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;

public interface DisposalGuidelineService {

    List<DisposalGuideline> findAll();
    DisposalGuideline findById(int theId);
    DisposalGuideline save(DisposalGuideline theDisposalGuideline);
    // DisposalGuideline update(DisposalGuideline theDisposalGuideline);
    void deleteById(int theId);
    List<DisposalGuideline> findByWasteCategoryId(int wasteCategoryId);
    
}
