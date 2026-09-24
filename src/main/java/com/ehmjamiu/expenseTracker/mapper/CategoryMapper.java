package com.ehmjamiu.expenseTracker.mapper;

import com.ehmjamiu.expenseTracker.dto.CategoryDto;
import com.ehmjamiu.expenseTracker.dto.CategoryResponseDto;
import com.ehmjamiu.expenseTracker.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {


    public Category toCategory(CategoryDto dto) {
        var category = new Category();

        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;


    }

    public CategoryResponseDto toCategoryResponseDto(Category response) {
        return new CategoryResponseDto(
                response.getName(),
                response.getDescription()
        );
    }


}