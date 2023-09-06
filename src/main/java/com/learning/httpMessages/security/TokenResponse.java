package com.learning.httpMessages.security;

import lombok.Data;

@Data
public class TokenResponse {

    private String message;

    public TokenResponse(String message) {
        this.message = message;
    }
}