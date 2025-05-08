package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietitianRepository extends JpaRepository<Dietitian, Long> {

    Optional<Dietitian> findDietitianByUser(User user);
}