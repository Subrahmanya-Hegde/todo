package com.hegde.todo.controller;

import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.response.TaskCreationResponse;
import com.hegde.todo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("v0/tasks")
public class TaskController {

    private static String TASK_API_PATH = "v0/tasks";
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskCreationResponse> createTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        TaskCreationResponse taskCreationResponse = taskService.createTask(taskCreateRequest);
        return ResponseEntity.created(URI.create(TASK_API_PATH + taskCreationResponse.id()))
                .body(taskCreationResponse);
    }
}
