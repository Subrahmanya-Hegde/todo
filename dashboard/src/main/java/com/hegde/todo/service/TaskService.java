package com.hegde.todo.service;

import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.response.TaskCreationResponse;

public interface TaskService {

    TaskCreationResponse createTask(TaskCreateRequest taskCreateRequest);

}
