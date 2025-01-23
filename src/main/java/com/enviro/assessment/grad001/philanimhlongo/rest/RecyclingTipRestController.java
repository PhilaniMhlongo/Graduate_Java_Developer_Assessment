package com.enviro.assessment.grad001.philanimhlongo.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
import com.enviro.assessment.grad001.philanimhlongo.service.RecyclingTipService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class RecyclingTipRestController {

    private RecyclingTipService  recyclingTipService;


    @Autowired
    public RecyclingTipRestController(RecyclingTipService  theRecyclingTipService){
        recyclingTipService= theRecyclingTipService;
    }

 
    /**
 * Expose an endpoint to retrieve all RecyclingTips.
 * 
 * @return List<RecyclingTip> a list of all RecyclingTips
 */

    @GetMapping("/tips")
    public List<RecyclingTip> findAll() {
        return recyclingTipService.findAll();
    }

/**
 * Expose an endpoint to retrieve a RecyclingTip by its ID.
 * 
 * @param tipId the ID of the RecyclingTip to retrieve
 * @return RecyclingTip the found RecyclingTip object
 * @throws NotFoundException if no RecyclingTip is found with the given ID
 */
     @GetMapping("/tips/{tipId}")
     public RecyclingTip getRecyclingTip(@PathVariable int tipId ) {
 
        RecyclingTip theRecyclingTip =recyclingTipService.findById(tipId);
 
         if (theRecyclingTip == null) {
             throw new NotFoundException("Recycling tip id not found - " + tipId);
         }
 
         return theRecyclingTip;
     }
 /**
 * Expose an endpoint to add a new RecyclingTip.
 * If an ID is provided in the request body, it is reset to 0 to ensure a new entity is created.
 * 
 * @param theRecyclingTip the RecyclingTip object to add
 * @return RecyclingTip the saved RecyclingTip object
 */

     @PostMapping("/tips")
     public RecyclingTip addRecyclingTip(@RequestBody RecyclingTip theRecyclingTip) {
 
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
         theRecyclingTip.setId(0);
 
         RecyclingTip dbtheRecyclingTip = recyclingTipService.save(theRecyclingTip);
 
         return dbtheRecyclingTip;
     }
 

 
     /**
 * Expose an endpoint to update an existing RecyclingTip.
 * The method first verifies if the RecyclingTip exists before updating.
 * 
 * @param theRecyclingTip the updated RecyclingTip object
 * @return RecyclingTip the saved (updated) RecyclingTip object
 * @throws NotFoundException if no RecyclingTip is found with the provided ID
 */

     @PutMapping("/tips")
     public RecyclingTip updateRecyclingTip(@Valid @RequestBody RecyclingTip theRecyclingTip) {

        RecyclingTip existingRecyclingTip =recyclingTipService.findById(theRecyclingTip.getId());

        if (existingRecyclingTip==null) {
            throw new NotFoundException("RecyclingTip not found ");
        }
 
        RecyclingTip dbRecyclingTip = recyclingTipService.save(theRecyclingTip);
 
         return dbRecyclingTip;
     }
 
     // add mapping for DELETE /categories/{categoryId} - delete tip
 
     /**
 * Expose an endpoint to delete a RecyclingTip by its ID.
 * Verifies that the RecyclingTip exists before attempting to delete.
 * 
 * @param tipId the ID of the RecyclingTip to delete
 * @return String a confirmation message indicating the RecyclingTip was deleted
 * @throws NotFoundException if no RecyclingTip is found with the given ID
 */

     @DeleteMapping("/tips/{tipId}")
     public String deleteRecyclingTip(@PathVariable int tipId ) {
 
        RecyclingTip tempRecyclingTip = recyclingTipService.findById(tipId );
 
         // throw exception if null
 
         if (tempRecyclingTip == null) {
             throw new NotFoundException("Recycling tip id not found - " + tipId);
         }
 
         recyclingTipService.deleteById(tipId);
 
         return "Deleted category id - " + tipId;
     }
 
}
