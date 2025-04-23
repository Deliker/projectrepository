package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AchievementService achievementService;

    @Autowired
    public TaskService(TaskRepository taskRepository, AchievementService achievementService) {
        this.taskRepository = taskRepository;
        this.achievementService = achievementService;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksForUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getTasksForUserOnDate(User user, Integer year, Integer month, Integer day) {
        return taskRepository.findByUserAndYearAndMonthAndDay(user, year, month, day);
    }

    public List<Task> getTasksForUserInMonth(User user, Integer year, Integer month) {
        return taskRepository.findByUserAndYearAndMonth(user, year, month);
    }

    public Task createTask(Task task, User user) {
        task.setUser(user);
        Task savedTask = taskRepository.save(task);

        LocalDateTime taskDate = LocalDateTime.of(task.getYear(), task.getMonth() + 1, task.getDay(), 0, 0);
        LocalDateTime now = LocalDateTime.now();
        if (taskDate.isAfter(now.plusDays(7))) {
            achievementService.checkAchievement(user, "early_planner");
        }
        
        return savedTask;
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task completeTask(Long id, User user) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompleted(true);
            task.setCompletedAt(LocalDateTime.now());
            
            Task savedTask = taskRepository.save(task);
 
            checkAchievementsAfterTaskCompletion(user, task);
            
            return savedTask;
        }
        return null;
    }

    public List<Task> getCompletedTasksForUser(User user) {
        return taskRepository.findByUserAndCompletedTrue(user);
    }

    public List<Task> getIncompleteTasksForUser(User user) {
        return taskRepository.findByUserAndCompletedFalse(user);
    }

    public List<Task> getTasksByCategory(User user, String category) {
        return taskRepository.findByUserAndCategory(user, category);
    }

    public List<Task> getTasksByPriority(User user, String priority) {
        return taskRepository.findByUserAndPriority(user, priority);
    }

    public long getCompletedTaskCount(User user) {
        return taskRepository.countByUserAndCompletedTrue(user);
    }

    public Map<String, Long> getTaskCountsByCategory(User user) {
        List<Object[]> results = taskRepository.countTasksByCategory(user);
        Map<String, Long> categoryCounts = new HashMap<>();
        
        for (Object[] result : results) {
            String category = (String) result[0];
            Long count = ((Number) result[1]).longValue();
            categoryCounts.put(category, count);
        }
        
        return categoryCounts;
    }

    public List<Task> getTasksForWeek(User user, LocalDateTime startDate) {
        LocalDateTime endDate = startDate.plusDays(6);
        
        return taskRepository.findTasksForWeek(
            user,
            startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth(),
            endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth()
        );
    }

    private void checkAchievementsAfterTaskCompletion(User user, Task task) {
        long completedTasksCount = taskRepository.countByUserAndCompletedTrue(user);

        if (completedTasksCount == 1) {
            achievementService.checkAchievement(user, "beginner");
        }

        if (completedTasksCount >= 50) {
            achievementService.checkAchievement(user, "time_master");
        }

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfDay = today.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = today.toLocalDate().atTime(23, 59, 59);
        
        List<Task> tasksCompletedToday = taskRepository.findByUserAndCompletedTrueAndCompletedAtBetween(
            user, startOfDay, endOfDay
        );
 
        if (tasksCompletedToday.size() >= 5) {
            achievementService.checkAchievement(user, "productive_day");
        }

        if (tasksCompletedToday.size() >= 10) {
            achievementService.checkAchievement(user, "super_productive_day");
        }
   
        LocalDateTime completionTime = task.getCompletedAt();
        if (completionTime.getHour() < 6) {
            achievementService.checkAchievement(user, "early_bird");
        }
 
        if (completionTime.getHour() >= 0 && completionTime.getHour() < 4) {
            achievementService.checkAchievement(user, "night_owl");
        }

        if ("high".equals(task.getPriority())) {
            long highPriorityTasksCompleted = taskRepository.findByUserAndCompletedTrue(user).stream()
                .filter(t -> "high".equals(t.getPriority()))
                .count();
                
            if (highPriorityTasksCompleted >= 10) {
                achievementService.checkAchievement(user, "priority_expert");
            }
        }

        Map<String, Long> categoryCounts = getTaskCountsByCategory(user);
        if (categoryCounts.size() >= 5) {
            achievementService.checkAchievement(user, "multitasker");
        }

    }
}