package com.rocha_inf.yasmimqueiroznails.catalog.controller;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.CategoryRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.CategoryResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(Pageable pageable) {
        Page<CategoryResponse> response = service.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
