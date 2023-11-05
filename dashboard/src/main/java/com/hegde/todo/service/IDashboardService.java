package com.hegde.todo.service;

import com.hegde.todo.model.request.CreateDashboardRequest;
import com.hegde.todo.model.response.CreateDashboardResponse;

    public interface IDashboardService {
    CreateDashboardResponse createDashboard(CreateDashboardRequest request);
}
