package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    User save(User user);
    
    User findById(Long id);
    
    Optional<User> findByEmail(String email);
    
    List<User> findAll();
    
    void delete(Long id);
    
    User authenticate(String email, String password);
    
    void initAdminUser();
}