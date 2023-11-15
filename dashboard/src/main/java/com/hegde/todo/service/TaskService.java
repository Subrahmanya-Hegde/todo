package com.hegde.todo.service;

import com.hegde.todo.domain.UserPrincipal;
import com.hegde.todo.dto.PageResponse;
import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.request.UpdateTaskRequest;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;
import com.hegde.todo.dto.response.ViewTaskResponse;

import java.util.List;

public interface TaskService {

    TaskCreationResponse createTask(TaskCreateRequest taskCreateRequest);
    PageResponse<ListTasksResponse> listTasks(UserPrincipal userPrincipal, int pageNo, int pageSize);

    ViewTaskResponse viewTicket(long taskId, UserPrincipal userPrincipal);
    List<String> listTaskStatusFlow();

    ViewTaskResponse updateTask(long taskId, UpdateTaskRequest updateTaskRequest, UserPrincipal userPrincipal);
}
