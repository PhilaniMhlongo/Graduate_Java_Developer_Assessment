package com.enviro.assessment.grad001.philanimhlongo.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.RecyclingTip;
import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.exception.ErrorResponse;
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

    // expose "/tips" and return a list of tips
    @GetMapping("/tips")
    public List<RecyclingTip> findAll() {
        return recyclingTipService.findAll();
    }


     // add mapping for GET /tips/{tipId}

     @GetMapping("/tips/{tipId}")
     public RecyclingTip getRecyclingTip(@PathVariable int tipId ) {
 
        RecyclingTip theRecyclingTip =recyclingTipService.findById(tipId);
 
         if (theRecyclingTip == null) {
             throw new NotFoundException("Recycling tip id not found - " + tipId);
         }
 
         return theRecyclingTip;
     }
 
     // add mapping for POST /tips - add new tip
 
     @PostMapping("/tips")
     public RecyclingTip addRecyclingTip(@RequestBody RecyclingTip theRecyclingTip) {
 
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
         theRecyclingTip.setId(0);
 
         RecyclingTip dbtheRecyclingTip = recyclingTipService.save(theRecyclingTip);
 
         return dbtheRecyclingTip;
     }
 
     // add mapping for PUT /tip - update existing tip
 
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
 

    // // Add an exception handler using @ExceptionHandler

    // @ExceptionHandler
    // public ResponseEntity<ErrorResponse> handleException(NotFoundException exc) {

    //     // create a ErrorResponse

    //     ErrorResponse error = new ErrorResponse();

    //     error.setStatus(HttpStatus.NOT_FOUND.value());
    //     error.setMessage(exc.getMessage());
    //     error.setTimeStamp(System.currentTimeMillis());

    //     // return ResponseEntity

    //     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    // }

    // // add another exception handler ... to catch any exception (catch all)

    // @ExceptionHandler
    // public ResponseEntity<ErrorResponse> handleException(Exception exc) {

    //     // create a ErrorResponse
    //     ErrorResponse error = new ErrorResponse();

    //     error.setStatus(HttpStatus.BAD_REQUEST.value());
    //     error.setMessage(exc.getMessage());
    //     error.setTimeStamp(System.currentTimeMillis());

    //     // return ResponseEntity
    //     return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    // }
}
