package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Achievement;
import com.example.demo.model.Role;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.AchievementService;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;
    private final AchievementService achievementService;

    @Autowired
    public AdminController(UserService userService, TaskService taskService, AchievementService achievementService) {
        this.userService = userService;
        this.taskService = taskService;
        this.achievementService = achievementService;
    }
    
    // Admin autentifikācijas pārbaude
    private ResponseEntity<?> checkAdminAccess(Long adminId) {
        if (adminId == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Admin ID is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        if (!userService.isAdmin(adminId)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Unauthorized: Admin access required");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        
        return null; 
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(id);
        
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            User savedUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/users/admin")
    public ResponseEntity<?> createAdminUser(@RequestBody User user, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            User savedUser = userService.createAdminUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(id);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Atjauninam lietotāja laukus
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                // Paroles atjaunināšanai nepieciešama īpaša apstrāde
                user.setPassword(userDetails.getPassword());
            }
            
            // Atjauninam lomu, ja tā ir norādīta
            if (userDetails.getRole() != null) {
                user.setRole(userDetails.getRole());
            }
            
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestParam String role, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            Role newRole = Role.valueOf(role);
            User updatedUser = userService.updateUserRole(id, newRole);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid role: " + role);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Uzdevumu pārvaldība
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<?> getUserTasks(@PathVariable Long userId, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(userId);
        
        if (userOpt.isPresent()) {
            List<Task> tasks = taskService.getAllTasksForUser(userOpt.get());
            return ResponseEntity.ok(tasks);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/tasks/user/{userId}")
    public ResponseEntity<?> createTaskForUser(@PathVariable Long userId, @RequestBody Task task, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(userId);
        
        if (userOpt.isPresent()) {
            Task createdTask = taskService.createTask(task, userOpt.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            taskService.deleteTask(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete task: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/achievements")
    public ResponseEntity<?> getAllAchievements(@RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        List<Achievement> achievements = achievementService.getAllAchievements();
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/achievements/user/{userId}")
    public ResponseEntity<?> getUserAchievements(@PathVariable Long userId, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(userId);
        
        if (userOpt.isPresent()) {
            List<Achievement> achievements = achievementService.getAllAchievementsForUser(userOpt.get());
            return ResponseEntity.ok(achievements);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/achievements/grant/{userId}")
    public ResponseEntity<?> grantAchievement(@PathVariable Long userId, @RequestParam String achievementId, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Optional<User> userOpt = userService.getUserById(userId);
        
        if (!userOpt.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        try {
            Achievement achievement = achievementService.unlockAchievement(userOpt.get(), achievementId);
            if (achievement == null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Achievement already unlocked");
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(achievement);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Long id, @RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        try {
            achievementService.deleteAchievement(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Achievement deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to delete achievement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getSystemStats(@RequestParam Long adminId) {
        ResponseEntity<?> accessCheck = checkAdminAccess(adminId);
        if (accessCheck != null) return accessCheck;
        
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userService.getAllUsers().size();
 
        long totalTasks = taskService.getAllTasks().size();

        long totalAchievements = achievementService.getAllAchievements().size();
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalTasks", totalTasks);
        stats.put("totalAchievements", totalAchievements);
        
        return ResponseEntity.ok(stats);
    }
}