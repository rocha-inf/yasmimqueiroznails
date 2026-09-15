package com.rocha_inf.yasmimqueiroznails.scheduling.controller;

import com.rocha_inf.yasmimqueiroznails.scheduling.dto.request.BlockOnceRequest;
import com.rocha_inf.yasmimqueiroznails.scheduling.dto.response.BlockOnceResponse;
import com.rocha_inf.yasmimqueiroznails.scheduling.service.BlockOnceService;
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

    private final BlockOnceService blockOnceService;

    public BlockController(BlockOnceService blockOnceService) {
        this.blockOnceService = blockOnceService;
    }

    @PostMapping("/once")
    public ResponseEntity<?> createBlockOnce(@Valid @RequestBody BlockOnceRequest request){
        BlockOnceResponse response = blockOnceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
