package com.tblmonitoring.tblmonitor.service;

import com.tblmonitoring.tblmonitor.dto.AdminDashboardDTO;
import com.tblmonitoring.tblmonitor.dto.SuperAdminDashboardDTO;


public interface DashboardService {

	SuperAdminDashboardDTO getSuperAdminDashboardData();
	 AdminDashboardDTO getAdminDashboardData(Long adminId);
	 
}
