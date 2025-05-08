package com.dietitianTrackingApp.converter;

import com.dietitianTrackingApp.entity.Patient;
import com.dietitianTrackingApp.service.PatientService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import java.util.Optional;

@FacesConverter(value = "patientConverter", managed = true)
public class PatientConverter implements Converter<Patient> {

    private PatientService getPatientService(FacesContext context) {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(context);
        return ctx.getBean(PatientService.class);
    }

    @Override
    public Patient getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            PatientService patientService = getPatientService(context);
            Patient patient = patientService.findById(Long.valueOf(value));
            if (patient == null) {
                throw new ConverterException("Hasta bulunamadı: " + value);
            }
            return patient;
        } catch (NumberFormatException e) {
            throw new ConverterException("Geçersiz hasta ID: " + value, e);
        } catch (Exception e) {
            throw new ConverterException("Dönüştürme hatası: " + e.getMessage(), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Patient patient) {
        if (patient == null || patient.getId() == null) {
            return "";
        }
        return String.valueOf(patient.getId());
    }
}