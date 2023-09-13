package com.learning.httpMessages.security;

import com.learning.model.courses.dao.UserDAO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {

    public String token;
    public UserDAO user;

}