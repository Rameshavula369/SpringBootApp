package com.effort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.effort")
public class LoginSyncApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LoginSyncApplication.class, args);
	}

}
