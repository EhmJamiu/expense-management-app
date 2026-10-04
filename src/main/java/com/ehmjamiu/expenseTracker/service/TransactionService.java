package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.dto.TransactionDto;
import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.mapper.TransactionMapper;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.ehmjamiu.expenseTracker.repo.CategoryRepository;
import com.ehmjamiu.expenseTracker.repo.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    public final TransactionRepository transactionRepository;
    public final TransactionMapper transactionMapper;
    public final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllTransactionType(){
        List<String> types = new ArrayList<>();
        for(TransactionType type : TransactionType.values()){
            types.add(type.name());
        }
        return types;

    }

    public List<Transaction> findAllTransactionList() {
        return transactionRepository.findAll();
    }

    public BigDecimal totalExpenseInTransaction(){
        TransactionType expense = TransactionType.EXPENSE;
        BigDecimal result;
        if(transactionRepository.totalExpenseInTransaction(expense) == null) {
            result = BigDecimal.valueOf(0);
        } else
            result = transactionRepository.totalExpenseInTransaction(expense);
        return result;
    }

    public BigDecimal totalIncomeInTransaction(){

        TransactionType income = TransactionType.INCOME;

        BigDecimal result;
        if(transactionRepository.totalIncomeInTransaction(income) == null) {
            result = BigDecimal.valueOf(0);
        } else
            result = transactionRepository.totalIncomeInTransaction(income);
        return result;
    }

    public BigDecimal totalBalance() {

        return totalExpenseInTransaction().subtract(totalIncomeInTransaction());
    }


    public List<Object[]> getTotalAmountByCategory(){
        return transactionRepository.getTotalAmountByCategory();
    }

    public TransactionResponseDto save(TransactionDto dto) {
        var transaction = transactionMapper.toTransaction(dto);
        var savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponseDto(savedTransaction);
    }

    public Transaction saveATransaction(TransactionDto dto){
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
        Transaction transaction = new Transaction();
        transaction.setTitle(dto.title());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setCategory(category);
        return transactionRepository.save(transaction);
    }

    public Transaction savePatch(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDto> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
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

    public void saveEditedTransaction(TransactionDto dto) {
        Transaction transaction = transactionRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setTitle(dto.title());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setCategory(category);

        transactionRepository.save(transaction);
    }
}