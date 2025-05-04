package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.User;

import java.util.List;

public interface UserService {
    
    User save(User user);
    
    User findById(Long id);
    
    User findByEmail(String email);
    
    List<User> findAll();
    
    void delete(Long id);
    
    User authenticate(String email, String password);
    
    void initAdminUser();
}