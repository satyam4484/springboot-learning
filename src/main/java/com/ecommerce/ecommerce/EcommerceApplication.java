package com.ecommerce.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ecommerce.ecommerce.component_.UserController;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
		UserController userController = context.getBean(UserController.class);
		userController.greetUser("Satyam");
	}

}

        //          🚀 Spring Boot starts
        //                  │
        //                  ↓
        //       @SpringBootApplication
        //                  │
        //                  ↓
        //          Component Scanning
        //                  │
        //                  ↓
        //       Finds @Component classes
        //                  │
        //                  ↓
        //         ┌─────────────────┐
        //         │ GreetingService │
        //         │   @Component    │
        //         └────────┬────────┘
        //                  │
        //                  ↓
        //       Spring creates object
        //                  │
        //                  ↓
        //           Spring Bean
        //                  │
        //                  ↓
        //       Spring Container
        //       (ApplicationContext)
        //                  │
        //                  ↓
        //   ┌─────────────────────────┐
        //   │  GreetingController     │
        //   │                         │
        //   │ GreetingService service │
        //   └────────────┬────────────┘
        //                ↑
        //                │
        //          Spring injects it