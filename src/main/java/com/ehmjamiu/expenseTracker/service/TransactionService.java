package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.mapper.TransactionMapper;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.ehmjamiu.expenseTracker.repo.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    public final TransactionRepository transactionRepository;
    public final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDto save(TransactionDto dto) {
        var transaction = transactionMapper.toTransaction(dto);
        var savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponseDto(savedTransaction);
    }

    public Transaction savePatch(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDto> findAll() {
        return transactionRepository.findAll().stream()
                .map(transaction ->
                        transactionMapper.toTransactionResponseDto(transaction))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        var transaction = transactionRepository.findById(id);
        if (transaction == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        transactionRepository.deleteById(id);
    }

    public Transaction findById(Integer id) {
        var budget = transactionRepository.findById(id);
        if (budget == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        return transactionRepository.findById(id).orElse(null);
    }


    public Transaction updateTransaction(Integer id, Transaction transaction) {
        Transaction existingTransaction = findById(id);
        existingTransaction.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(existingTransaction);
    }

    public List<Transaction> findTransactionByTitle(String keyword) {
        return transactionRepository.findTransactionByTitle(keyword);
    }

    public List<Transaction> findTransactionByCategoryId(Integer id) {
        return transactionRepository.findTransactionByCategoryId(id);
    }

    public List<Transaction> findTransactionByType(TransactionType type) {
        return transactionRepository.findTransactionByType(type);
    }

    public List<Transaction> findTransactionByDate(Date from, Date to) {
        return transactionRepository.findTransactionByDate(from, to);
    }
}