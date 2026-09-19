package com.rocha_inf.yasmimqueiroznails.catalog.controller;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.OfferingResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.service.OfferingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offerings")
public class OfferingController {

    private final OfferingService offeringService;

    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @PostMapping
    public ResponseEntity<OfferingResponse> create(@Valid @RequestBody OfferingRequest request){

        OfferingResponse response = offeringService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
