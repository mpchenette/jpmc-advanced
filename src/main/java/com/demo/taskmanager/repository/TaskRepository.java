package com.demo.taskmanager.repository;

import com.demo.taskmanager.model.Priority;
import com.demo.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Find tasks by completion status
    List<Task> findByCompleted(Boolean completed);
    
    // Find tasks that are due before a specific date
    List<Task> findByDueDateBefore(LocalDateTime dateTime);
    
    // Find tasks containing specific text in title
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    // Find tasks containing specific text in description
    List<Task> findByDescriptionContainingIgnoreCase(String description);
    
    // Find overdue tasks (due date in the past and not completed)
    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.completed = false")
    List<Task> findOverdueTasks();
    
    // Find tasks due today
    @Query("SELECT t FROM Task t WHERE DATE(t.dueDate) = CURRENT_DATE")
    List<Task> findTasksDueToday();
    
    // Count completed tasks
    long countByCompleted(Boolean completed);
    
    // Find tasks by title or description containing search term
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Task> findByTitleOrDescriptionContaining(String searchTerm);
    
    // Find tasks by priority
    List<Task> findByPriority(Priority priority);
    
    // Find tasks by category
    List<Task> findByCategoryIgnoreCase(String category);
    
    // Find tasks sorted by priority (High to Low)
    @Query("SELECT t FROM Task t ORDER BY t.priority DESC, t.createdDate ASC")
    List<Task> findAllOrderedByPriority();
    
    // Count tasks by category
    long countByCategory(String category);
    
    // Count tasks by priority
    long countByPriority(Priority priority);
    
    // Find high priority incomplete tasks
    @Query("SELECT t FROM Task t WHERE t.priority = 'HIGH' AND t.completed = false ORDER BY t.dueDate ASC")
    List<Task> findHighPriorityIncompleteTasks();
    
    // Find tasks by title or description containing search term, case insensitive
    @Query(value = "SELECT * FROM task WHERE title = '" + 
                   "#{#title}" + "' AND completed = false", nativeQuery = true)
    List<Task> findByTitleCaseInsensitive(@Param("title") String title);
}
