package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.Appointment;
import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.service.AppointmentService;
import com.dietitianTrackingApp.service.DietitianService;
import com.dietitianTrackingApp.service.PatientService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
@Data
public class DietitianBean implements Serializable {

    private User loggedInUser;
    private Dietitian currentDietitian;
    private List<Appointment> upcomingAppointments;
    private List<Patient> patients;
    private String searchName = "";

    @Inject
    private AppointmentService appointmentService;

    @Inject
    private PatientService patientService;

    @Inject
    private DietitianService dietitianService;

    @PostConstruct
    public void init() {
        // Oturum bilgisini al
        loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            // Kullanıcı bir diyetisyen ise
            if (loggedInUser.getRole().name().equals("DIETITIAN")) {
                // Diyetisyen bilgilerini al
                Optional<Dietitian> dietitianOpt = dietitianService.getDietitianByUserId(loggedInUser.getId());
                if (dietitianOpt.isPresent()) {
                    currentDietitian = dietitianOpt.get();

                    // Bu diyetisyene ait hastaları getir
                    patients = patientService.getPatientsByDietitianId(currentDietitian.getId());

                    // Yaklaşan randevuları getir (bugün ve sonrası)
                    upcomingAppointments = appointmentService.getUpcomingAppointmentsByDietitianId(currentDietitian.getId());
                }
            }
        }
    }

    public void searchPatients() {
        if (searchName != null && !searchName.trim().isEmpty()) {
            patients = patientService.searchPatientsByDietitianIdAndNameContaining(currentDietitian.getId(), searchName);
        } else {
            // Boş arama ise tüm hastaları getir
            patients = patientService.getPatientsByDietitianId(currentDietitian.getId());
        }
    }

    public String goToPatientDetails(Long patientId) {
        return "patientDetails?faces-redirect=true&patientId=" + patientId;
    }

    // Yaş hesaplama yardımcı metodu
    public int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
        return 0;
    }
}