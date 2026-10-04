package com.ehmjamiu.expenseTracker.dto;

import com.ehmjamiu.expenseTracker.model.TransactionType;

import java.math.BigDecimal;

public record TransactionDto(
        Integer id,
        String title,
        BigDecimal amount,
        TransactionType type,
        Integer categoryId
) {
}
