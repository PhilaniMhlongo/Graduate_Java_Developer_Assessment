package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;

import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

public class WasteCategoryServiceImpl implements WasteCategoryService {

    private WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    public WasteCategoryServiceImpl(WasteCategoryRepository theWasteCategoryRepository)
    {
        this.wasteCategoryRepository=theWasteCategoryRepository;
    }

    @Override
    public List<WasteCategory> findAll() {
      
       return wasteCategoryRepository.findAll();
    }

    @Override
    public WasteCategory findById(int theId) {
    
        Optional<WasteCategory> result=wasteCategoryRepository.findById(theId);

        WasteCategory theWasteCategory=null;

        if(result.isPresent()){
            theWasteCategory=result.get();
        }
        else{
            // we didn't find wastecategory
            throw new RuntimeException("Did not find wastecategory id - " + theId);
        }

        return theWasteCategory;
    }

    @Override
    public WasteCategory save(WasteCategory thewWasteCategory) {
        return wasteCategoryRepository.save(thewWasteCategory);
    }

    @Override
    public void deleteById(int theId) {
        wasteCategoryRepository.deleteById(theId);
    }
    
}
