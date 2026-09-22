package com.ehmjamiu.expenseTracker.repo;

import com.ehmjamiu.expenseTracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
