package com.ehmjamiu.expenseTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String name;

    @Positive
    public BigDecimal amount;

    @Min(value = 1)
    @Max(value = 12)
    public Month month;

    public Year year;

    @OneToMany(mappedBy = "budgets")
    @JsonBackReference
    public List<Category> category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    public Users user;


    @Column(updatable = false)
    public LocalDateTime createdAt;

    @Column(insertable = false)
    public LocalDateTime updatedAt;

    public Budget() {
        createdAt = LocalDateTime.now();
    }
}