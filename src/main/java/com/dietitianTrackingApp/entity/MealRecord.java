package com.dietitianTrackingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal_records")
public class MealRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;
    
    @Column(nullable = false)
    private LocalDateTime mealDateTime;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String foodItems;
    
    private Integer calories;
    
    private Double protein; // gram cinsinden
    
    private Double carbs; // gram cinsinden
    
    private Double fat; // gram cinsinden
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "image_url")
    private String imageUrl;
}