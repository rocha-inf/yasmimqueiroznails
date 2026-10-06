package com.rocha_inf.yasmimqueiroznails.security.auth.message;

public record VerificationEmailMessage(String email, String verificationURL) {}
