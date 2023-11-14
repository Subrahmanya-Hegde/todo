package com.hegde.todo.service;

import com.hegde.todo.domain.UserPrincipal;
import com.hegde.todo.dto.PageResponse;
import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;

public interface TaskService {

    TaskCreationResponse createTask(TaskCreateRequest taskCreateRequest);
    PageResponse<ListTasksResponse> listTasks(UserPrincipal userPrincipal, int pageNo, int pageSize);
}
