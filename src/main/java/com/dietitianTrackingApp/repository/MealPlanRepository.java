package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.MealPlan;
import com.dietitianTrackingApp.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    
    List<MealPlan> findByNutritionPlanId(Long nutritionPlanId);
    
    List<MealPlan> findByNutritionPlanIdAndMealType(Long nutritionPlanId, MealType mealType);
}