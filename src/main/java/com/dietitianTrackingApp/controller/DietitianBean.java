package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.*;
import com.dietitianTrackingApp.service.AppointmentService;
import com.dietitianTrackingApp.service.DietitianService;
import com.dietitianTrackingApp.service.PatientService;
import com.dietitianTrackingApp.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Patient newPatient;
    private Patient selectedPatient;
    private Appointment newAppointment;
    private boolean showPatientDialog;
    private boolean showAppointmentDialog;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DietitianService dietitianService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        // Oturum bilgisini al
        loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            // Kullanıcı bir diyetisyen ise
            if (loggedInUser.getRole().name().equals("DIETITIAN")) {
                // Diyetisyen bilgilerini al
                Optional<Dietitian> dietitianOpt = dietitianService.getDietitianByUserId(loggedInUser);
                if (dietitianOpt.isPresent()) {
                    currentDietitian = dietitianOpt.get();

                    // Bu diyetisyene ait hastaları getir
                    loadPatients();

                    // Yaklaşan randevuları getir (bugün ve sonrası)
                    upcomingAppointments = appointmentService.getUpcomingAppointmentsByDietitianId(currentDietitian.getId());

                    // Yeni nesneleri initialize et
                    initializeNewPatient(); // Yeni hasta ve kullanıcı nesnelerini başlat
                    initializeNewAppointment(); // Yeni randevu nesnesini başlat
                }
            }
        }else{
            // Kullanıcı oturum açmamışsa, hata mesajı göster
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Oturum açılmadı"));
            // Giriş sayfasına yönlendir
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }

    private void loadPatients() {
        patients = patientService.getPatientsByDietitianId(currentDietitian.getId());
    }

    private void initializeNewAppointment() {
        newAppointment = new Appointment();
        newAppointment.setDietitian(currentDietitian);
        // Patient nesnesini null olarak bırak, selectOneMenu seçimi ile doldurulacak
        newAppointment.setStatus(AppointmentStatus.SCHEDULED);
    }

    public void searchPatients() {
        if (searchName != null && !searchName.trim().isEmpty()) {
            patients = patientService.searchPatientsByDietitianIdAndNameContaining(currentDietitian.getId(), searchName);
        } else {
            // Boş arama ise tüm hastaları getir
            loadPatients();
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

    private void initializeNewPatient() {
        newPatient = new Patient();
        User user = new User();
        user.setRole(UserRole.PATIENT);
        newPatient.setUser(user);
        // Form alanlarını temizle
        PrimeFaces.current().ajax().update("patientForm");
    }

    public void openNewPatientDialog() {
        initializeNewPatient(); // Her dialog açılışında yeni nesneler oluştur
        PrimeFaces.current().executeScript("PF('patientDialogVar').show()");
    }

    public void savePatient() {
        try {
            if (newPatient == null || newPatient.getUser() == null) {
                throw new IllegalStateException("Hasta veya kullanıcı bilgileri eksik");
            }

            // Kullanıcı bilgilerini kontrol et ve kaydet
            User userToSave = newPatient.getUser();
            userToSave.setRole(UserRole.PATIENT);

            // Varsayılan şifre oluştur
            String defaultPassword = userToSave.getEmail().split("@")[0];
            userToSave.setPassword(defaultPassword); // Şifreyi hashlemek için gerekli servis metodu kullanılmalı
            userToSave.setFullName(userToSave.getName() + " " + userToSave.getSurname());

            User savedUser = userService.save(userToSave);
            newPatient.setUser(savedUser);
            newPatient.setDietitian(currentDietitian);

            Patient savedPatient = patientService.save(newPatient);
            loadPatients();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı",
                            "Hasta kaydedildi. Geçici şifre: " + defaultPassword));

            // Dialog'u kapat
            PrimeFaces.current().executeScript("PF('patientDialogVar').hide()");

            // Form ve tabloyu güncelle
            PrimeFaces.current().ajax().update("mainForm:patientTable", "messages", "patientForm"
            ,"appointmentForm");

            // Yeni hasta nesnesini sıfırla
            initializeNewPatient();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata",
                            "Hasta kaydedilirken hata oluştu: " + e.getMessage()));
            PrimeFaces.current().ajax().update("messages");
        }
    }

    public void openNewAppointmentDialog() {
        this.newAppointment = new Appointment();
        this.newAppointment.setDietitian(currentDietitian); // Mevcut diyetisyeni set et
        PrimeFaces.current().executeScript("PF('appointmentDialogVar').show()");
    }

    public void saveAppointment() {
        try {
            if (newAppointment.getPatient() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Lütfen bir hasta seçin"));
                return;
            }
            newAppointment.setDietitian(currentDietitian);
            newAppointment.setStatus(AppointmentStatus.SCHEDULED);
            appointmentService.save(newAppointment);
            upcomingAppointments = appointmentService.getUpcomingAppointmentsByDietitianId(currentDietitian.getId());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Randevu kaydedildi"));

            PrimeFaces.current().executeScript("PF('appointmentDialogVar').hide()");
            initializeNewAppointment();
            PrimeFaces.current().ajax().update("mainForm:upcomingAppointments", "messages");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", e.getMessage()));
        }
    }

}