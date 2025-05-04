package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Appointment;
import com.dietitianTrackingApp.entity.AppointmentStatus;
import com.dietitianTrackingApp.entity.Consultation;
import com.dietitianTrackingApp.repository.AppointmentRepository;
import com.dietitianTrackingApp.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository, 
                              AppointmentRepository appointmentRepository) {
        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Consultation> findAllConsultations() {
        return consultationRepository.findAll();
    }

    public Optional<Consultation> findConsultationById(Long id) {
        return consultationRepository.findById(id);
    }

    public Optional<Consultation> findConsultationByAppointmentId(Long appointmentId) {
        return consultationRepository.findByAppointmentId(appointmentId);
    }

    public List<Consultation> findConsultationsByPatientId(Long patientId) {
        return consultationRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<Consultation> findConsultationsByDietitianId(Long dietitianId) {
        return consultationRepository.findByDietitianIdOrderByCreatedAtDesc(dietitianId);
    }

    @Transactional
    public Consultation createConsultation(Long appointmentId, Consultation consultation) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        
        // Randevu durumunu tamamlandı olarak güncelle
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
        
        consultation.setAppointment(appointment);
        return consultationRepository.save(consultation);
    }

    @Transactional
    public Consultation updateConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Transactional
    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}