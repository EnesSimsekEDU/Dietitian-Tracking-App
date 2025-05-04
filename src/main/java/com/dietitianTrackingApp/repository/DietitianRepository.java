package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietitianRepository extends JpaRepository<Dietitian, Long> {
    
    Optional<Dietitian> findByUserEmail(String email);
    
    Optional<Dietitian> findByUserId(Long userId);
    
    List<Dietitian> findBySpecialization(String specialization);
    
    @Query("SELECT d FROM Dietitian d WHERE LOWER(d.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Dietitian> findByNameContainingIgnoreCase(String name);
}