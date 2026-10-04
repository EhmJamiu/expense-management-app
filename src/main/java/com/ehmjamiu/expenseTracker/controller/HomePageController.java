package com.ehmjamiu.expenseTracker.controller;


import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.ehmjamiu.expenseTracker.service.BudgetService;
import com.ehmjamiu.expenseTracker.service.CategoryService;
import com.ehmjamiu.expenseTracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class HomePageController {

    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public HomePageController (BudgetService budgetService, CategoryService categoryService, TransactionService transactionService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
    }
    


    @GetMapping("/home-page")
    public String loadHomePage(Model model) {
        BigDecimal totalExpense = transactionService.totalExpenseInTransaction();
        BigDecimal totalIncome = transactionService.totalIncomeInTransaction();
        BigDecimal totalBalance = transactionService.totalBalance();
        LocalDate currentDate = LocalDate.now();

        List<TransactionResponseDto> transactions = transactionService.findAll(0, 5);

        List<Object[]> amountByCategories = transactionService.getTotalAmountByCategory();

        model.addAttribute("amountByCategories", amountByCategories);
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("currentDate", currentDate);


        return "home-page";
    }
}