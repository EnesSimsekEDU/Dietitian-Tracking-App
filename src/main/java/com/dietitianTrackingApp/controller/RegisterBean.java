package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.entity.UserRole;
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
public class RegisterBean implements Serializable {
    
    private String name;
    private String surname;
    private String email;
    private String password;
    private String confirmPassword;
    
    @Inject
    private UserService userService;
    
    @PostConstruct
    public void init() {
        // İsteğe bağlı initialization
    }
    
    public String register() {
        // Şifre doğrulama kontrolü
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Şifre Hatası", "Şifreler eşleşmiyor"));
            return null;
        }
        
        try {
            // Kullanıcı zaten var mı kontrolü
            if (userService.findByEmail(email) != null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Kayıt Hatası", "Bu e-posta adresi zaten kullanılıyor"));
                return null;
            }
            
            // Yeni kullanıcı oluştur
            User newUser = new User();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setEmail(email);
            newUser.setPassword(password); // Gerçek uygulamada şifre hashlenmelidir
            newUser.setFullName(name + " " + surname);
            newUser.setRole(UserRole.PATIENT); // Varsayılan olarak hasta rolü
            
            // Kullanıcıyı kaydet
            userService.save(newUser);
            
            // Başarılı kayıt mesajı
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            "Kayıt Başarılı", "Hesabınız başarıyla oluşturuldu"));
            
            // Giriş sayfasına yönlendir
            return "login?faces-redirect=true";
            
        } catch (Exception e) {
            // Hata mesajı
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Kayıt Hatası", "Kayıt sırasında bir hata oluştu: " + e.getMessage()));
            return null;
        }
    }
}