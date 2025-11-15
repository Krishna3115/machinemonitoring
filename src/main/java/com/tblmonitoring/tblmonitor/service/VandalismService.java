package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.util.List;

import com.tblmonitoring.tblmonitor.dto.VandalismReportDTO;
import com.tblmonitoring.tblmonitor.dto.VandalismReportwithUserDTO;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;

public interface VandalismService {

	VandalismReport saveReport(VandalismReportDTO dto);
	List<VandalismReport> getAllReports();
	List<VandalismReport> saveMultipleReports(List<VandalismReportDTO> reportDTOs);
	List<VandalismReportwithUserDTO> getAllReportsWithUserNames();


	
}
