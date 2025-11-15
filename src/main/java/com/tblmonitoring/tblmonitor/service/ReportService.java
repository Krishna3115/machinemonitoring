package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.util.List;

import com.tblmonitoring.tblmonitor.dto.DispatchFilterDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.VandalismReportDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	List<Machine> getDispatchReport(DispatchFilterDTO filter);

	List<DispatchReportDTO> getDispatchReportsByFilters(DispatchFilterDTO filter);
	
	void exportDispatchReportWithFilters(HttpServletResponse response, DispatchFilterDTO filter, boolean exportAll) throws IOException;

	void exportDispatchReport(HttpServletResponse response, boolean exportAll) throws IOException;
	
	List<VandalismReport> saveMultipleReports(List<VandalismReportDTO> reportDTOs);

}
