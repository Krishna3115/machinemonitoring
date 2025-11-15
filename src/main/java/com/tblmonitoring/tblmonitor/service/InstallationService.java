package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.InstallationFilterDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationFormDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationReportDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface InstallationService {

	String startInstallation(String modelNo, Long technicianId);
    String endInstallation(String modelNo, InstallationFormDTO dto);
    String completeInstallation(InstallationFormDTO request);
    List<InstallationProgressDTO> getInstallationInProgressList();
    List<InstallationProgressDTO> getInstallationInProgressByTechnician(Long technicianId);
    List<InstallationReportDTO> getInstallationReport();
	void exportInstallationReport(HttpServletResponse response);
	List<InstallationReportDTO> getInstallationReportFiltered(InstallationFilterDTO filter);
}
