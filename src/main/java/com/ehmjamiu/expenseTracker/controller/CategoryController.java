package com.ehmjamiu.expenseTracker.controller;


import com.ehmjamiu.expenseTracker.dto.CategoryDto;
import com.ehmjamiu.expenseTracker.dto.CategoryResponseDto;
import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.exceptionHandler.ErrorResponse;
import com.ehmjamiu.expenseTracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final JsonMapper jsonMapper;



    @Autowired
    public CategoryController(CategoryService categoryService, JsonMapper jsonMapper) {
        this.categoryService = categoryService;
        this.jsonMapper = jsonMapper;
      }

    @GetMapping("/categories/list")
    public String getAllTransactionList(Model model){
        List<Category> categoryList =categoryService.findAllCategory();

        model.addAttribute("categories", categoryList);

        return "category";
    }

    @GetMapping("/categories/add")
    public String addTransaction(Model model) {


        Category category = new Category();
        model.addAttribute("category", category);

        return "category-form";
    }

    @PostMapping("/categories/saveCategory")
    public String saveACategory(@ModelAttribute("category") CategoryDto category) {

        categoryService.save(category);
        return "redirect:/categories/list";
    }

    @GetMapping("/categories/editCategory")
    public String showFormForUpdate(@RequestParam("id") Integer id, Model model) {
        Category category = categoryService.findById(id);

        model.addAttribute("category", category);

        return "edit-category-form";
    }

    @PostMapping("categories/saveEditedCategory")
    public String saveEditCategory(@ModelAttribute("id") CategoryDto category){

        categoryService.save(category);

        return "redirect:/categories/list";
    }


    @GetMapping("/categories/deleteCategory")
    public String deleteTask(@RequestParam("id") Integer id){

        categoryService.deleteById(id);

        return "redirect:/categories/list";
    }



    @PostMapping("/categories")
    public CategoryResponseDto saveCategory(@RequestBody CategoryDto dto){
        return categoryService.save(dto);

    }


    @GetMapping("/categories")
    public List<CategoryResponseDto> findAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category findACategory(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }

    // patch mapping has not yet return CategoryResponseDto object.
    @PatchMapping("categories/{id}")
    public Category patchCategory(@PathVariable Integer id, @RequestBody Map<String, Object> patchPayload) {
        Category existingCategory = categoryService.findById(id);

        if(patchPayload.containsKey("id")){
            throw new RuntimeException("The request body must not contain id");
        }
        var  patchedCategory = jsonMapper.updateValue(existingCategory, patchPayload);
        //patchedCategory.setUpdatedAt(LocalDateTime.now());
        return categoryService.savePatch(patchedCategory);
    }

    @PutMapping("categories/{id}")
    public Category updateCategory(@PathVariable Integer id, @RequestBody Category category) {
       return categoryService.updateCategory(id, category);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CategoryNotFound e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        Map<String, String> errors  = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError)error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}