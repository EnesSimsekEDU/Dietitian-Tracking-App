package com.dietitianTrackingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private Double height; // cm cinsinden
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<WeightRecord> weightRecords = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MealRecord> mealRecords = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", height=" + height +
                ", userId=" + (user != null ? user.getId() : null) +
                ", dietitianId=" + (dietitian != null ? dietitian.getId() : null) +
                '}';
    }
}