package com.chaitanya.pms;

import com.chaitanya.pms.security.jwt.JwtProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Welcome to Auth-Service");
		System.out.println(new BCryptPasswordEncoder().encode("Admin@123"));
		// $2a$10$WkaUreBRN9xOYyhfuNS/betLDDYufARR3H7c6Ycw4FsXEh406cfsm
		// $2a$10$eYtLU4CDbrHKHH/tfKXEqefS7lcM.t5aZ1gu4VVbs0889h2dbiU5e
	}
}
