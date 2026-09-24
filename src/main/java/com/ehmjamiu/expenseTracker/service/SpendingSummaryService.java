package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.entity.SpendingSummary;
import com.ehmjamiu.expenseTracker.repo.SpendingSummaryRepository;
import org.springframework.stereotype.Service;

@Service
public class SpendingSummaryService {

    private final SpendingSummaryRepository spendingSummaryRepository;

    public SpendingSummaryService(SpendingSummaryRepository spendingSummaryRepository) {
        this.spendingSummaryRepository = spendingSummaryRepository;
    }

    public SpendingSummary spendingSummary() {
        SpendingSummary summary = new SpendingSummary();
        summary.setBudgetAmount(spendingSummaryRepository.getBudgetAmount());
        summary.setTotalSpent(spendingSummaryRepository.getTotalSpent());
        summary.setRemainingAmount(spendingSummaryRepository.getBudgetAmount().subtract(spendingSummaryRepository.getTotalSpent()));
        summary.setBudgetExceeded(spendingSummaryRepository.getTotalSpent().compareTo(spendingSummaryRepository.getBudgetAmount()) > 0);

        return summary;
    }
}