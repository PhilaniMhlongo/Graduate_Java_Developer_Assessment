package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
@Service
public class WasteCategoryServiceImpl implements WasteCategoryService {

    private WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    public WasteCategoryServiceImpl(WasteCategoryRepository theWasteCategoryRepository)
    {
        this.wasteCategoryRepository=theWasteCategoryRepository;
    }

    /**
 * Retrieve all WasteCategories from the database.
 * 
 * @return List<WasteCategory> a list of all WasteCategories
 */

    @Override
    public List<WasteCategory> findAll() {
      
       return wasteCategoryRepository.findAll();
    }

    /**
 * Retrieve a WasteCategory by its ID.
 * 
 * @param theId the ID of the WasteCategory to retrieve
 * @return WasteCategory the found WasteCategory object
 * @throws NotFoundException if no WasteCategory is found with the given ID
 */

    @Override
    public WasteCategory findById(int theId) {
    
        Optional<WasteCategory> result=wasteCategoryRepository.findById(theId);

        WasteCategory theWasteCategory=null;

        if(result.isPresent()){
            theWasteCategory=result.get();
        }
        else{
            // we didn't find wastecategory
            throw new NotFoundException("Did not find wastecategory id - " + theId);
        }

        return theWasteCategory;
    }

    /**
 * Save or update a WasteCategory in the database.
 * Ensures that a WasteCategory with the same name does not already exist before saving.
 * 
 * @param theWasteCategory the WasteCategory to save or update
 * @return WasteCategory the saved or updated WasteCategory object
 * @throws IllegalArgumentException if a WasteCategory with the same name already exists
 */

    @Transactional
    @Override
    public WasteCategory save(WasteCategory theWasteCategory) {
        if (wasteCategoryRepository.existsByName(theWasteCategory.getName())) {
            throw new IllegalArgumentException("Category with name '" + theWasteCategory.getName() + "' already exists.");
        }
        return wasteCategoryRepository.save(theWasteCategory);
    }

    @Transactional
    @Override
    public WasteCategory update(WasteCategory theWasteCategory) {
        if (wasteCategoryRepository.existsByName(theWasteCategory.getName())) {
           
            return wasteCategoryRepository.save(theWasteCategory);
        }
        else{
            throw new IllegalArgumentException("Category with name '" + theWasteCategory.getName() + "' does not exists.");
        }
        
    }

    /**
 * Delete a WasteCategory by its ID.
 * 
 * @param theId the ID of the WasteCategory to delete
 * @throws EmptyResultDataAccessException if no WasteCategory is found with the given ID
 */

    @Transactional
    @Override
    public void deleteById(int theId) {
        wasteCategoryRepository.deleteById(theId);
    }

    /**
 * Retrieve the latest (most recent) ID from the WasteCategory repository.
 * 
 * @return Integer the latest ID
 */

    @Override
    public Integer latestId() {
        return wasteCategoryRepository.findLatestId();
    }
    
}
