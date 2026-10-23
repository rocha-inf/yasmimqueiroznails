package com.rocha_inf.yasmimqueiroznails.catalog.service;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.CategoryRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.CategoryResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.CategoryNameAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.catalog.mapper.CategoryMapper;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public CategoryResponse create(CategoryRequest request) {

        if (repository.existsByNameAndDeletedAtIsNull(request.name())){
            throw new CategoryNameAlreadyExistsException("O nome passado para a categoria já existe");
        }

        Category category = mapper.toEntity(request);
        return mapper.toResponse(repository.save(category));
    }

    public Page<CategoryResponse> findAll(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toResponse);
    }

}
