package com.ehmjamiu.expenseTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;


@Data
@Entity
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive
    @NotNull
    private BigDecimal amount;

    @Enumerated(EnumType.ORDINAL)
//    @Max(value = 12)
//    @Min(value = 1)
    private Month month;

    @Min(value = 2002)
    @Max(value = 2030)
    @JsonFormat(pattern = "yyyy")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(updatable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
    private LocalDateTime updatedAt;

    public Budget() {
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    private void initializedCreateAt(){
        if(createdAt == null)
            createdAt = LocalDateTime.now();
    }


}