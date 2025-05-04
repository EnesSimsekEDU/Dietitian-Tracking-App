package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;

@Named
@ViewScoped
@Data
public class ProfileBean implements Serializable {

    private User currentUser;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        // Get the logged-in user from the session
        currentUser = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        if (currentUser == null) {
            // Redirect to login if no user is logged in
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login");
            } catch (Exception e) {
                // Log the error
                e.printStackTrace();
            }
        }
    }

    public String updateProfile() {
        try {
            // Update fullName based on name and surname
            currentUser.setFullName(currentUser.getName() + " " + currentUser.getSurname());

            // Update user information
            userService.save(currentUser);

            // Update the session with the updated user
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("loggedInUser", currentUser);

            // Show success message
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Başarılı", "Profil bilgileriniz güncellendi"));

            return null; // Stay on the same page
        } catch (Exception e) {
            // Show error message
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hata", "Profil güncellenirken bir hata oluştu: " + e.getMessage()));
            return null;
        }
    }

    public String changePassword() {
        try {
            // Validate current password
            if (!userService.authenticate(currentUser.getEmail(), currentPassword).getId().equals(currentUser.getId())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Hata", "Mevcut şifre yanlış"));
                return null;
            }

            // Validate new password and confirmation
            if (!newPassword.equals(confirmPassword)) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Hata", "Yeni şifreler eşleşmiyor"));
                return null;
            }

            // Update password
            currentUser.setPassword(newPassword);
            userService.save(currentUser);

            // Clear password fields
            currentPassword = null;
            newPassword = null;
            confirmPassword = null;

            // Show success message
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Başarılı", "Şifreniz başarıyla değiştirildi"));

            return null; // Stay on the same page
        } catch (Exception e) {
            // Show error message
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hata", "Şifre değiştirilirken bir hata oluştu: " + e.getMessage()));
            return null;
        }
    }
}
