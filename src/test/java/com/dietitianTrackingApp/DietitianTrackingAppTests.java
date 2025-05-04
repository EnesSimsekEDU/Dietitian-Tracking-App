package com.dietitianTrackingApp;

import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.entity.WeightRecord;
import com.dietitianTrackingApp.repository.PatientRepository;
import com.dietitianTrackingApp.service.WeightRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DietitianTrackingAppTests {

    @Autowired
    private WeightRecordService weightRecordService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateWeightRecord() {
        // Given
        Long patientId = 1L; // Using existing patient from schema.sql
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        assertTrue(patientOpt.isPresent(), "Patient with ID 1 should exist");

        WeightRecord weightRecord = new WeightRecord();
        weightRecord.setWeight(72.5);
        weightRecord.setMuscleWeight(29.0);
        weightRecord.setFatWeight(21.8);
        weightRecord.setBoneWeight(10.7);
        weightRecord.setMuscleRatio(40.0);
        weightRecord.setFatRatio(30.0);
        weightRecord.setBodyWaterRatio(55.0);
        weightRecord.setBmiScore(24.8);
        weightRecord.setRecordDate(LocalDateTime.now());
        weightRecord.setNotes("Test weight record with all new fields");

        // When
        System.out.println("[DEBUG_LOG] Creating new weight record for patient ID: " + patientId);
        WeightRecord savedRecord = weightRecordService.createWeightRecord(patientId, weightRecord);

        // Then
        assertNotNull(savedRecord, "Saved weight record should not be null");
        assertNotNull(savedRecord.getId(), "Saved weight record should have an ID");
        System.out.println("[DEBUG_LOG] Successfully created weight record with ID: " + savedRecord.getId());
    }
}
