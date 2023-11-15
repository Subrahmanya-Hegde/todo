package com.hegde.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hegde.todo.dto.ETA;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskCreateRequest(String title, String description, String assignedTo, String createdBy,
                                ETA eta) {
}
