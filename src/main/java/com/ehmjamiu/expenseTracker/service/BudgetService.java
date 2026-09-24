package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.repo.BudgetRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository){
        this.budgetRepository = budgetRepository;
    }

    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    public void deleteById(Integer id) {
        var budget = budgetRepository.findById(id);
        if (budget == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        budgetRepository.deleteById(id);
    }

    public Budget findById(Integer id) {
        var budget = budgetRepository.findById(id);
        if (budget == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        return budgetRepository.findById(id).orElse(null);
    }


    public Budget updateBudget(Integer id, Budget budget) {
        Budget existingBudget = findById(id);
        existingBudget.setUpdatedAt(LocalDateTime.now());

        return budgetRepository.save(existingBudget);
    }
}