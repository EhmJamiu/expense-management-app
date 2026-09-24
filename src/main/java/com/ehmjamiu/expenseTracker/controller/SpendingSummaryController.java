package com.ehmjamiu.expenseTracker.controller;

import com.ehmjamiu.expenseTracker.entity.SpendingSummary;
import com.ehmjamiu.expenseTracker.service.SpendingSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpendingSummaryController {

    private final SpendingSummaryService spendingSummaryService;

    public SpendingSummaryController(SpendingSummaryService spendingSummaryService) {
        this.spendingSummaryService = spendingSummaryService;
    }


    @GetMapping("/spending/summary")
    public SpendingSummary spendingSummary(){
        return spendingSummaryService.spendingSummary();

    }
}