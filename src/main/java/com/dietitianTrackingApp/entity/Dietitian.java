package com.dietitianTrackingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dietitians")
@ToString(exclude = {"patients", "user"}) // Tüm lazy koleksiyonları ve lazy entity'leri toString()'den hariç tut
public class Dietitian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private String title;

    private String specialization;

    private String licenseNumber;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @OneToMany(mappedBy = "dietitian", cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();

}
