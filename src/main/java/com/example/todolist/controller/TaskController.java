package com.example.todolist.controller;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.entity.Task;
import com.example.todolist.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class TaskController {

    private TaskServiceImpl taskService;

    @GetMapping("/tasks")
    public List<Task> findAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable("id") long taskId) {
        return taskService.findById(taskId);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long taskId, @Valid @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long taskId) {
        return taskService.deleteById(taskId);
    }
}
