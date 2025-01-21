package com.enviro.assessment.grad001.philanimhlongo.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.philanimhlongo.entity.WasteCategory;
import com.enviro.assessment.grad001.philanimhlongo.exception.ErrorResponse;
import com.enviro.assessment.grad001.philanimhlongo.exception.NotFoundException;
import com.enviro.assessment.grad001.philanimhlongo.service.WasteCategoryService;

@RestController
@RequestMapping("/api/v1")
public class WasteCategoriesRestController {
    private WasteCategoryService wasteCategoryService;


    @Autowired
    public WasteCategoriesRestController(WasteCategoryService theWasteCategoryService){
        wasteCategoryService=theWasteCategoryService;
    }

    // expose "/categories" and return a list of categories
    @GetMapping("/categories")
    public List<WasteCategory> findAll() {
        return wasteCategoryService.findAll();
    }


     // add mapping for GET /categories/{categoryId}

     @GetMapping("/categories/{categoryId}")
     public WasteCategory getWasteCategory(@PathVariable int categoryId ) {
 
        WasteCategory theWasteCategory = wasteCategoryService.findById(categoryId);
 
         if (theWasteCategory == null) {
             throw new NotFoundException("WasteCategory id not found - " + categoryId);
         }
 
         return theWasteCategory;
     }
 
     // add mapping for POST /categories - add new category
 
     @PostMapping("/categories")
     public WasteCategory addCategory(@RequestBody WasteCategory theWasteCategory) {
 
         // also just in case they pass an id in JSON ... set id to 0
         // this is to force a save of new item ... instead of update
 
        theWasteCategory.setId(wasteCategoryService.latestId());
 
         // Get the category with the highest current id to determine the next available id

        // Save the category
        WasteCategory dbWasteCategory = wasteCategoryService.save(theWasteCategory);

        return dbWasteCategory;
     }
 
     // add mapping for PUT /categories - update existing category
 
     @PutMapping("/categories")
     public WasteCategory updateCategory(@RequestBody WasteCategory theWasteCategory) {
 
         WasteCategory dbWasteCategory = wasteCategoryService.save(theWasteCategory);
 
         return dbWasteCategory;
     }
 
     // add mapping for DELETE /categories/{categoryId} - delete category
 
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
