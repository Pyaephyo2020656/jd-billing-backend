package com.tjd.invoicemanagement.service;

import com.tjd.invoicemanagement.model.User;
import com.tjd.invoicemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User login(String username, String password) {
        return userRepo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password)) // Password စစ်ခြင်း
                .filter(User::getEnabled) // အကောင့် enabled ဖြစ်မဖြစ် ထပ်စစ်ခြင်း (False ဖြစ်လျှင် null ထွက်မည်)
                .orElse(null);
    }
    
    public User toggleUserStatus(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.getEnabled()); // လက်ရှိ status ကို ပြောင်းပြန်လှန်ခြင်း
        return userRepo.save(user);
    }
    

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }
    
    public User updateUserRole(Integer id, String newRole) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole); // Role အသစ်ကို သတ်မှတ်ခြင်း
        return userRepo.save(user);
    }
}