package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Appointment;
import com.dietitianTrackingApp.entity.AppointmentStatus;
import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.repository.AppointmentRepository;
import com.dietitianTrackingApp.repository.DietitianRepository;
import com.dietitianTrackingApp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, 
                             PatientRepository patientRepository,
                             DietitianRepository dietitianRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> findAppointmentsByDietitianId(Long dietitianId) {
        return appointmentRepository.findByDietitianId(dietitianId);
    }

    public List<Appointment> findAppointmentsByPatientIdAndStatus(Long patientId, AppointmentStatus status) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, status);
    }

    public List<Appointment> findAppointmentsByDietitianIdAndStatus(Long dietitianId, AppointmentStatus status) {
        return appointmentRepository.findByDietitianIdAndStatus(dietitianId, status);
    }

    public List<Appointment> findAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateTimeBetween(startDate, endDate);
    }

    public List<Appointment> findAppointmentsByDietitianIdAndDateRange(Long dietitianId, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByDietitianIdAndAppointmentDateTimeBetween(dietitianId, startDate, endDate);
    }

    public List<Appointment> findAppointmentsByPatientIdAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByPatientIdAndAppointmentDateTimeBetween(patientId, startDate, endDate);
    }

    public List<Appointment> getUpcomingAppointmentsByDietitianId(Long dietitianId) {
        LocalDateTime now = LocalDateTime.now();
        return appointmentRepository.findByDietitianIdAndAppointmentDateTimeBetween(dietitianId, now, now.plusYears(100));
    }

    @Transactional
    public Appointment createAppointment(Long patientId, Long dietitianId, Appointment appointment) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new IllegalArgumentException("Dietitian not found"));

        appointment.setPatient(patient);
        appointment.setDietitian(dietitian);

        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment save(Appointment newAppointment) {
        // Diyetisyen bilgisini set et
        if (newAppointment.getDietitian() != null) {
            Dietitian dietitian = dietitianRepository.findById(newAppointment.getDietitian().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Dietitian not found"));
            newAppointment.setDietitian(dietitian);
        }

        // Hastayı set et
        if (newAppointment.getPatient() != null) {
            Patient patient = patientRepository.findById(newAppointment.getPatient().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
            newAppointment.setPatient(patient);
        }

        return appointmentRepository.save(newAppointment);
    }
}
