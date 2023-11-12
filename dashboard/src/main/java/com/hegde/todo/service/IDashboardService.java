package com.hegde.todo.service;

import com.hegde.todo.dto.request.CreateDashboardRequest;
import com.hegde.todo.dto.response.CreateDashboardResponse;

    public interface IDashboardService {
    CreateDashboardResponse createDashboard(CreateDashboardRequest request);
}
