package com.ehmjamiu.expenseTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SpendingSummary {

    private BigDecimal budgetAmount;

    private BigDecimal totalSpent;

    private BigDecimal remainingAmount;

    @Id
    private Boolean budgetExceeded;


}