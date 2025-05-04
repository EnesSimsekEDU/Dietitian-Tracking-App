package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.NutritionPlan;
import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.repository.DietitianRepository;
import com.dietitianTrackingApp.repository.NutritionPlanRepository;
import com.dietitianTrackingApp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NutritionPlanService {

    private final NutritionPlanRepository nutritionPlanRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    @Autowired
    public NutritionPlanService(NutritionPlanRepository nutritionPlanRepository,
                                PatientRepository patientRepository,
                                DietitianRepository dietitianRepository) {
        this.nutritionPlanRepository = nutritionPlanRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<NutritionPlan> findAllNutritionPlans() {
        return nutritionPlanRepository.findAll();
    }

    public Optional<NutritionPlan> findNutritionPlanById(Long id) {
        return nutritionPlanRepository.findById(id);
    }

    public List<NutritionPlan> findNutritionPlansByPatientId(Long patientId) {
        return nutritionPlanRepository.findByPatientIdOrderByStartDateDesc(patientId);
    }

    public List<NutritionPlan> findNutritionPlansByDietitianId(Long dietitianId) {
        return nutritionPlanRepository.findByDietitianId(dietitianId);
    }

    public Optional<NutritionPlan> findCurrentNutritionPlanByPatientId(Long patientId) {
        return nutritionPlanRepository.findCurrentPlanByPatientId(patientId, LocalDate.now());
    }

    @Transactional
    public NutritionPlan createNutritionPlan(Long patientId, Long dietitianId, NutritionPlan nutritionPlan) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new IllegalArgumentException("Dietitian not found"));

        nutritionPlan.setPatient(patient);
        nutritionPlan.setDietitian(dietitian);

        if (nutritionPlan.getStartDate() == null) {
            nutritionPlan.setStartDate(LocalDate.now());
        }

        return nutritionPlanRepository.save(nutritionPlan);
    }

    @Transactional
    public NutritionPlan updateNutritionPlan(NutritionPlan nutritionPlan) {
        return nutritionPlanRepository.save(nutritionPlan);
    }

    @Transactional
    public void deleteNutritionPlan(Long id) {
        nutritionPlanRepository.deleteById(id);
    }

    public NutritionPlan getLatestNutritionPlanByPatientId(Long patientId) {
        return nutritionPlanRepository.findLatestByPatientId(patientId);
    }

    public List<NutritionPlan> getNutritionPlansByPatientId(Long patientId) {
        return nutritionPlanRepository.findByPatientIdOrderByStartDateDesc(patientId);
    }
}