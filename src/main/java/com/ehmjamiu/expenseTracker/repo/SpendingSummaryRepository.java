package com.ehmjamiu.expenseTracker.repo;

import com.ehmjamiu.expenseTracker.entity.SpendingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SpendingSummaryRepository extends JpaRepository<SpendingSummary, Integer> {

    @Query("""
        SELECT SUM(b.amount) FROM Budget b
    """)
    BigDecimal getBudgetAmount();

    @Query("""
        SELECT SUM(t.amount) FROM Transaction t WHERE t.type = EXPENSE
    """)
    BigDecimal getTotalSpent();

//    @Query("""
//        SELECT (SUM(b.amount) -
//            (SELECT SUM(t.amount) FROM Transaction t
//                WHERE t.type = com.ehmjamiu.expenseTracker.TransactionType.EXPENSE)) FROM Budget b
//    """)
//    BigDecimal getRemainingAmount();


//    @Query("""
//        SELECT
//    """)
//    Boolean getBudgetExceeded();
}
