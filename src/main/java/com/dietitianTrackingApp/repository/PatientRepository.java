package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByUserEmail(String email);
    
    Optional<Patient> findByUserId(Long userId);
    
    List<Patient> findByDietitianId(Long dietitianId);
    
    @Query("SELECT p FROM Patient p WHERE LOWER(p.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Patient p WHERE p.dietitian.id = :dietitianId AND LOWER(p.user.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByDietitianIdAndNameContainingIgnoreCase(Long dietitianId, String name);
}