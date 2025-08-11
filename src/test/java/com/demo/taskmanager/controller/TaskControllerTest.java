package com.demo.taskmanager.controller;

import com.demo.taskmanager.model.Priority;
import com.demo.taskmanager.model.Task;
import com.demo.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task sampleTask;
    private List<Task> taskList;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Test Task", "Test Description");
        sampleTask.setId(1L);
        sampleTask.setPriority(Priority.HIGH);
        sampleTask.setCategory("Work");
        sampleTask.setCreatedDate(LocalDateTime.now());

        taskList = Arrays.asList(sampleTask);
    }

    @Test
    void getAllTasks_ShouldReturnTaskList() throws Exception {
        // Arrange
        when(taskService.getAllTasks()).thenReturn(taskList);

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Test Description"));
    }

    @Test
    void getTask_ExistingId_ShouldReturnTask() throws Exception {
        // Arrange
        when(taskService.getTask(1L)).thenReturn(Optional.of(sampleTask));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getTask_NonExistingId_ShouldReturn404() throws Exception {
        // Arrange
        when(taskService.getTask(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_ValidTask_ShouldReturnCreatedTask() throws Exception {
        // Arrange
        Task newTask = new Task("New Task", "New Description");
        when(taskService.createTask(any(Task.class))).thenReturn(sampleTask);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void createTask_InvalidTask_ShouldReturn400() throws Exception {
        // Arrange
        Task invalidTask = new Task("", "Description");
        when(taskService.createTask(any(Task.class))).thenThrow(new IllegalArgumentException("Invalid task"));

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markTaskAsCompleted_ExistingTask_ShouldReturnCompletedTask() throws Exception {
        // Arrange
        Task completedTask = new Task("Test Task", "Test Description");
        completedTask.setId(1L);
        completedTask.setCompleted(true);
        
        when(taskService.markTaskAsCompleted(1L)).thenReturn(completedTask);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void deleteTask_ExistingTask_ShouldReturn204() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTasksByPriority_ShouldReturnFilteredTasks() throws Exception {
        // Arrange
        when(taskService.getTasksByPriority(Priority.HIGH)).thenReturn(taskList);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/priority/HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priority").value("HIGH"));
    }

    @Test
    void getTasksByCategory_ShouldReturnFilteredTasks() throws Exception {
        // Arrange
        when(taskService.getTasksByCategory("Work")).thenReturn(taskList);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/category/Work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Work"));
    }

    @Test
    void searchTasks_ShouldReturnMatchingTasks() throws Exception {
        // Arrange
        when(taskService.searchTasks("Test")).thenReturn(taskList);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/search").param("query", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void getTaskStatistics_ShouldReturnStatistics() throws Exception {
        // Arrange
        TaskService.TaskStatistics stats = new TaskService.TaskStatistics(10, 6, 4, 1);
        when(taskService.getTaskStatistics()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTasks").value(10))
                .andExpect(jsonPath("$.completedTasks").value(6))
                .andExpect(jsonPath("$.completionRate").value(60.0));
    }
}
