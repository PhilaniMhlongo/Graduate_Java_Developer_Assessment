package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.philanimhlongo.dao.DisposalGuidelineRepository;

import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;

import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DisposalGuidelineServiceImpl implements DisposalGuidelineService {



    private WasteCategoryRepository wasteCategoryRepository;
    private DisposalGuidelineRepository disposalGuidelineRepository;

    @Autowired
    public DisposalGuidelineServiceImpl(DisposalGuidelineRepository theDisposalGuidelineRepository
                                        ,WasteCategoryRepository theWasteCategoryRepository){
        
        this.disposalGuidelineRepository=theDisposalGuidelineRepository;
        this.wasteCategoryRepository=theWasteCategoryRepository;
    }
    @Override
    public List<DisposalGuideline> findAll() {
       return disposalGuidelineRepository.findAll();
    }

    /**
 * Retrieve a DisposalGuideline by its ID.
 * @param theId the ID of the DisposalGuideline to retrieve
 * @return DisposalGuideline the found DisposalGuideline object
 * @throws RuntimeException if no DisposalGuideline is found with the given ID
 */
    @Override
    public DisposalGuideline findById(int theId) {
        Optional<DisposalGuideline> result=disposalGuidelineRepository.findById(theId);

        DisposalGuideline theDisposalGuideline=null;

        if(result.isPresent()){
            theDisposalGuideline=result.get();
        }
        else{
            // we didn't find wastecategory
            throw new RuntimeException("Did not find disposal guideline id - " + theId);
        }

        return theDisposalGuideline;
    }

    /**
 * Save or update a DisposalGuideline in the database.
 * Ensures that the associated WasteCategory exists before saving.
 * 
 * @param theDisposalGuideline the DisposalGuideline to save or update
 * @return DisposalGuideline the saved or updated DisposalGuideline object
 * @throws RuntimeException if the associated WasteCategory is not found
 */
    @Transactional
    @Override
    public DisposalGuideline save(DisposalGuideline theDisposalGuideline) {
       // Verify that the waste category exists
        if (theDisposalGuideline.getWasteCategory() != null && 
            theDisposalGuideline.getWasteCategory().getId() != 0) {
            
            Optional<WasteCategory> category = wasteCategoryRepository
                .findById(theDisposalGuideline.getWasteCategory().getId());
            
            if (!category.isPresent()) {
                throw new RuntimeException("Waste category not found with id: " 
                    + theDisposalGuideline.getWasteCategory().getId());
            }

            theDisposalGuideline.setWasteCategory(category.get());
        }
        return disposalGuidelineRepository.save(theDisposalGuideline);
    }

    /**
 * Delete a DisposalGuideline by its ID.
 * Verifies that the DisposalGuideline exists before attempting to delete.
 * 
 * @param theId the ID of the DisposalGuideline to delete
 * @throws RuntimeException if no DisposalGuideline is found with the given ID
 */

    @Transactional
    @Override
    public void deleteById(int theId) {
       Optional<DisposalGuideline> disposalGuideline= disposalGuidelineRepository.findById(theId);
        if (!disposalGuideline.isPresent()) {
            throw new RuntimeException("Disposal guideline not found with id: " + theId);
        }
        disposalGuidelineRepository.deleteById(theId);
    }

    /**
 * Retrieve all DisposalGuidelines associated with a specific WasteCategory ID.
 * Verifies that the WasteCategory exists before fetching the guidelines.
 * 
 * @param wasteCategoryId the ID of the WasteCategory
 * @return List<DisposalGuideline> a list of DisposalGuidelines associated with the WasteCategory
 * @throws RuntimeException if no WasteCategory is found with the given ID
 */

    @Override
    public List<DisposalGuideline> findByWasteCategoryId(int wasteCategoryId) {
        Optional<WasteCategory> category = wasteCategoryRepository.findById(wasteCategoryId);
        if (!category.isPresent()) {
            throw new RuntimeException("Waste category not found with id: " + wasteCategoryId);
        }
        return disposalGuidelineRepository.findByWasteCategoryId(wasteCategoryId);
    }
  
    
}
