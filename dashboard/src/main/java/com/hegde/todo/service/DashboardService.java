package com.hegde.todo.service;

import com.hegde.todo.domain.Dashboard;
import com.hegde.todo.domain.DashboardUser;
import com.hegde.todo.domain.User;
import com.hegde.todo.dto.request.CreateDashboardRequest;
import com.hegde.todo.dto.response.CreateDashboardResponse;
import com.hegde.todo.repository.DashboardRepository;
import com.hegde.todo.repository.DashboardUserRepository;
import com.hegde.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DashboardService implements IDashboardService {

    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final DashboardUserRepository dashboardUserRepository;

    public DashboardService(DashboardRepository dashboardRepository,
                            UserRepository userRepository,
                            DashboardUserRepository dashboardUserRepository) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
        this.dashboardUserRepository = dashboardUserRepository;
    }

    @Override
    public CreateDashboardResponse createDashboard(CreateDashboardRequest request) {
        Dashboard dashboard = dashboardRepository.save(Dashboard.builder()
                .project(request.project())
                .description(request.description())
                .build());
        assignUsersToDashboard(request.userIds(), dashboard);
        log.info("Dashboard created successfully - {}", dashboard.getProject());
        return CreateDashboardResponse.builder()
                .dashboard(dashboard)
                .build();
    }

    private void assignUsersToDashboard(List<String> userIds, Dashboard dashboard) {
        Set<User> users = userRepository.findByUuidIn(userIds);
        users.forEach(user -> dashboardUserRepository.save(
                DashboardUser.builder().dashboard(dashboard).user(user).build()));
    }
}
