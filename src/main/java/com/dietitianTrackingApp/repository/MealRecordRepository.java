package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.MealRecord;
import com.dietitianTrackingApp.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRecordRepository extends JpaRepository<MealRecord, Long> {
    
    List<MealRecord> findByPatientId(Long patientId);
    
    List<MealRecord> findByPatientIdOrderByMealDateTimeDesc(Long patientId);
    
    List<MealRecord> findByPatientIdAndMealType(Long patientId, MealType mealType);
    
    List<MealRecord> findByPatientIdAndMealDateTimeBetween(Long patientId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(m.calories) FROM MealRecord m WHERE m.patient.id = :patientId AND DATE(m.mealDateTime) = DATE(:date)")
    Integer sumCaloriesByPatientIdAndDate(Long patientId, LocalDateTime date);
}