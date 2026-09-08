package com.ecommerce.ecommerce.component_;

import org.springframework.stereotype.Component;

@Component 
public class UserController {
    private final GreetingService greetingService;

    public UserController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void greetUser(String name) {
        String message = this.greetingService.greet(name);
        System.out.println(message);
    }
    
}
