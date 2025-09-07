package com.codewithmosh.store;

import com.codewithmosh.store.UserRegistration.User;
import com.codewithmosh.store.UserRegistration.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(StoreApplication.class, args);
        var userService = context.getBean(UserService.class);
        userService.registerUser(new User(1L, "Mosh", "12345", "mosh@codewithmosh.com"));
        userService.registerUser(new User(1L, "Chathura", "12345", "Chathura@gmail.com"));
    }
}
