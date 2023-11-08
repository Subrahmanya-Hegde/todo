package com.hegde.todo.controller;

import com.hegde.todo.model.request.CreateDashboardRequest;
import com.hegde.todo.service.IDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v0/dashboard")
public class DashboardController {

    private final IDashboardService iDashboardService;

    public DashboardController(IDashboardService iDashboardService) {
        this.iDashboardService = iDashboardService;
    }

    @PostMapping
    ResponseEntity createDashboard(@RequestBody CreateDashboardRequest createDashboardRequest){
        log.info("Create request received");
        return ResponseEntity.ok(iDashboardService.createDashboard(createDashboardRequest));
    }

    @GetMapping
    ResponseEntity getDashboard(){
        log.info("Create request received");
        return ResponseEntity.ok(List.of("Test"));
    }
}
