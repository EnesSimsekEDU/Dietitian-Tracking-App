package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.MealPlan;
import com.dietitianTrackingApp.entity.MealType;
import com.dietitianTrackingApp.entity.NutritionPlan;
import com.dietitianTrackingApp.repository.MealPlanRepository;
import com.dietitianTrackingApp.repository.NutritionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    public MealPlanService(MealPlanRepository mealPlanRepository, 
                          NutritionPlanRepository nutritionPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.nutritionPlanRepository = nutritionPlanRepository;
    }

    public List<MealPlan> findAllMealPlans() {
        return mealPlanRepository.findAll();
    }

    public Optional<MealPlan> findMealPlanById(Long id) {
        return mealPlanRepository.findById(id);
    }

    public List<MealPlan> findMealPlansByNutritionPlanId(Long nutritionPlanId) {
        return mealPlanRepository.findByNutritionPlanId(nutritionPlanId);
    }

    public List<MealPlan> findMealPlansByNutritionPlanIdAndMealType(Long nutritionPlanId, MealType mealType) {
        return mealPlanRepository.findByNutritionPlanIdAndMealType(nutritionPlanId, mealType);
    }

    @Transactional
    public MealPlan createMealPlan(Long nutritionPlanId, MealPlan mealPlan) {
        NutritionPlan nutritionPlan = nutritionPlanRepository.findById(nutritionPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Nutrition plan not found"));
        
        mealPlan.setNutritionPlan(nutritionPlan);
        return mealPlanRepository.save(mealPlan);
    }

    @Transactional
    public MealPlan updateMealPlan(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }

    @Transactional
    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}