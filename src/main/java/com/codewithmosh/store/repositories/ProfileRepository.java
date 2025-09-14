package com.codewithmosh.store.repositories;

import com.codewithmosh.store.dtos.UserSummary;
import com.codewithmosh.store.entities.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
//
//    @EntityGraph(attributePaths = "user")
//    @Query("SELECT p FROM Profile p WHERE p.loyaltyPoints > :points ORDER BY p.user.email ")
//    List<UserSummary> findByLoyaltyPointsGreaterThan(Integer points);
}
