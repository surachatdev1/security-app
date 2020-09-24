package com.nls.app.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityAppApplication.class, args);
	}

	@GetMapping("/hello")
	public String helloPage() {
		return "hello";
	}
	@GetMapping("/users")
	public String userListPage() {
		return "user/user-list";
	}
	
	@GetMapping("/users/form")
	public String userFormPage() {
		return "user/user-form";
	}
}
