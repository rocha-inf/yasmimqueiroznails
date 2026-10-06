package com.rocha_inf.yasmimqueiroznails.block.controller;

import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.service.BlockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping
    public ResponseEntity<BlockResponse> create(@Valid @RequestBody BlockRequest request){
        BlockResponse response = blockService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
