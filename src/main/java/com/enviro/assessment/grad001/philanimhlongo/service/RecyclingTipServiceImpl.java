package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.enviro.assessment.grad001.philanimhlongo.dao.RecyclingTipRepository;
import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;


public class RecyclingTipServiceImpl implements RecyclingTipService {

    private WasteCategoryRepository wasteCategoryRepository;
    private RecyclingTipRepository recyclingTipRepository;

    @Autowired
    public RecyclingTipServiceImpl(RecyclingTipRepository theRecyclingTipRepository,WasteCategoryRepository theWasteCategoryRepository){
        this.recyclingTipRepository=theRecyclingTipRepository;
        this.wasteCategoryRepository=theWasteCategoryRepository;
    }

    @Override
    public List<RecyclingTip> findAll() {
        return recyclingTipRepository.findAll();
    }

    @Override
    public RecyclingTip findById(int theId) {
        Optional<RecyclingTip> result=recyclingTipRepository.findById(theId);

        RecyclingTip theRecyclingTip=null;

        if(result.isPresent()){
            theRecyclingTip=result.get();
        }
        else{
            // we didn't find wastecategory
            throw new RuntimeException("Did not find recycling tip id - " + theId);
        }

        return theRecyclingTip;
    }

    @Override
    public RecyclingTip save(RecyclingTip theRecyclingTip) {
       // Verify that the waste category exists
        if (theRecyclingTip.getWasteCategory() != null && 
            theRecyclingTip.getWasteCategory().getId() != 0) {
            
            Optional<WasteCategory> category = wasteCategoryRepository
                .findById(theRecyclingTip.getWasteCategory().getId());
            
            if (!category.isPresent()) {
                throw new RuntimeException("Waste category not found with id: " 
                    + theRecyclingTip.getWasteCategory().getId());
            }

            theRecyclingTip.setWasteCategory(category.get());
        }
        return recyclingTipRepository.save(theRecyclingTip);
    }

    @Override
    public void deleteById(int theId) {
        Optional<RecyclingTip> tip = recyclingTipRepository.findById(theId);
        if (!tip.isPresent()) {
            throw new RuntimeException("Recycling tip not found with id: " + theId);
        }
        recyclingTipRepository.deleteById(theId);
    }

    @Override
    public List<RecyclingTip> findByRecyclingTipId(int wasteCategoryId) {
        Optional<WasteCategory> category = wasteCategoryRepository.findById(wasteCategoryId);
        if (!category.isPresent()) {
            throw new RuntimeException("Waste category not found with id: " + wasteCategoryId);
        }
        return recyclingTipRepository.findByWasteCategoryId(wasteCategoryId);
    }

    
    
    
}
