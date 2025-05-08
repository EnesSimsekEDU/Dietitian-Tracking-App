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



    public Optional<Dietitian> getDietitianByUserId(User user) {
        return dietitianRepository.findDietitianByUser(user);
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

    public Dietitian save(Dietitian newDietitian) {
    return dietitianRepository.save(newDietitian);
    }
}
