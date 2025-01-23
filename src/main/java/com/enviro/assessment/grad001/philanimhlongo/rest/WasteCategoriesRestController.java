package com.enviro.assessment.grad001.philanimhlongo.rest;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class WasteCategoriesRestController {
    private WasteCategoryService wasteCategoryService;


    @Autowired
    public WasteCategoriesRestController(WasteCategoryService theWasteCategoryService){
        wasteCategoryService=theWasteCategoryService;
    }

    /**
 * Expose an endpoint to retrieve all WasteCategories.
 * 
 * @return List<WasteCategory> a list of all WasteCategories
 */

    @GetMapping("/categories")
    public List<WasteCategory> findAll() {
        return wasteCategoryService.findAll();
    }


   /**
 * Expose an endpoint to retrieve a WasteCategory by its ID.
 * 
 * @param categoryId the ID of the WasteCategory to retrieve
 * @return WasteCategory the found WasteCategory object
 * @throws NotFoundException if no WasteCategory is found with the given ID
 */
     @GetMapping("/categories/{categoryId}")
     public WasteCategory getWasteCategory(@PathVariable int categoryId ) {
 
        WasteCategory theWasteCategory = wasteCategoryService.findById(categoryId);
 
         if (theWasteCategory == null) {
             throw new NotFoundException("WasteCategory id not found - " + categoryId);
         }
 
         return theWasteCategory;
     }
 
     /**
 * Expose an endpoint to add a new WasteCategory.
 * If an ID is provided in the request body, it is replaced with the next available ID based on the current highest ID.
 * 
 * @param theWasteCategory the WasteCategory object to add
 * @return WasteCategory the saved WasteCategory object
 */

     @PostMapping("/categories")
     public WasteCategory addCategory(@Valid @RequestBody WasteCategory theWasteCategory) {
 
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
        theWasteCategory.setId(wasteCategoryService.latestId());
 
         // Get the category with the highest current id to determine the next available id

        // Save the category
        WasteCategory dbWasteCategory = wasteCategoryService.save(theWasteCategory);

        return dbWasteCategory;
     }
 
     /**
 * Expose an endpoint to update an existing WasteCategory.
 * The method first verifies if the WasteCategory exists before updating.
 * 
 * @param theWasteCategory the updated WasteCategory object
 * @return WasteCategory the saved (updated) WasteCategory object
 * @throws NotFoundException if no WasteCategory is found with the provided ID
 */

     @PutMapping("/categories")
     public WasteCategory updateCategory(@Valid @RequestBody WasteCategory theWasteCategory) {

        WasteCategory existingCategory = wasteCategoryService.findById(theWasteCategory.getId());

        if (existingCategory==null) {
            throw new NotFoundException("WasteCategory not found ");
        }
 
         WasteCategory dbWasteCategory = wasteCategoryService.save(theWasteCategory);
 
         return dbWasteCategory;
     }
 
   /**
 * Expose an endpoint to delete a WasteCategory by its ID.
 * Verifies that the WasteCategory exists before attempting to delete.
 * 
 * @param categoryId the ID of the WasteCategory to delete
 * @return String a confirmation message indicating the WasteCategory was deleted
 * @throws NotFoundException if no WasteCategory is found with the given ID
 */
     @DeleteMapping("/categories/{categoryId}")
     public String deleteWasteCategory(@PathVariable int categoryId ) {
 
        WasteCategory tempWasteCategory = wasteCategoryService.findById(categoryId );
 
         // throw exception if null
 
         if (tempWasteCategory == null) {
             throw new NotFoundException("WasteCategory id not found - " + categoryId);
         }
 
         wasteCategoryService.deleteById(categoryId);
 
         return "Deleted category id - " + categoryId;
     }
 
}
