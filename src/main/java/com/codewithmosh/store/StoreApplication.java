package com.codewithmosh.store;

import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.Profile;
import com.codewithmosh.store.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
//        ApplicationContext context=SpringApplication.run(StoreApplication.class, args);
        var user = User.builder()
                .id(1L)
                .name("John")
                .password("password")
                .email("john@example.com")
                .build();

        var profile = Profile.builder()
                .bio("bio")
                        .build();

        user.setProfile(profile);
        profile.setUser(user);

        System.out.println(user);
    }
}
