package com.ehmjamiu.expenseTracker.service;

import com.ehmjamiu.expenseTracker.dto.CategoryDto;
import com.ehmjamiu.expenseTracker.dto.CategoryResponseDto;
import com.ehmjamiu.expenseTracker.entity.Budget;
import com.ehmjamiu.expenseTracker.entity.Category;
import com.ehmjamiu.expenseTracker.exceptionHandler.CategoryNotFound;
import com.ehmjamiu.expenseTracker.mapper.CategoryMapper;
import com.ehmjamiu.expenseTracker.repo.CategoryRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;

    }


    public Category savePatch(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponseDto save(CategoryDto dto) {
        var category = categoryMapper.toCategory(dto);
        var savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }




    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> categoryMapper.toCategoryResponseDto(category))
                .collect(Collectors.toList());
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


    public Category updateCategory(Integer id, Category category) {
        Category existingCategory = findById(id);
//            existingCategory.setUpdatedAt(LocalDateTime.now());

        return categoryRepository.save(existingCategory);
    }

   }