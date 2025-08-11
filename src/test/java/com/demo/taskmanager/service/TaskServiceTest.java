package com.demo.taskmanager.service;

import com.demo.taskmanager.model.Priority;
import com.demo.taskmanager.model.Task;
import com.demo.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Test Task", "Test Description");
        sampleTask.setId(1L);
        sampleTask.setPriority(Priority.HIGH);
        sampleTask.setCategory("Work");
    }

    @Test
    void createTask_ValidTask_ShouldReturnSavedTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // Act
        Task result = taskService.createTask(sampleTask);

        // Assert
        assertNotNull(result);
        assertEquals(sampleTask.getTitle(), result.getTitle());
        verify(taskRepository).save(sampleTask);
    }

    @Test
    void createTask_EmptyTitle_ShouldThrowException() {
        // Arrange
        Task invalidTask = new Task("", "Description");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        });
    }

    @Test
    void createTask_NullTitle_ShouldThrowException() {
        // Arrange
        Task invalidTask = new Task(null, "Description");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        });
    }

    @Test
    void createTask_DueDateInPast_ShouldThrowException() {
        // Arrange
        Task invalidTask = new Task("Valid Title", "Description");
        invalidTask.setDueDate(LocalDateTime.now().minusDays(1));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        });
    }

    @Test
    void getTask_ExistingId_ShouldReturnTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // Act
        Optional<Task> result = taskService.getTask(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(sampleTask.getId(), result.get().getId());
    }

    @Test
    void getTask_NonExistingId_ShouldReturnEmpty() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = taskService.getTask(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        // Arrange
        List<Task> tasks = Arrays.asList(sampleTask, new Task("Task 2", "Description 2"));
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }

    @Test
    void markTaskAsCompleted_ExistingTask_ShouldMarkAsCompleted() {
        // Arrange
        Task uncompletedTask = new Task("Test Task", "Description");
        uncompletedTask.setId(1L);
        uncompletedTask.setCompleted(false);
        
        Task completedTask = new Task("Test Task", "Description");
        completedTask.setId(1L);
        completedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(uncompletedTask));
        when(taskRepository.save(any(Task.class))).thenReturn(completedTask);

        // Act
        Task result = taskService.markTaskAsCompleted(1L);

        // Assert
        assertTrue(result.getCompleted());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void markTaskAsCompleted_NonExistingTask_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            taskService.markTaskAsCompleted(999L);
        });
    }

    @Test
    void deleteTask_ExistingTask_ShouldDeleteTask() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_NonExistingTask_ShouldThrowException() {
        // Arrange
        when(taskRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            taskService.deleteTask(999L);
        });
    }

    @Test
    void getTasksByPriority_ShouldReturnFilteredTasks() {
        // Arrange
        List<Task> highPriorityTasks = Arrays.asList(sampleTask);
        when(taskRepository.findByPriority(Priority.HIGH)).thenReturn(highPriorityTasks);

        // Act
        List<Task> result = taskService.getTasksByPriority(Priority.HIGH);

        // Assert
        assertEquals(1, result.size());
        assertEquals(Priority.HIGH, result.get(0).getPriority());
    }

    @Test
    void getTasksByCategory_ShouldReturnFilteredTasks() {
        // Arrange
        List<Task> workTasks = Arrays.asList(sampleTask);
        when(taskRepository.findByCategoryIgnoreCase("Work")).thenReturn(workTasks);

        // Act
        List<Task> result = taskService.getTasksByCategory("Work");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Work", result.get(0).getCategory());
    }

    @Test
    void searchTasks_EmptyQuery_ShouldReturnAllTasks() {
        // Arrange
        List<Task> allTasks = Arrays.asList(sampleTask);
        when(taskRepository.findAll()).thenReturn(allTasks);

        // Act
        List<Task> result = taskService.searchTasks("");

        // Assert
        assertEquals(1, result.size());
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskStatistics_ShouldReturnCorrectStatistics() {
        // Arrange
        when(taskRepository.count()).thenReturn(10L);
        when(taskRepository.countByCompleted(true)).thenReturn(6L);
        when(taskRepository.countByCompleted(false)).thenReturn(4L);
        when(taskRepository.findOverdueTasks()).thenReturn(Arrays.asList(sampleTask));

        // Act
        TaskService.TaskStatistics stats = taskService.getTaskStatistics();

        // Assert
        assertEquals(10L, stats.getTotalTasks());
        assertEquals(6L, stats.getCompletedTasks());
        assertEquals(4L, stats.getPendingTasks());
        assertEquals(1L, stats.getOverdueTasks());
        assertEquals(60.0, stats.getCompletionRate());
    }
}
