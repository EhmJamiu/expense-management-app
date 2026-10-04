package com.ehmjamiu.expenseTracker.controller;

import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.exceptionHandler.ErrorResponse;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.ehmjamiu.expenseTracker.service.CategoryService;
import com.ehmjamiu.expenseTracker.service.TransactionService;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final JsonMapper jsonMapper;
    private final CategoryService categoryService;

    public TransactionController(TransactionService transactionService, JsonMapper jsonMapper, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.jsonMapper = jsonMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/transactions/list")
    public String getAllTransactionList(Model model){
        List<Transaction> transactionList = transactionService.findAllTransactionList();

        model.addAttribute("transactions", transactionList);

        return "transaction";
    }

    @GetMapping("/transactions/add")
    public String addTransaction(Model model) {

        //List<Category> categoryNameList = categoryService.getCategoryName();
        List<String> transactionTypeList= transactionService.getAllTransactionType();


        model.addAttribute("transaction", new TransactionDto(null, null, null, null, null));
        model.addAttribute("categories", categoryService.findAllCategory());
        //model.addAttribute("categoryNameList", categoryNameList);
        model.addAttribute("transactionTypeList", transactionTypeList);
        return "transaction-form";
    }

    @PostMapping("/transactions/saveTransaction")
    public String saveATransaction(@ModelAttribute("transaction") TransactionDto transaction) {

        transactionService.save(transaction);
        return "redirect:/transactions/list";
    }

    @GetMapping("/transactions/editTransaction")
    public String transactionFormEdit(@RequestParam("id") Integer id, Model model) {
        Transaction transaction = transactionService.findById(id);
        TransactionDto transactionDto = new TransactionDto(
                transaction.getId(),
                transaction.getTitle(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory().getId()
        );

        model.addAttribute("transactionDto", transactionDto);
        model.addAttribute("categories", categoryService.findAllCategory());
        model.addAttribute("transactionTypeList", transactionService.getAllTransactionType());

        return "edit-transaction-form";
    }

    @PostMapping("transactions/saveEditedTransaction")
    public String saveEditedTransaction(@ModelAttribute("transactionDto") TransactionDto transactionDto){

        transactionService.saveEditedTransaction(transactionDto);

        return "redirect:/transactions/list";
    }

    @GetMapping("/transactions/deleteTransaction")
    public String deleteTask(@RequestParam("id") Integer id){
       transactionService.deleteById(id);

        return "redirect:/transactions/list";
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(CategoryNotFound e) {
//        ErrorResponse error = new ErrorResponse();
//
//        error.setStatus(HttpStatus.NOT_FOUND.value());
//        error.setMessage(e.getMessage());
//        error.setTimeStamp(System.currentTimeMillis());
//
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//
//    }
}