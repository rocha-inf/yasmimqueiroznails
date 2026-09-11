package com.rocha_inf.yasmimqueiroznails.catalog.service;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.CategoryRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.CategoryResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.CategoryNameAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.catalog.mapper.CategoryMapper;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse create(CategoryRequest request) {

        if (categoryRepository.existsByNameAndDeletedAtIsNull(request.name())){
            throw new CategoryNameAlreadyExistsException("O nome passado para a categoria já existe");
        }

        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }


}
