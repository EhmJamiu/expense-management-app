package com.ehmjamiu.expenseTracker.controller;

import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.exceptionHandler.ErrorResponse;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.ehmjamiu.expenseTracker.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final JsonMapper jsonMapper;


    public TransactionController(TransactionService transactionService, JsonMapper jsonMapper) {
        this.transactionService = transactionService;
        this.jsonMapper = jsonMapper;
    }

    @PostMapping("/transactions")
    public TransactionResponseDto saveTransaction(@RequestBody TransactionDto dto){
        return transactionService.save(dto);
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDto> findAllTransaction() {
        return transactionService.findAll();
    }

    @GetMapping("/transactions/{id}")
    public Transaction findATransaction(@PathVariable Integer id) {
        return transactionService.findById(id);
    }

    @DeleteMapping("/transactions/{id}")
    public void deleteBudget(@PathVariable Integer id) {
        transactionService.deleteById(id);
    }

    // patch mapping has not yet return CategoryResponseDto object.
    @PatchMapping("transactions/{id}")
    public Transaction patchTransaction(@PathVariable Integer id, @RequestBody Map<String, Object> patchPayload) {
       Transaction existingTransaction = transactionService.findById(id);

        if(patchPayload.containsKey("id")){
            throw new RuntimeException("The request body must not contain id");
        }
        var  patchedTransaction = jsonMapper.updateValue(existingTransaction, patchPayload);
        patchedTransaction.setUpdatedAt(LocalDateTime.now());
        return transactionService.savePatch(patchedTransaction);
    }

    @PutMapping("transactions/{id}")
    public Transaction updateTransaction(@PathVariable Integer id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @GetMapping("/transactions/title")
    public List<Transaction> findTransactionByTitle(@RequestParam String keyword) {
        return transactionService.findTransactionByTitle(keyword);
    }

    @GetMapping("/transactions/category")
    public List<Transaction> findTransactionByCategoryId(@RequestParam Integer id){
        return transactionService.findTransactionByCategoryId(id);
    }

    @GetMapping("transactions/type")
    public List<Transaction> findTransactionByType(@RequestParam TransactionType type){
        return transactionService.findTransactionByType(type);
    }

    @GetMapping("transactions/between")
    public List<Transaction> findTransactionByDate(@RequestParam Date from, Date to){
        return transactionService.findTransactionByDate(from, to);
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