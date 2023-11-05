package com.hegde.todo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hegde.todo.domain.Dashboard;
import lombok.Builder;


@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateDashboardResponse (Dashboard dashboard){
}