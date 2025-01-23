package com.enviro.assessment.grad001.philanimhlongo.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;

import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
import com.enviro.assessment.grad001.philanimhlongo.service.DisposalGuidelineService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class DisposalGuidelineRestController {
    private DisposalGuidelineService  disposalGuidelineService;


    @Autowired
    public DisposalGuidelineRestController(DisposalGuidelineService  theDisposalGuidelineService){
        disposalGuidelineService= theDisposalGuidelineService;
    }

/**
 * Expose an endpoint to retrieve all DisposalGuidelines.
 * 
 * @return List<DisposalGuideline> a list of all DisposalGuidelines
 */

    @GetMapping("/guidelines")
    public List<DisposalGuideline> findAll() {
             // expose "/guidelines" and return a list of guidelines
        return disposalGuidelineService.findAll();
    }


  

    /**
 * Expose an endpoint to retrieve a DisposalGuideline by its ID.
 * 
 * @param guidelineId the ID of the DisposalGuideline to retrieve
 * @return DisposalGuideline the found DisposalGuideline object
 * @throws NotFoundException if no DisposalGuideline is found with the given ID
 */

     @GetMapping("/guidelines/{guidelineId}")
     public DisposalGuideline getDisposalGuideline(@PathVariable int guidelineId ) {
    // add mapping for GET /guidelines/{guidelineId}
        DisposalGuideline theDisposalGuideline =disposalGuidelineService.findById(guidelineId);
 
         if (theDisposalGuideline == null) {
             throw new NotFoundException("Disposal Guideline id not found - " + guidelineId);
         }
 
         return theDisposalGuideline;
     }
 
     
 
     /**
 * Expose an endpoint to add a new DisposalGuideline.
 * If an ID is provided in the request body, it is reset to 0 to ensure a new entity is created.
 * 
 * @param theDisposalGuideline the DisposalGuideline object to add
 * @return DisposalGuideline the saved DisposalGuideline object
 */

     @PostMapping("/guidelines")
     public DisposalGuideline addDisposalGuideline(@Valid @RequestBody DisposalGuideline theDisposalGuideline) {
 // add mapping for POST /guidelines - add new guideline
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
         theDisposalGuideline.setId(0);
 
         DisposalGuideline dbtheDisposalGuideline = disposalGuidelineService.save(theDisposalGuideline);
 
         return dbtheDisposalGuideline;
     }
 
 
     /**
 * Expose an endpoint to update an existing DisposalGuideline.
 * The method first verifies if the DisposalGuideline exists before updating.
 * 
 * @param theDisposalGuideline the updated DisposalGuideline object
 * @return DisposalGuideline the saved (updated) DisposalGuideline object
 * @throws NotFoundException if no DisposalGuideline is found with the provided ID
 */

     @PutMapping("/guidelines")
     public DisposalGuideline updateDisposalGuideline(@Valid @RequestBody DisposalGuideline theDisposalGuideline) {
 
     // add mapping for PUT /guidelines - update existing guidelines

        DisposalGuideline existingDisposalGuideline =disposalGuidelineService.findById(theDisposalGuideline.getId());

        if (existingDisposalGuideline==null) {
            throw new NotFoundException("Disposal Guideline not found ");
        }
        DisposalGuideline dbDisposalGuideline = disposalGuidelineService.save(theDisposalGuideline);
 
         return dbDisposalGuideline;
     }
 
     
 

     /**
 * Expose an endpoint to delete a DisposalGuideline by its ID.
 * Verifies if the DisposalGuideline exists before attempting to delete.
 * 
 * @param guidelineId the ID of the DisposalGuideline to delete
 * @return String a confirmation message indicating the DisposalGuideline was deleted
 * @throws NotFoundException if no DisposalGuideline is found with the given ID
 */

     @DeleteMapping("/guidelines/{guidelineId}")
     public String deleteDisposalGuideline(@PathVariable int guidelineId ) {
 // add mapping for DELETE /guidelines/{guidelineId} - delete guideline
        DisposalGuideline tempDisposalGuideline = disposalGuidelineService.findById(guidelineId );
 
         // throw exception if null
 
         if (tempDisposalGuideline == null) {
             throw new NotFoundException("Disposal guideline id not found - " + guidelineId);
         }
 
         disposalGuidelineService.deleteById(guidelineId);
 
         return "Deleted category id - " + guidelineId;
     }
 
}
