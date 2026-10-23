package com.rocha_inf.yasmimqueiroznails.catalog.service;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.CategoryRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.CategoryUpdateRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.CategoryResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.CategoryNameAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.CategoryNotFoundException;
import com.rocha_inf.yasmimqueiroznails.catalog.mapper.CategoryMapper;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.CategoryRepository;
import com.rocha_inf.yasmimqueiroznails.shared.normalization.StringNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public Page<CategoryResponse> findAll(Pageable pageable){
        return repository.findAllByDeletedAtIsNull(pageable).map(mapper::toResponse);
    }

    private Category findByIdAndDeletedAtIsNull(UUID id){
        return repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

    private void validateCreate(CategoryRequest request) {
        if (repository.existsByNameAndDeletedAtIsNull(request.name())){
            throw new CategoryNameAlreadyExistsException("O nome passado para a categoria já existe");
        }
    }

    public void validateUpdate(Category entity, CategoryUpdateRequest request){
        if (repository.existsByNameAndDeletedAtIsNullAndIdNot(request.name(), entity.getId())){
            throw new CategoryNameAlreadyExistsException("O nome passado para a categoria já existe");
        }
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        this.validateCreate(request);
        Category category = mapper.toEntity(request);
        return mapper.toResponse(repository.save(category));
    }

    @Transactional
    public CategoryResponse update(UUID id, CategoryUpdateRequest request){

        Category category = this.findByIdAndDeletedAtIsNull(id);
        this.validateUpdate(category, request);

        if (request.name() != null) { category.setName(request.name()); }
        if (request.description() != null) {
            String description = StringNormalizer.normalize(request.description());
            category.setDescription(description);
        }

        return mapper.toResponse(category);

    }

    @Transactional
    public void delete(UUID id){
        Category category = this.findByIdAndDeletedAtIsNull(id);
        category.delete();
    }
}
