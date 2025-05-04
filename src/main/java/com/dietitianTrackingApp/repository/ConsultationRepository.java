package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    
    Optional<Consultation> findByAppointmentId(Long appointmentId);
    
    @Query("SELECT c FROM Consultation c WHERE c.appointment.patient.id = :patientId ORDER BY c.createdAt DESC")
    List<Consultation> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    
    @Query("SELECT c FROM Consultation c WHERE c.appointment.dietitian.id = :dietitianId ORDER BY c.createdAt DESC")
    List<Consultation> findByDietitianIdOrderByCreatedAtDesc(Long dietitianId);
}