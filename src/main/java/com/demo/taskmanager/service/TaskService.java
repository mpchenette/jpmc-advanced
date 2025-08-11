package com.demo.taskmanager.service;

import com.demo.taskmanager.model.Priority;
import com.demo.taskmanager.model.Task;
import com.demo.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    /**
     * Create new task with validation
     */
    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        
        // Set creation date if not already set
        if (task.getCreatedDate() == null) {
            task.setCreatedDate(LocalDateTime.now());
        }
        
        // Validate due date is not in the past
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        
        return taskRepository.save(task);
    }
    
    /**
     * Get task by ID
     */
    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }
    
    /**
     * Get all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * Mark task as completed
     */
    public Task markTaskAsCompleted(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompleted(true);
            return taskRepository.save(task);
        }
        throw new RuntimeException("Task not found with id: " + id);
    }
    
    /**
     * Update an existing task
     */
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTaskOpt = taskRepository.findById(id);
        if (existingTaskOpt.isPresent()) {
            Task existingTask = existingTaskOpt.get();
            
            // Update fields
            if (updatedTask.getTitle() != null) {
                existingTask.setTitle(updatedTask.getTitle());
            }
            if (updatedTask.getDescription() != null) {
                existingTask.setDescription(updatedTask.getDescription());
            }
            if (updatedTask.getDueDate() != null) {
                existingTask.setDueDate(updatedTask.getDueDate());
            }
            if (updatedTask.getCompleted() != null) {
                existingTask.setCompleted(updatedTask.getCompleted());
            }
            if (updatedTask.getPriority() != null) {
                existingTask.setPriority(updatedTask.getPriority());
            }
            if (updatedTask.getCategory() != null) {
                existingTask.setCategory(updatedTask.getCategory());
            }
            
            return taskRepository.save(existingTask);
        }
        throw new RuntimeException("Task not found with id: " + id);
    }
    
    /**
     * Delete a task
     */
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
    
    /**
     * Get tasks by completion status
     */
    public List<Task> getTasksByCompletionStatus(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
    
    /**
     * Find overdue tasks
     */
    public List<Task> getOverdueTasks() {
        return taskRepository.findOverdueTasks();
    }
    
    /**
     * Find tasks due today
     */
    public List<Task> getTasksDueToday() {
        return taskRepository.findTasksDueToday();
    }
    
    /**
     * Search tasks by title or description
     */
    public List<Task> searchTasks(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllTasks();
        }
        return taskRepository.findByTitleOrDescriptionContaining(searchTerm.trim());
    }
    
    /**
     * Get task completion statistics
     */
    public TaskStatistics getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByCompleted(true);
        long pendingTasks = taskRepository.countByCompleted(false);
        long overdueTasks = taskRepository.findOverdueTasks().size();
        
        return new TaskStatistics(totalTasks, completedTasks, pendingTasks, overdueTasks);
    }
    
    /**
     * Get tasks by priority
     */
    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }
    
    /**
     * Get tasks by category
     */
    public List<Task> getTasksByCategory(String category) {
        return taskRepository.findByCategoryIgnoreCase(category);
    }
    
    /**
     * Get all tasks sorted by priority (High to Low)
     */
    public List<Task> getAllTasksSortedByPriority() {
        return taskRepository.findAllOrderedByPriority();
    }
    
    /**
     * Get high priority incomplete tasks
     */
    public List<Task> getHighPriorityIncompleteTasks() {
        return taskRepository.findHighPriorityIncompleteTasks();
    }
    
    /**
     * Inner class for task statistics
     */
    public static class TaskStatistics {
        private final long totalTasks;
        private final long completedTasks;
        private final long pendingTasks;
        private final long overdueTasks;
        
        public TaskStatistics(long totalTasks, long completedTasks, long pendingTasks, long overdueTasks) {
            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.pendingTasks = pendingTasks;
            this.overdueTasks = overdueTasks;
        }
        
        public long getTotalTasks() { return totalTasks; }
        public long getCompletedTasks() { return completedTasks; }
        public long getPendingTasks() { return pendingTasks; }
        public long getOverdueTasks() { return overdueTasks; }
        
        public double getCompletionRate() {
            return totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;
        }
    }
}
