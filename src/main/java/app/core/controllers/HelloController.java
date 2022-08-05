package app.core.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController
public class HelloController {

	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;

	@Value("${spring.datasource.url}")
	private String dburl;
	@Value("${env.name}")
	private String env;

	@GetMapping
	public String getProperties() {

		return "Hello from " + env + " : " + dburl;
	}

}
