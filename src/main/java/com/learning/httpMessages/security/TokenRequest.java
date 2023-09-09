package com.learning.httpMessages.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    public String email;
    public String password;
}