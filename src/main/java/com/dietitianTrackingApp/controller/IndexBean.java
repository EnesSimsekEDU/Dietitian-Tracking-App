package com.dietitianTrackingApp.controller;

import com.dietitianTrackingApp.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;

@Named
@ViewScoped
@Data
public class IndexBean implements Serializable {

    private User loggedInUser;

    @PostConstruct
    public void init() {
        // Oturum bilgisini al
        loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");
    }
}
