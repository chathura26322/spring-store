package com.codewithmosh.store.UserRegistration;

public interface UserRepository {
     void save(User user);
     User findByEmail(String email);
}
