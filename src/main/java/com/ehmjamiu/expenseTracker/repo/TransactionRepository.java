package com.ehmjamiu.expenseTracker.repo;

import com.ehmjamiu.expenseTracker.dto.TransactionResponseDto;
import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("""
        SELECT t FROM Transaction t ORDER BY t.createdAt ASC
        """)
    List<TransactionResponseDto> findAll(Pageable pageable);

    @Query("""
        SELECT t FROM Transaction t WHERE t.title ILIKE :title
        """)
    List<Transaction> findTransactionByTitle(@Param("title") String title);

    @Query("""
        SELECT t FROM Transaction t WHERE t.category.id = :category_id
        """)
    List<Transaction> findTransactionByCategoryId(@Param("category_id") Integer id);

    @Query("""
        SELECT t FROM Transaction t WHERE t.type = :type
        """)
    List<Transaction> findTransactionByType(@Param("type") TransactionType type);

    @Query("""
        SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :from AND :to
        """)
    List<Transaction> findTransactionByDate(Date from, Date to);


    @Query("""
        SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type
        """)
    BigDecimal totalExpenseInTransaction(@Param("type") TransactionType expense);

    @Query("""
        SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type
        """)
    BigDecimal totalIncomeInTransaction(@Param("type") TransactionType income);

    @Query("""
        SELECT t.category.name, SUM(t.amount) FROM Transaction t GROUP BY t.category.name
        """)
    List<Object[]> getTotalAmountByCategory();

//    @Query("""
//        SELECT t.type FROM Transaction t
//        """)
//    List<String> getAllTransactionType();
}
