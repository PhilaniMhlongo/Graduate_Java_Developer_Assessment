package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;


import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;

public interface RecyclingTipService {
    
    List<RecyclingTip> findAll();
    RecyclingTip findById(int theId);
    RecyclingTip save(RecyclingTip theRecyclingTip);
    void deleteById(int theId);
    List<RecyclingTip> findByRecyclingTipId(int wasteCategoryId);
    
}
