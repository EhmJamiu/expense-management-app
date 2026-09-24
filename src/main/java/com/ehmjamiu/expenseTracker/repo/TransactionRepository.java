package com.ehmjamiu.expenseTracker.repo;

import com.ehmjamiu.expenseTracker.entity.Transaction;
import com.ehmjamiu.expenseTracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

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
}
