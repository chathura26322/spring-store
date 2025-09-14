package com.codewithmosh.store.repositories;

import com.codewithmosh.store.dtos.UserSummary;
import com.codewithmosh.store.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"tags","addresses"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "addresses")
    @Query("select u from User u")
    List<User> findAllWithAddress();

    @EntityGraph(attributePaths = {"profile"})
    @Query("SELECT u AS user FROM User u JOIN u.profile p WHERE p.loyaltyPoints > :points ORDER BY u.email ")
    List<UserSummary> findUsersByProfileLoyaltyPoints(Integer points);
}
