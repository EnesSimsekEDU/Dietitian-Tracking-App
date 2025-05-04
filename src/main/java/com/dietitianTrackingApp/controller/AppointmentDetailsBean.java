package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.Appointment;
import com.dietitianTrackingApp.entity.AppointmentStatus;
import com.dietitianTrackingApp.entity.Consultation;
import com.dietitianTrackingApp.service.AppointmentService;
import com.dietitianTrackingApp.service.ConsultationService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
@ViewScoped
public class AppointmentDetailsBean implements Serializable {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ConsultationService consultationService;

    @Getter @Setter
    private Long appointmentId;

    @Getter @Setter
    private Appointment appointment;

    @Getter @Setter
    private Consultation consultation;

    @Getter @Setter
    private boolean editMode = false;

    @Getter
    private List<AppointmentStatus> statusOptions;

    @PostConstruct
    public void init() {
        statusOptions = Arrays.asList(AppointmentStatus.values());
        loadAppointment();
    }

    public void loadAppointment() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String appointmentIdParam = params.get("appointmentId");
        
        if (appointmentIdParam != null && !appointmentIdParam.isEmpty()) {
            try {
                appointmentId = Long.parseLong(appointmentIdParam);
                Optional<Appointment> appointmentOpt = appointmentService.findAppointmentById(appointmentId);
                
                if (appointmentOpt.isPresent()) {
                    appointment = appointmentOpt.get();
                    
                    // Load consultation if exists
                    Optional<Consultation> consultationOpt = consultationService.findConsultationByAppointmentId(appointmentId);
                    consultation = consultationOpt.orElse(new Consultation());
                    
                    if (!consultationOpt.isPresent()) {
                        consultation.setAppointment(appointment);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Randevu bulunamadı."));
                }
            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Geçersiz randevu ID."));
            }
        }
    }

    public void updateAppointmentStatus() {
        try {
            appointment = appointmentService.updateAppointment(appointment);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Randevu durumu güncellendi."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Randevu durumu güncellenirken bir hata oluştu."));
        }
    }

    public void saveConsultation() {
        try {
            if (consultation.getId() == null) {
                // New consultation
                consultationService.createConsultation(appointmentId, consultation);
                // Refresh appointment to get updated status
                appointment = appointmentService.findAppointmentById(appointmentId).orElse(appointment);
            } else {
                // Update existing consultation
                consultationService.updateConsultation(consultation);
            }
            
            editMode = false;
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Konsültasyon kaydedildi."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Konsültasyon kaydedilirken bir hata oluştu."));
        }
    }

    public void toggleEditMode() {
        editMode = !editMode;
    }

    public boolean hasConsultation() {
        return consultation != null && consultation.getId() != null;
    }

    public String getStatusLabel(AppointmentStatus status) {
        switch (status) {
            case SCHEDULED:
                return "Planlandı";
            case COMPLETED:
                return "Tamamlandı";
            case CANCELLED:
                return "İptal Edildi";
            default:
                return status.toString();
        }
    }

    public String getStatusClass(AppointmentStatus status) {
        switch (status) {
            case SCHEDULED:
                return "status-scheduled";
            case COMPLETED:
                return "status-completed";
            case CANCELLED:
                return "status-cancelled";
            default:
                return "";
        }
    }
}