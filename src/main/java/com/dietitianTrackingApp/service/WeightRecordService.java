package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.entity.WeightRecord;
import com.dietitianTrackingApp.repository.PatientRepository;
import com.dietitianTrackingApp.repository.WeightRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public WeightRecordService(WeightRecordRepository weightRecordRepository, PatientRepository patientRepository) {
        this.weightRecordRepository = weightRecordRepository;
        this.patientRepository = patientRepository;
    }

    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    public Optional<WeightRecord> getWeightRecordById(Long id) {
        return weightRecordRepository.findById(id);
    }

    public List<WeightRecord> getWeightRecordsByPatientId(Long patientId) {
        return weightRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId);
    }

    public List<WeightRecord> getWeightRecordsByPatientIdAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return weightRecordRepository.findByPatientIdAndRecordDateBetween(patientId, startDate, endDate);
    }

    public WeightRecord getLatestWeightRecordByPatientId(Long patientId) {
        return weightRecordRepository.findLatestByPatientId(patientId);
    }

    @Transactional
    public WeightRecord saveWeightRecord(WeightRecord weightRecord) {
        return weightRecordRepository.save(weightRecord);
    }

    @Transactional
    public WeightRecord createWeightRecord(Long patientId, WeightRecord weightRecord) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Hasta bulunamadı");
        }

        Patient patient = patientOpt.get();
        weightRecord.setPatient(patient);

        if (weightRecord.getRecordDate() == null) {
            weightRecord.setRecordDate(LocalDateTime.now());
        }

        return weightRecordRepository.save(weightRecord);
    }

    @Transactional
    public void deleteWeightRecord(Long id) {
        weightRecordRepository.deleteById(id);
    }
}