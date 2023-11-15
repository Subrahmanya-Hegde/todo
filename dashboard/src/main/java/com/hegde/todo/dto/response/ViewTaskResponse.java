package com.hegde.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hegde.todo.dto.TaskStatus;
import com.hegde.todo.dto.UserDto;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ViewTaskResponse(String title, TaskStatus status,
                               UserDto assignedTo, UserDto createdBy,
                               String description, String estimatedCompletionTime) {
}