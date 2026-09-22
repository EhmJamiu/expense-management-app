package com.ehmjamiu.expenseTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @Email
    public String email;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    public List<Budget> budgets;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    public List<Category> categories;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    public List<Transaction> transactions;

    @ManyToMany(mappedBy = "users")
    public List<Role> roles;

}
