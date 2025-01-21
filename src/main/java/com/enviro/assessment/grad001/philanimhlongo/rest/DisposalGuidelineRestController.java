package com.enviro.assessment.grad001.philanimhlongo.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.DisposalGuideline;

import com.enviro.assessment.grad001.philanimhlongo.exception.ErrorResponse;
import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
import com.enviro.assessment.grad001.philanimhlongo.service.DisposalGuidelineService;


@RestController
@RequestMapping("/api/v1")
public class DisposalGuidelineRestController {
    private DisposalGuidelineService  disposalGuidelineService;


    @Autowired
    public DisposalGuidelineRestController(DisposalGuidelineService  theDisposalGuidelineService){
        disposalGuidelineService= theDisposalGuidelineService;
    }
     // expose "/guidelines" and return a list of guidelines
    @GetMapping("/guidelines")
    public List<DisposalGuideline> findAll() {
        return disposalGuidelineService.findAll();
    }


     // add mapping for GET /guidelines/{guidelineId}

     @GetMapping("/guidelines/{guidelineId}")
     public DisposalGuideline getDisposalGuideline(@PathVariable int guidelineId ) {
 
        DisposalGuideline theDisposalGuideline =disposalGuidelineService.findById(guidelineId);
 
         if (theDisposalGuideline == null) {
             throw new NotFoundException("Disposal Guideline id not found - " + guidelineId);
         }
 
         return theDisposalGuideline;
     }
 
     // add mapping for POST /guidelines - add new guideline
 
     @PostMapping("/guidelines")
     public DisposalGuideline addDisposalGuideline(@RequestBody DisposalGuideline theDisposalGuideline) {
 
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
         theDisposalGuideline.setId(0);
 
         DisposalGuideline dbtheDisposalGuideline = disposalGuidelineService.save(theDisposalGuideline);
 
         return dbtheDisposalGuideline;
     }
 
     // add mapping for PUT /guidelines - update existing guidelines
 
     @PutMapping("/guidelines")
     public DisposalGuideline updateDisposalGuideline(@RequestBody DisposalGuideline theDisposalGuideline) {
 
        DisposalGuideline dbDisposalGuideline = disposalGuidelineService.save(theDisposalGuideline);
 
         return dbDisposalGuideline;
     }
 
     // add mapping for DELETE /guidelines/{guidelineId} - delete guideline
 
     @DeleteMapping("/guidelines/{guidelineId}")
     public String deleteDisposalGuideline(@PathVariable int guidelineId ) {
 
        DisposalGuideline tempDisposalGuideline = disposalGuidelineService.findById(guidelineId );
 
         // throw exception if null
 
         if (tempDisposalGuideline == null) {
             throw new NotFoundException("Disposal guideline id not found - " + guidelineId);
         }
 
         disposalGuidelineService.deleteById(guidelineId);
 
         return "Deleted category id - " + guidelineId;
     }
 

    // Add an exception handler using @ExceptionHandler

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exc) {

        // create a ErrorResponse

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler ... to catch any exception (catch all)

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {

        // create a ErrorResponse
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
