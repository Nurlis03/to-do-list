package com.example.todolist.service;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.entity.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    ResponseEntity<Task> findById(long taskId);

    ResponseEntity<Task> createTask(TaskDto taskDto);

    ResponseEntity<Task> updateTask(long taskId, TaskDto taskDto);
    ResponseEntity<String> deleteById(long taskId);
}
