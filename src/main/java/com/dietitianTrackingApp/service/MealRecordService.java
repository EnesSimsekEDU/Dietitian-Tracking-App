package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.MealRecord;
import com.dietitianTrackingApp.entity.MealType;
import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.repository.MealRecordRepository;
import com.dietitianTrackingApp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MealRecordService {

    private final MealRecordRepository mealRecordRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public MealRecordService(MealRecordRepository mealRecordRepository, PatientRepository patientRepository) {
        this.mealRecordRepository = mealRecordRepository;
        this.patientRepository = patientRepository;
    }

    public List<MealRecord> findAllMealRecords() {
        return mealRecordRepository.findAll();
    }

    public Optional<MealRecord> findMealRecordById(Long id) {
        return mealRecordRepository.findById(id);
    }

    public List<MealRecord> findMealRecordsByPatientId(Long patientId) {
        return mealRecordRepository.findByPatientIdOrderByMealDateTimeDesc(patientId);
    }

    public List<MealRecord> findMealRecordsByPatientIdAndMealType(Long patientId, MealType mealType) {
        return mealRecordRepository.findByPatientIdAndMealType(patientId, mealType);
    }

    public List<MealRecord> findMealRecordsByPatientIdAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return mealRecordRepository.findByPatientIdAndMealDateTimeBetween(patientId, startDate, endDate);
    }

    public Integer getDailyCaloriesByPatientIdAndDate(Long patientId, LocalDateTime date) {
        return mealRecordRepository.sumCaloriesByPatientIdAndDate(patientId, date);
    }

    @Transactional
    public MealRecord createMealRecord(Long patientId, MealRecord mealRecord) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        
        mealRecord.setPatient(patient);
        
        if (mealRecord.getMealDateTime() == null) {
            mealRecord.setMealDateTime(LocalDateTime.now());
        }
        
        return mealRecordRepository.save(mealRecord);
    }

    @Transactional
    public MealRecord updateMealRecord(MealRecord mealRecord) {
        return mealRecordRepository.save(mealRecord);
    }

    @Transactional
    public void deleteMealRecord(Long id) {
        mealRecordRepository.deleteById(id);
    }
}