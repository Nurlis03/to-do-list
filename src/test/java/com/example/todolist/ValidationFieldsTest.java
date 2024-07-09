package com.example.todolist;

import com.example.todolist.controller.TaskController;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.entity.ExecutionStatus;
import com.example.todolist.entity.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.service.TaskService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ValidationFieldsTest {
    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTaskControllerInjected() {
        assertThat(taskController).isNotNull();
    }

    @Test
    public void testGetRequestToTasks_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRequestToTaskById_thenCorrectResponse() throws Exception {
        Task task = Task.builder()
                        .id(2)
                        .description("Task 101")
                        .executionStatus(ExecutionStatus.COMPLETED)
                        .build();

        ResponseEntity<Task> responseEntity = ResponseEntity.ok().body(task);
        when(taskRepository.findById(2L));

        String taskId = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRequestToTaskById_whenTaskNotFound_thenCorrectResponse() throws Exception {
        String taskId = "21923432";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    public void testPostRequestToTasksAndValidTask_thenCorrectResponse() throws Exception {
        String task = "{\"description\": \"Task 10\", \"executionStatus\" : \"COMPLETED\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                        .content(task)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPostRequestToTasksAndInvalidTask_thenCorrectResponse() throws Exception {
        String task = "{\"description\": \"\", \"executionStatus\" : \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                        .content(task)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("description is mandatory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executionStatus", Is.is("executionStatus is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPutRequestToTasksAndValidTask_thenCorrectResponse() throws Exception {
        Task task = new Task(2, "I must go to shop", ExecutionStatus.COMPLETED);
        String updatedTaskBody = "{\"description\": \"I must go to shop\", \"executionStatus\" : \"COMPLETED\"}";
        String taskId = "2";

        ResponseEntity<Task> responseEntity = ResponseEntity.ok().body(task);
        when(taskRepository.save(task));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks/" + taskId)
                        .content(updatedTaskBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("I must go to shop")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executionStatus", Is.is("COMPLETED")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPutRequestToTasksAndInvalidTask_thenCorrectResponse() throws Exception {
        String task = "{\"description\": \"\", \"executionStatus\" : \"\"}";
        String taskId = "2";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks/" + taskId)
                        .content(task)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("description is mandatory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executionStatus", Is.is("executionStatus is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
