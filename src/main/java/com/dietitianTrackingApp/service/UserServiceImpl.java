package com.dietitianTrackingApp.service;

import com.dietitianTrackingApp.entity.User;
import com.dietitianTrackingApp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @PostConstruct
    public void init() {
        // Uygulama başlatıldığında admin kullanıcısını oluştur
        initAdminUser();
    }
    
    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not found"));
        
        if (user != null && user.getPassword().equals(password)) {
            // Gerçek uygulamada şifre karşılaştırması için daha güvenli bir yöntem kullanılmalıdır
            return user;
        }
        
        return null;
    }
    
    @Override
    @Transactional
    public void initAdminUser() {
     /*   // Admin kullanıcısı zaten var mı kontrol et
        User existingAdmin = userRepository.findByEmail("admin@dietitian.com").orElse(null);
        
        if (existingAdmin == null) {
            // Admin kullanıcısı oluştur
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setSurname("User");
            adminUser.setEmail("admin@dietitian.com");
            adminUser.setPassword("admin123"); // Gerçek uygulamada şifre hashlenmelidir
            adminUser.setFullName("Admin User");
            adminUser.setRole(UserRole.ADMIN);
            
            userRepository.save(adminUser);
        }*/
    }
}