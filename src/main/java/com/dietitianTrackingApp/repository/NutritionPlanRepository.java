package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
    
    List<NutritionPlan> findByPatientId(Long patientId);
    
    List<NutritionPlan> findByDietitianId(Long dietitianId);
    
    @Query("SELECT np FROM NutritionPlan np WHERE np.patient.id = :patientId AND np.startDate <= :date AND (np.endDate IS NULL OR np.endDate >= :date)")
    Optional<NutritionPlan> findCurrentPlanByPatientId(Long patientId, LocalDate date);
    
    @Query("SELECT np FROM NutritionPlan np WHERE np.patient.id = :patientId ORDER BY np.startDate DESC")
    List<NutritionPlan> findByPatientIdOrderByStartDateDesc(Long patientId);

    @Query("SELECT np FROM NutritionPlan np WHERE np.patient.id = :patientId ORDER BY np.startDate DESC, np.createdAt DESC LIMIT 1")
    NutritionPlan findLatestByPatientId(Long patientId);
}