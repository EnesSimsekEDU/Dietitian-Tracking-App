package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.*;
import com.dietitianTrackingApp.service.NutritionPlanService;
import com.dietitianTrackingApp.service.PatientService;
import com.dietitianTrackingApp.service.WeightRecordService;
import org.primefaces.PrimeFaces;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
@ViewScoped
@Getter
@Setter
@ToString(exclude = {"patient", "loggedInUser", "latestNutritionPlan", "nutritionPlans", "weightRecords"})
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsBean implements Serializable {

    private Long patientId;
    private Patient patient;
    private User loggedInUser;
    private NutritionPlan latestNutritionPlan;
    private WeightRecord latestWeightRecord;
    private List<NutritionPlan> nutritionPlans;
    private List<WeightRecord> weightRecords;
    private boolean accessAuthorized = false;

    // Dialog control
    private boolean weightRecordDialogVisible = false;
    private boolean nutritionPlanDialogVisible = false;

    // New records
    private WeightRecord newWeightRecord = new WeightRecord();
    private NutritionPlan newNutritionPlan = new NutritionPlan();

    @Inject
    private PatientService patientService;

    @Inject
    private NutritionPlanService nutritionPlanService;

    @Inject
    private WeightRecordService weightRecordService;

    @PostConstruct
    public void init() {
        // Giriş yapan kullanıcıyı al
        loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        // URL'den patientId parametresini al
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        String patientIdParam = params.get("patientId");

        if (patientIdParam != null && !patientIdParam.isEmpty()) {
            try {
                patientId = Long.valueOf(patientIdParam);

                // Hasta bilgilerini yükle
                Optional<Patient> patientOpt = patientService.getPatientById(patientId);
                if (patientOpt.isPresent()) {
                    patient = patientOpt.get();

                    // Sadece bu hastanın diyetisyeni erişebilmeli
                    if (loggedInUser != null && loggedInUser.getRole().name().equals("DIETITIAN")) {
                        if (patient.getDietitian().getUser().getId().equals(loggedInUser.getId())) {
                            accessAuthorized = true;

                            // En son beslenme planını al
                            latestNutritionPlan = nutritionPlanService.getLatestNutritionPlanByPatientId(patientId);

                            // En son kilo kaydını al
                            latestWeightRecord = weightRecordService.getLatestWeightRecordByPatientId(patientId);

                            // Tüm beslenme planlarını al
                            nutritionPlans = nutritionPlanService.getNutritionPlansByPatientId(patientId);

                            // Tüm kilo kayıtlarını al
                            weightRecords = weightRecordService.getWeightRecordsByPatientId(patientId);
                        } else {
                            // Yetki hatası
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR, "Yetki Hatası", 
                                    "Bu hastanın bilgilerine erişim yetkiniz bulunmamaktadır."));
                        }
                    } else {
                        // Kullanıcı diyetisyen değil
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, "Yetki Hatası", 
                                "Bu sayfaya erişim yetkiniz bulunmamaktadır."));
                    }
                } else {
                    // Hasta bulunamadı
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Hata", "Hasta bulunamadı."));
                }
            } catch (NumberFormatException e) {
                // Geçersiz hasta ID
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Hata", "Geçersiz hasta ID."));
            }
        } else {
            // Hasta ID parametresi eksik
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Hata", "Hasta ID parametresi eksik."));
        }
    }

    // BMI hesaplama metodu
    public double calculateBMI() {
        if (patient != null && latestWeightRecord != null && patient.getHeight() != null) {
            double heightInMeters = patient.getHeight() / 100.0; // cm -> m
            return Math.round((latestWeightRecord.getWeight() / (heightInMeters * heightInMeters)) * 10.0) / 10.0;
        }
        return 0.0;
    }

    // BMI durumu (Normal, Fazla kilolu, vs.)
    public String getBMIStatus() {
        double bmi = calculateBMI();
        if (bmi < 18.5) return "Zayıf";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Fazla Kilolu";
        else return "Obez";
    }

    // Yaş hesaplama metodu
    public int calculateAge() {
        if (patient != null && patient.getBirthDate() != null) {
            return Period.between(patient.getBirthDate(), LocalDate.now()).getYears();
        }
        return 0;
    }

    // Kilo değişimi yüzdesi hesaplama
    public double calculateWeightChangePercent() {
        if (weightRecords != null && weightRecords.size() >= 2) {
            WeightRecord firstRecord = weightRecords.get(weightRecords.size() - 1);
            WeightRecord lastRecord = weightRecords.get(0);

            double change = lastRecord.getWeight() - firstRecord.getWeight();
            return Math.round((change / firstRecord.getWeight() * 100) * 10.0) / 10.0;
        }
        return 0.0;
    }

    // Dialog control methods
    public void openWeightRecordDialog() {
        this.newWeightRecord = new WeightRecord();
        this.newWeightRecord.setRecordDate(LocalDateTime.now());
        this.weightRecordDialogVisible = true;
    }

    public void closeWeightRecordDialog() {
        this.weightRecordDialogVisible = false;
    }

    public void openNutritionPlanDialog() {
        this.newNutritionPlan = new NutritionPlan();
        this.newNutritionPlan.setStartDate(LocalDate.now());
        this.nutritionPlanDialogVisible = true;
    }

    public void closeNutritionPlanDialog() {
        this.nutritionPlanDialogVisible = false;
    }

    // Save methods
    public void saveWeightRecord() {
        try {
            this.newWeightRecord = weightRecordService.createWeightRecord(patientId, newWeightRecord);

            // Refresh weight records list
            this.weightRecords = weightRecordService.getWeightRecordsByPatientId(patientId);
            this.latestWeightRecord = weightRecordService.getLatestWeightRecordByPatientId(patientId);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni ölçüm kaydedildi."));

            // Update the UI
            PrimeFaces.current().ajax().update("messagesForm:messages", "weightRecordsTable", "patientInfoTable");

            closeWeightRecordDialog();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Hata", "Ölçüm kaydedilirken bir hata oluştu: " + e.getMessage()));
        }
    }

    public void saveNutritionPlan() {
        try {
            // Get dietitian ID from the patient's dietitian
            Long dietitianId = patient.getDietitian().getId();

            this.newNutritionPlan = nutritionPlanService.createNutritionPlan(
                    patientId, dietitianId, newNutritionPlan);

            // Refresh nutrition plans list
            this.nutritionPlans = nutritionPlanService.getNutritionPlansByPatientId(patientId);
            this.latestNutritionPlan = nutritionPlanService.getLatestNutritionPlanByPatientId(patientId);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni diyet planı kaydedildi."));

            // Update the UI
            PrimeFaces.current().ajax().update("messagesForm:messages", "nutritionPlansTable", "patientInfoTable");

            closeNutritionPlanDialog();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Hata", "Diyet planı kaydedilirken bir hata oluştu: " + e.getMessage()));
        }
    }
}
