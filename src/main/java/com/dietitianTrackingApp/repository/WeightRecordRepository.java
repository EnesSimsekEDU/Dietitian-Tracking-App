package com.dietitianTrackingApp.repository;

import com.dietitianTrackingApp.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
    
    List<WeightRecord> findByPatientId(Long patientId);

    // En son kaydedilen ölçüm kayıtlarını tarih sırasına göre tersine sırala
    List<WeightRecord> findByPatientIdOrderByRecordDateDesc(Long patientId);

    // Belirli bir tarih aralığındaki ölçüm kayıtlarını getir
    List<WeightRecord> findByPatientIdAndRecordDateBetween(Long patientId, LocalDateTime startDate, LocalDateTime endDate);

    // En son kaydedilen ölçüm kaydını getir
    @Query("SELECT w FROM WeightRecord w WHERE w.patient.id = :patientId ORDER BY w.recordDate DESC LIMIT 1")
    WeightRecord findLatestByPatientId(Long patientId);

}