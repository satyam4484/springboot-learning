package com.ecom.ecommerce.service;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.dto.CategoryDto;
import com.ecom.ecommerce.entity.Category;
import com.ecom.ecommerce.exception.ApplicationException;
import com.ecom.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {

        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ApplicationException(
                    "Category already exists with name: " + categoryDto.getName(),
                    ErrorCode.CATEGORY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        Category category = modelMapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationException(
                                "Category not found with id: " + id,
                                ErrorCode.CATEGORY_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        )
                );

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    public CategoryDto updateCategory(
            Long id,
            CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationException(
                                "Category not found with id: " + id,
                                ErrorCode.CATEGORY_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!category.getName().equals(categoryDto.getName())
                && categoryRepository.existsByName(categoryDto.getName())) {

            throw new ApplicationException(
                    "Category already exists with name: "
                            + categoryDto.getName(),
                    ErrorCode.CATEGORY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        modelMapper.map(categoryDto, category);

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Transactional
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationException(
                                "Category not found with id: " + id,
                                ErrorCode.CATEGORY_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        )
                );

        categoryRepository.delete(category);
    }
}