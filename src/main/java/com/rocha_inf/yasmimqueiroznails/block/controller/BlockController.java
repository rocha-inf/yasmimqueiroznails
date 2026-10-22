package com.rocha_inf.yasmimqueiroznails.block.controller;

import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockUpdateRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.service.BlockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService service;

    @PostMapping
    public ResponseEntity<BlockResponse> create(@Valid @RequestBody BlockRequest request){
        BlockResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BlockResponse>> findAll(@PageableDefault(size = 15, sort = "startsAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<BlockResponse> response = service.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlockResponse> update(@PathVariable UUID id, @RequestBody BlockUpdateRequest request){
        BlockResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}