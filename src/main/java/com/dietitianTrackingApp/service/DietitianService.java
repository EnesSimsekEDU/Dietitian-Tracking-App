package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.Dietitian;
import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.entity.UserRole;
import com.dietitianTrackingApp.repository.DietitianRepository;
import com.dietitianTrackingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DietitianService {

    private final DietitianRepository dietitianRepository;
    private final UserRepository userRepository;

    @Autowired
    public DietitianService(DietitianRepository dietitianRepository, UserRepository userRepository) {
        this.dietitianRepository = dietitianRepository;
        this.userRepository = userRepository;
    }

    public List<Dietitian> findAllDietitians() {
        return dietitianRepository.findAll();
    }

    public Optional<Dietitian> findDietitianById(Long id) {
        return dietitianRepository.findById(id);
    }

    public Optional<Dietitian> findDietitianByUserId(Long userId) {
        return dietitianRepository.findByUserId(userId);
    }

    public Optional<Dietitian> getDietitianByUserId(Long userId) {
        return findDietitianByUserId(userId);
    }

    public Optional<Dietitian> findDietitianByEmail(String email) {
        return dietitianRepository.findByUserEmail(email);
    }

    public List<Dietitian> findDietitiansBySpecialization(String specialization) {
        return dietitianRepository.findBySpecialization(specialization);
    }

    public List<Dietitian> searchDietitiansByName(String name) {
        return dietitianRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public Dietitian createDietitian(User user, Dietitian dietitian) {
        if (user.getId() == null) {
            // Yeni kullanıcı oluşturur
            user.setRole(UserRole.DIETITIAN);
            user = userRepository.save(user);
        } else {
            // Mevcut kullanıcı rolünü günceller
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existingUser.setRole(UserRole.DIETITIAN);
            user = userRepository.save(existingUser);
        }

        dietitian.setUser(user);
        return dietitianRepository.save(dietitian);
    }

    @Transactional
    public Dietitian updateDietitian(Dietitian dietitian) {
        return dietitianRepository.save(dietitian);
    }

    @Transactional
    public void deleteDietitian(Long id) {
        dietitianRepository.deleteById(id);
    }
}
