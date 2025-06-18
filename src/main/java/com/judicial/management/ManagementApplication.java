package com.judicial.management;

import com.judicial.management.model.User;
import com.judicial.management.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Judicial Management API", version = "v1"),
		security = @SecurityRequirement(name = "bearerAuth"))
@SecuritySchemes({
		@SecurityScheme(
				name = "bearerAuth",
				type = SecuritySchemeType.HTTP,
				scheme = "bearer",
				bearerFormat = "JWT"
		)
})
public class ManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			if (userRepository.findByUsername("admin").isEmpty()) {
				User adminUser = new User();
				adminUser.setUsername("admin");
				adminUser.setPassword(passwordEncoder.encode("password"));
				userRepository.save(adminUser);
				System.out.println(">>> User 'admin' created with password 'password'");
			}
		};
	}
}
