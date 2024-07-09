package com.example.todolist.service.impl;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.entity.ExecutionStatus;
import com.example.todolist.entity.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public ResponseEntity<Task> findById(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + taskId));
        return ResponseEntity.ok().body(task);
    }

    @Override
    public ResponseEntity<Task> createTask(TaskDto taskDto) {
        Task newTask = Task.builder()
                                .description(taskDto.getDescription())
                                .executionStatus(ExecutionStatus.valueOf(taskDto.getExecutionStatus()))
                                .build();
        taskRepository.save(newTask);
        return ResponseEntity.ok().body(newTask);
    }

    @Override
    public ResponseEntity<Task> updateTask(long taskId, TaskDto taskDto) {
        Task updatedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + taskId));
        updatedTask.setDescription(taskDto.getDescription());
        updatedTask.setExecutionStatus(ExecutionStatus.valueOf(taskDto.getExecutionStatus()));
        taskRepository.save(updatedTask);
        return ResponseEntity.ok().body(updatedTask);
    }

    @Override
    public ResponseEntity<String> deleteById(long taskId) {
        boolean exists = taskRepository.existsById(taskId);
        if (!exists) {
            throw new EntityNotFoundException("task with id " + taskId + " does not exists");
        }
        taskRepository.deleteById(taskId);
        return new ResponseEntity<>("Task with id " + taskId + " successfully deleted", HttpStatus.OK);
    }
}
