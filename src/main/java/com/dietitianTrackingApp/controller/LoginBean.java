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
public class LoginBean implements Serializable {

    private String email;
    private String password;
    private boolean rememberMe;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        // İsteğe bağlı initialization
    }

    public String login() {
        try {
            User authenticatedUser = userService.authenticate(email, password);

            if (authenticatedUser != null) {
                // Session kullanıcı bilgilerini sakla
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("loggedInUser", authenticatedUser);

                // Başarılı giriş mesajı
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Giriş Başarılı", "Hoş geldiniz, " + authenticatedUser.getFullName()));

                // Diyetisyen sayfasına yönlendir
                if (authenticatedUser.getRole().name().equals("DIETITIAN")) {
                    return "dietitian?faces-redirect=true";
                } else {
                    return "index?faces-redirect=true";
                }
            } else {
                // Hatalı giriş mesajı
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Giriş Başarısız", "E-posta veya şifre hatalı"));
                return null;
            }
        } catch (Exception e) {
            // Hata mesajı
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hata", "Giriş sırasında bir hata oluştu: " + e.getMessage()));
            return null;
        }
    }

    public String logout() {
        // Session bilgilerini temizle
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
}
