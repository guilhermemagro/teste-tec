package com.apress.ravi.UserRegistrationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackageClasses = UserRegistrationRestController.class)
public class UserRegistrationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationSystemApplication.class, args);
	}

}
