package com.learning.model.courses.dao;

import com.learning.model.users.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    public String fullName;
    public String email;
    public ERole role;
}
