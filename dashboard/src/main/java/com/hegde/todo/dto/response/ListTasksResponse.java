package com.hegde.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hegde.todo.dto.TaskStatus;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ListTasksResponse(Long id, String title, String description, TaskStatus status) {
}
