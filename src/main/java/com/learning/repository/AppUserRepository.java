package com.learning.repository;

import com.learning.model.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.role.name = 'ROLE_ADMIN'")
    List<AppUser> findAllAdmins();

    @Query("SELECT u FROM AppUser u WHERE u.role.name = 'ROLE_REGULAR'")
    List<AppUser> findRegularUsers();
}
