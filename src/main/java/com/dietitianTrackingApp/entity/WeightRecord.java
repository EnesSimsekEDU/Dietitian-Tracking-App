package com.dietitianTrackingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weight_records")
public class WeightRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weight_record_seq")
    @SequenceGenerator(name = "weight_record_seq", sequenceName = "weight_record_seq", initialValue = 5, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    private Double weight; // kg cinsinden

    @Column
    private Double muscleWeight; // kas ağırlığı

    @Column
    private Double fatWeight; // yağ ağırlığı

    @Column
    private Double boneWeight; // kemik ağırlığı

    @Column
    private Double muscleRatio; // kas oranı

    @Column
    private Double fatRatio; // yağ oranı

    @Column
    private Double bodyWaterRatio; // vücut su oranı

    @Column
    private Double bmiScore; // vücut kitle indeksi puanı

    @Column(nullable = false)
    private LocalDateTime recordDate;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
