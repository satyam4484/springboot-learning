package com.ecommerce.ecommerce.learning.component_;

import org.springframework.stereotype.Component;

@Component 
public class GreetingService {
    public String greet(String name) {
        return "Hello" + name+ "!";
    }
}


// You write a class
//       ↓
// @Component
//       ↓
// "Spring, please manage this class"
//       ↓
// Spring finds it
//       ↓
// Spring creates the object
//       ↓
// Object becomes a Bean
//       ↓
// Spring keeps/manages that Bean
//       ↓
// Other classes can request it
//       ↓
// Spring injects the Bean

