package com.example.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskDto {
    @NotBlank(message = "description is mandatory")
    private String description;

    @NotBlank(message = "executionStatus is mandatory")
    private String executionStatus;
}
