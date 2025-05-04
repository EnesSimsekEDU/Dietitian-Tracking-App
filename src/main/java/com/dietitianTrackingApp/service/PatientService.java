package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.entity.UserRole;
import com.dietitianTrackingApp.repository.DietitianRepository;
import com.dietitianTrackingApp.repository.PatientRepository;
import com.dietitianTrackingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DietitianRepository dietitianRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, UserRepository userRepository, 
                         DietitianRepository dietitianRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> findPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return patientRepository.findByUserEmail(email);
    }

    public List<Patient> findPatientsByDietitianId(Long dietitianId) {
        return patientRepository.findByDietitianId(dietitianId);
    }

    public List<Patient> getPatientsByDietitianId(Long dietitianId) {
        return findPatientsByDietitianId(dietitianId);
    }

    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Patient> searchPatientsByDietitianAndName(Long dietitianId, String name) {
        return patientRepository.findByDietitianIdAndNameContainingIgnoreCase(dietitianId, name);
    }

    public List<Patient> searchPatientsByDietitianIdAndNameContaining(Long dietitianId, String name) {
        return searchPatientsByDietitianAndName(dietitianId, name);
    }

    @Transactional
    public Patient createPatient(User user, Patient patient, Long dietitianId) {
        if (user.getId() == null) {
            // Yeni kullanıcı oluşturur
            user.setRole(UserRole.PATIENT);
            user = userRepository.save(user);
        } else {
            // Mevcut kullanıcı rolünü günceller
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existingUser.setRole(UserRole.PATIENT);
            user = userRepository.save(existingUser);
        }

        patient.setUser(user);

        if (dietitianId != null) {
            Dietitian dietitian = dietitianRepository.findById(dietitianId)
                    .orElseThrow(() -> new IllegalArgumentException("Dietitian not found"));
            patient.setDietitian(dietitian);
        }

        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient assignDietitian(Long patientId, Long dietitianId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new IllegalArgumentException("Dietitian not found"));

        patient.setDietitian(dietitian);
        return patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Optional<Patient> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }
}
