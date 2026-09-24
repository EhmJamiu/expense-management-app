package com.ehmjamiu.expenseTracker.mapper;

import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public Transaction toTransaction(TransactionDto dto) {
        var transaction = new Transaction();
        transaction.setTitle(dto.title());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());

        var category = new Category();
        category.setId(dto.categoryId());

        transaction.setCategory(category);
        return transaction;
    }

    public TransactionResponseDto toTransactionResponseDto(Transaction response) {
        return new TransactionResponseDto(
                response.getTitle(),
                response.getAmount(),
                response.getType(),
                response.getCategory().getId(),
                response.getCreatedAt()
        );
    }
}