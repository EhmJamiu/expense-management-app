package com.ehmjamiu.expenseTracker.entity;

import com.ehmjamiu.expenseTracker.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private String description;

    public Category() {

    }

//    @Column(updatable = false)
//    @NotNull
//    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
//    private LocalDateTime createdAt;
//
//    @Column(insertable = false)
//    @JsonFormat(shape =JsonFormat.Shape.STRING, timezone = "UTC+1", pattern = "dd-mm-yyyy hh:mm a")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    private void initializedCreateAt(){
//        if(createdAt == null)
//            createdAt = LocalDateTime.now();
//    }

//    public Category() {
//        this.createdAt = LocalDateTime.now();
//
//    }



  }

