package com.assignment.clinic;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Kavitha Starting point for clinic application - main method Get -
 *         http://localhost:9002/v2/api-docs - swagger data Get -
 *         http://localhost:9002/swagger-ui.html - swagger UI rendered from
 *         swagger data
 *
 */
@SpringBootApplication
@EnableSwagger2
public class ClinicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicServiceApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.assignment")).build().apiInfo(customInfo());
	}

	private ApiInfo customInfo() {
		ApiInfo customInfo = new ApiInfo("Clinic Program API", "Assignment Project", "1.0", "Terms of service",
				new Contact("Test", "www.test.com", "test@outlook.com"), "Free license",
				"API license URL", Collections.emptyList());

		return customInfo;

	}
	
}
