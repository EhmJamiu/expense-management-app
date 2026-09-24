package com.ehmjamiu.expenseTracker.entity;

import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Entity
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(updatable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
    private LocalDateTime updatedAt;


    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    private void initializedCreateAt(){
        if(createdAt == null)
            createdAt = LocalDateTime.now();
    }
}