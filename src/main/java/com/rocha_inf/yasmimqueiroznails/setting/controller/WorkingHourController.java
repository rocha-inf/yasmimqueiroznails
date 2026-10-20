package com.rocha_inf.yasmimqueiroznails.setting.controller;

import com.rocha_inf.yasmimqueiroznails.setting.dto.request.WorkingHourRequest;
import com.rocha_inf.yasmimqueiroznails.setting.dto.response.WorkingHourResponse;
import com.rocha_inf.yasmimqueiroznails.setting.service.WorkingHourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/working-hours")
@RequiredArgsConstructor
public class WorkingHourController {

    private final WorkingHourService service;

    @PostMapping
    public ResponseEntity<WorkingHourResponse> create(@Valid @RequestBody WorkingHourRequest request){
        WorkingHourResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
