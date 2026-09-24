package com.ehmjamiu.expenseTracker.dto;

import com.ehmjamiu.expenseTracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDto(
        String title,
        BigDecimal amount,
        TransactionType type,
        Integer categoryId,
        LocalDateTime createdAt
) {
}
