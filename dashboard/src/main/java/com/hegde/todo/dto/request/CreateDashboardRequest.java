package com.hegde.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateDashboardRequest(String project, String description,  @JsonProperty("user_ids")List<String> userIds) {
}
