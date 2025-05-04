package com.dietitianTrackingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal_plans")
public class MealPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrition_plan_id")
    private NutritionPlan nutritionPlan;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String foodItems;
    
    private Integer calories;
    
    private Double protein; // gram cinsinden
    
    private Double carbs; // gram cinsinden
    
    private Double fat; // gram cinsinden
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}