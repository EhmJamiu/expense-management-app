package com.ehmjamiu.expenseTracker.controller;

import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.exceptionHandler.ErrorResponse;
import com.ehmjamiu.expenseTracker.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BudgetController {
    private final BudgetService budgetService;
    private final JsonMapper jsonMapper;

    public BudgetController(BudgetService budgetService, JsonMapper jsonMapper) {
        this.budgetService = budgetService;
        this.jsonMapper = jsonMapper;
    }

    @PostMapping("/budgets")
    public Budget saveBudget(@RequestBody Budget budget){
        return budgetService.save(budget);

    }


    @GetMapping("/budgets")
    public List<Budget> findAllBudget() {
        return budgetService.findAll();
    }

    @GetMapping("/budgets/{id}")
    public Budget findABudget(@PathVariable Integer id) {
        return budgetService.findById(id);
    }

    @DeleteMapping("/budgets/{id}")
    public void deleteBudget(@PathVariable Integer id) {
        budgetService.deleteById(id);
    }

    // patch mapping has not yet return CategoryResponseDto object.
    @PatchMapping("budgets/{id}")
    public Budget patchBudget(@PathVariable Integer id, @RequestBody Map<String, Object> patchPayload) {
        Budget existingBudget = budgetService.findById(id);

        if(patchPayload.containsKey("id")){
            throw new RuntimeException("The request body must not contain id");
        }
        var  patchedBudget = jsonMapper.updateValue(existingBudget, patchPayload);
        patchedBudget.setUpdatedAt(LocalDateTime.now());
        return budgetService.save(patchedBudget);
    }

    @PutMapping("budgets/{id}")
    public Budget updateBudget(@PathVariable Integer id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CategoryNotFound e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

}

