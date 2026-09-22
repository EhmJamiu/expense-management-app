package com.ehmjamiu.expenseTracker.entity;

import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    public String title;

    public String description;

    @Positive
    @NotNull
    public BigDecimal amount;

    @NotNull
    public TransactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    public Users users;

    @NotNull
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @Column(insertable = false)
    public LocalDateTime updatedAt;


    public Transaction() {
        createdAt = LocalDateTime.now();
    }
}