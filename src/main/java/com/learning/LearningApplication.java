package com.learning;

import com.learning.model.users.ERole;
import com.learning.model.users.Role;
import com.learning.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(RoleRepository roleRepository) {
//		return args -> {
//
//			Role role_admin = new Role();
//			role_admin.setName(ERole.ROLE_ADMIN);
//			roleRepository.save(role_admin);
//
//			Role role_regular = new Role();
//			role_regular.setName(ERole.ROLE_REGULAR);
//			roleRepository.save(role_regular);
//		};
//	}
}
