package com.rocha_inf.yasmimqueiroznails.controller;

import com.rocha_inf.yasmimqueiroznails.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){

        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
