package com.hegde.todo.controller;

import com.hegde.todo.dto.PageResponse;
import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.request.UpdateTaskRequest;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;
import com.hegde.todo.dto.response.ViewTaskResponse;
import com.hegde.todo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("v0/tasks")
public class TaskController {

    private static String TASK_API_PATH = "v0/tasks/";
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

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<ListTasksResponse>> getTasks(@PathVariable("userId")UUID userId,
                                                                    @DefaultValue("1")@RequestParam(value = "pageNo", required = false) int pageNo,
                                                                    @DefaultValue("10") @RequestParam(value = "pageSize", required = false) int pageSize) {
        return ResponseEntity.ok(taskService.listTasks(userId, pageNo, pageSize));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ViewTaskResponse> viewTicket(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.viewTicket(taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ViewTaskResponse> updateTicket(@PathVariable("taskId") Long taskId,
                                                         @RequestBody UpdateTaskRequest updateTaskRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskId, updateTaskRequest));
    }


    /**
     * Lists down valid task status.
     *
     * @return
     */
    @GetMapping("/status")
    public ResponseEntity<List<String>> listTaskStatuses() {
        return ResponseEntity.ok(taskService.listTaskStatusFlow());
    }
}
