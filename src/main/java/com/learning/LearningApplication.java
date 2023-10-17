package com.learning;

import com.learning.model.users.AppUser;
import com.learning.model.users.ERole;
import com.learning.model.users.Role;
import com.learning.repository.AppUserRepository;
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
//	public CommandLineRunner demo(RoleRepository roleRepository, AppUserRepository userRepository) {
//		return args -> {
//
//			Role role_admin = new Role();
//			role_admin.setName(ERole.ROLE_ADMIN);
//			roleRepository.save(role_admin);
//
//			Role role_regular = new Role();
//			role_regular.setName(ERole.ROLE_REGULAR);
//			roleRepository.save(role_regular);
//
//			AppUser admin = new AppUser();
//			admin.setFirstName("Admin");
//			admin.setLastName("Admin");
//			admin.setEmail("admin@gmail.com");
//			admin.setPassword("$2a$12$6IZD/nURZV18XH5dL7iBt.rDk3NtiDYwTtpP3YDdYYepfRGsl9LMG");
//			admin.setRole(role_admin);
//			userRepository.save(admin);
//		};
//	}
}
