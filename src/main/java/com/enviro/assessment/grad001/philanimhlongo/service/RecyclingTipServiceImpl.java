package com.enviro.assessment.grad001.philanimhlongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.enviro.assessment.grad001.philanimhlongo.dao.RecyclingTipRepository;
import com.enviro.assessment.grad001.philanimhlongo.dao.WasteCategoryRepository;
import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;

@Service
public class RecyclingTipServiceImpl implements RecyclingTipService {

    private WasteCategoryRepository wasteCategoryRepository;
    private RecyclingTipRepository recyclingTipRepository;

    @Autowired
    public RecyclingTipServiceImpl(RecyclingTipRepository theRecyclingTipRepository,WasteCategoryRepository theWasteCategoryRepository){
        this.recyclingTipRepository=theRecyclingTipRepository;
        this.wasteCategoryRepository=theWasteCategoryRepository;
    }

    /**
 * Retrieve all RecyclingTips from the database.
 * 
 * @return List<RecyclingTip> a list of all RecyclingTips
 */
    @Override
    public List<RecyclingTip> findAll() {
        return recyclingTipRepository.findAll();
    }

    /**
 * Retrieve a RecyclingTip by its ID.
 * 
 * @param theId the ID of the RecyclingTip to retrieve
 * @return RecyclingTip the found RecyclingTip object
 * @throws RuntimeException if no RecyclingTip is found with the given ID
 */

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

    /**
 * Save or update a RecyclingTip in the database.
 * Ensures that the associated WasteCategory exists before saving.
 * 
 * @param theRecyclingTip the RecyclingTip to save or update
 * @return RecyclingTip the saved or updated RecyclingTip object
 * @throws RuntimeException if the associated WasteCategory is not found
 */
    @Transactional
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

    /**
 * Delete a RecyclingTip by its ID.
 * Verifies that the RecyclingTip exists before attempting to delete.
 * 
 * @param theId the ID of the RecyclingTip to delete
 * @throws RuntimeException if no RecyclingTip is found with the given ID
 */

    @Transactional
    @Override
    public void deleteById(int theId) {
        Optional<RecyclingTip> tip = recyclingTipRepository.findById(theId);
        if (!tip.isPresent()) {
            throw new RuntimeException("Recycling tip not found with id: " + theId);
        }
        recyclingTipRepository.deleteById(theId);
    }

    /**
 * Retrieve all RecyclingTips associated with a specific WasteCategory ID.
 * Verifies that the WasteCategory exists before fetching the tips.
 * 
 * @param wasteCategoryId the ID of the WasteCategory
 * @return List<RecyclingTip> a list of RecyclingTips associated with the WasteCategory
 * @throws RuntimeException if no WasteCategory is found with the given ID
 */
    @Override
    public List<RecyclingTip> findByRecyclingTipId(int wasteCategoryId) {
        Optional<WasteCategory> category = wasteCategoryRepository.findById(wasteCategoryId);
        if (!category.isPresent()) {
            throw new RuntimeException("Waste category not found with id: " + wasteCategoryId);
        }
        return recyclingTipRepository.findByWasteCategoryId(wasteCategoryId);
    }

    
    
    
}
