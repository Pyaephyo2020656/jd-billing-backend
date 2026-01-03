package com.tjd.invoicemanagement.controller;

import com.tjd.invoicemanagement.model.User;
import com.tjd.invoicemanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user); 
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable Integer id) {
        try {
            User updatedUser = userService.toggleUserStatus(id);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    

    @GetMapping
    public List<User> list() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
    
    
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody String newRole) {
        try {
            // Double quotes တွေ ပါလာရင် ဖယ်ထုတ်ပစ်ရန်
            String cleanedRole = newRole.replace("\"", ""); 
            User updatedUser = userService.updateUserRole(id, cleanedRole);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}