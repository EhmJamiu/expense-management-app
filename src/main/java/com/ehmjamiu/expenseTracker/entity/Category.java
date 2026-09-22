package com.ehmjamiu.expenseTracker.entity;

import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String name;

    public TransactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    public Users user;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonManagedReference
    public Budget budgets;

    @Column(updatable = false)
    public LocalDateTime createdAt;

    @Column(insertable = false)
    public LocalDateTime updatedAt;

    public Category() {
        createdAt = LocalDateTime.now();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Budget getBudgets() {
        return budgets;
    }

    public void setBudgets(Budget budgets) {
        this.budgets = budgets;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

