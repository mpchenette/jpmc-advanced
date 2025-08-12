package com.demo.taskmanager.controller;

import com.demo.taskmanager.model.Priority;
import com.demo.taskmanager.model.Task;
import com.demo.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // For demo purposes
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    /**
     * Get all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get specific task by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> task = taskService.getTask(id);
        return task.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Create new task
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Update existing task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Mark task as completed
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id) {
        try {
            Task completedTask = taskService.markTaskAsCompleted(id);
            return ResponseEntity.ok(completedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get tasks by completion status
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Boolean completed) {
        List<Task> tasks = taskService.getTasksByCompletionStatus(completed);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get overdue tasks
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> overdueTasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(overdueTasks);
    }
    
    /**
     * Get tasks due today
     */
    @GetMapping("/due-today")
    public ResponseEntity<List<Task>> getTasksDueToday() {
        List<Task> tasksDueToday = taskService.getTasksDueToday();
        return ResponseEntity.ok(tasksDueToday);
    }
    
    /**
     * Search tasks by title or description
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query) {
        List<Task> searchResults = taskService.searchTasks(query);
        return ResponseEntity.ok(searchResults);
    }
    
    /**
     * Get task completion statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<TaskService.TaskStatistics> getTaskStatistics() {
        TaskService.TaskStatistics stats = taskService.getTaskStatistics();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Get tasks by priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get tasks by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable String category) {
        List<Task> tasks = taskService.getTasksByCategory(category);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get all tasks sorted by priority
     */
    @GetMapping("/sorted-by-priority")
    public ResponseEntity<List<Task>> getAllTasksSortedByPriority() {
        List<Task> tasks = taskService.getAllTasksSortedByPriority();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get high priority incomplete tasks
     */
    @GetMapping("/high-priority-incomplete")
    public ResponseEntity<List<Task>> getHighPriorityIncompleteTasks() {
        List<Task> tasks = taskService.getHighPriorityIncompleteTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Get task details as HTML
     * This endpoint returns HTML content
     */
    @GetMapping("/{id}/details-html")
    public ResponseEntity<String> getTaskDetailsAsHtml(@PathVariable Long id) {
        Optional<Task> taskOpt = taskService.getTask(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Task task = taskOpt.get();

        String html = "<html><body>" +
                     "<h1>Task Details</h1>" +
                     "<h2>Title: " + task.getTitle() + "</h2>" +
                     "<p>Description: " + task.getDescription() + "</p>" +
                     "<p>Priority: " + task.getPriority() + "</p>" +
                     "<p>Category: " + task.getCategory() + "</p>" +
                     "<p>Due Date: " + task.getDueDate() + "</p>" +
                     "<p>Completed: " + task.getCompleted() + "</p>" +
                     "</body></html>";
        
        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);
    }
}
