package com.example.todolist.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_seq", allocationSize = 1)
    private long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus;
}
