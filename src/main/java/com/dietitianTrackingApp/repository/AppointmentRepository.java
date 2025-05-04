package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.Appointment;
import com.dietitianTrackingApp.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByPatientId(Long patientId);
    
    List<Appointment> findByDietitianId(Long dietitianId);
    
    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);
    
    List<Appointment> findByDietitianIdAndStatus(Long dietitianId, AppointmentStatus status);
    
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Appointment> findByDietitianIdAndAppointmentDateTimeBetween(Long dietitianId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Appointment> findByPatientIdAndAppointmentDateTimeBetween(Long patientId, LocalDateTime startDate, LocalDateTime endDate);
}