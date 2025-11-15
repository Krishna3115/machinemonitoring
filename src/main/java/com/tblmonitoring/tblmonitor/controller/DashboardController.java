package com.tblmonitoring.tblmonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.tblmonitoring.tblmonitor.dto.AdminDashboardDTO;
import com.tblmonitoring.tblmonitor.service.DashboardService;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	@Autowired
    private DashboardService dashboardService;

    @GetMapping("/superadmin/dashboard")
    public ResponseEntity<?> getSuperAdminDashboard() {
        return ResponseEntity.ok(dashboardService.getSuperAdminDashboardData());
    }

    @GetMapping("/admin/dashboard/{adminId}")
    public ResponseEntity<?> getAdminDashboard(@PathVariable("adminId") Long adminId) {
        return ResponseEntity.ok(dashboardService.getAdminDashboardData(adminId));
    }
 
}
