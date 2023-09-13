package com.learning.model.courses.dao;

import com.learning.model.users.ERole;
import lombok.Data;

@Data
public class UserDAO {

    public String fullName;
    public String email;
    public ERole role;
}
