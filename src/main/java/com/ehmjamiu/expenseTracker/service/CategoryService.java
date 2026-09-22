package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.repo.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final JsonMapper jsonMapper;


    public CategoryService(CategoryRepository categoryRepository, JsonMapper jsonMapper) {
        this.categoryRepository = categoryRepository;
        this.jsonMapper = jsonMapper;
    }


    public Category save(Category category) {
        return categoryRepository.save(category);
    }




    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(Integer id) {
        var category = categoryRepository.findById(id);
        if (category == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        categoryRepository.deleteById(id);
    }

    public Category findById(Integer id) {
        var category = categoryRepository.findById(id);
        if (category == null) {
            throw new CategoryNotFound("Category with id - " +id + "does not exists.");
        }
        return categoryRepository.findById(id).orElse(null);
    }



}