package com.tblmonitoring.tblmonitor.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.DispatchFilterDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationFilterDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationReportDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.service.InstallationService;
import com.tblmonitoring.tblmonitor.service.MachineService;
import com.tblmonitoring.tblmonitor.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
//@CrossOrigin(origins = "*")
public class ReportController {

	 @Autowired
	 private ReportService reportService;
	 
	 @Autowired
	 private MachineRepository machineRepository;
	 
	 @Autowired
	 private MachineService machineService;
	 
	 @Autowired
	 private InstallationService installationService;

//	    @GetMapping("/dispatch")
//	    public List<DispatchReportDTO> getDispatchReport(@ModelAttribute DispatchFilterDTO filter) {
//	        return reportService.getDispatchReport(filter);
//	    }

	 @PostMapping("/dispatch")
	 public ResponseEntity<List<Machine>> getDispatchReport(@RequestBody DispatchFilterDTO filter) {
	     LocalDateTime startOfDay = null;
	     LocalDateTime endOfDay = null;

	     if (filter.getDispatchDate() != null) {
	         startOfDay = filter.getDispatchDate().atStartOfDay();
	         endOfDay = startOfDay.plusDays(1);
	     }

	     List<Machine> filteredMachines = machineRepository.findMachinesByFilters(
	         filter.getPoNumber(),
	         filter.getDivision(),
	         filter.getSection(),
	         startOfDay,
	         endOfDay
	     );

	     return ResponseEntity.ok(filteredMachines);
	 }
	 
	    @PostMapping("/dispatch-reports")
	    public ResponseEntity<List<DispatchReportDTO>> getDispatchReports(@RequestBody DispatchFilterDTO filter) {
	        List<DispatchReportDTO> reports = machineService.getDispatchReportsByFilters(filter);
	        return ResponseEntity.ok(reports);
	    }
	    
	    @GetMapping("/export-dispatch-report")
	    public void exportDispatchReport(@RequestParam("exportAll") boolean exportAll, HttpServletResponse response) throws IOException  {
	        reportService.exportDispatchReport(response, exportAll);
	    }
	    
	    
//	    @GetMapping("/installation-report/export")
//	    public void exportInstallationReport(HttpServletResponse response) throws IOException {
//	        installationService.exportInstallationReport(response);
//	    }

	    @GetMapping("/list")
	    public List<InstallationReportDTO> getInstallationReport() {
	        return installationService.getInstallationReport();
	    }

	    @GetMapping("/export")
	    public void exportInstallationReport(HttpServletResponse response) throws IOException {
	        installationService.exportInstallationReport(response);
	    }
	    
	    @PostMapping("/list")
	    public List<InstallationReportDTO> getFilteredInstallationReport(@RequestBody InstallationFilterDTO filter) {
	        return installationService.getInstallationReportFiltered(filter);
	    }

//	    @GetMapping("/maintenance")
//	    public List<MaintenanceDTO> getMaintenanceReport(@ModelAttribute MaintenanceFilterDTO filter) {
//	        return reportService.getMaintenanceReport(filter);
//	    }
//
//	    @GetMapping("/customer-complaints")
//	    public List<ComplaintDTO> getCustomerComplaintsReport() {
//	        return reportService.getCustomerComplaints();
//	    }
//
//	    @GetMapping("/active-machines")
//	    public List<MachineDTO> getActiveMachines() {
//	        return reportService.getActiveMachines();
//	    }
//
//	    @GetMapping("/warranty")
//	    public List<WarrantyDTO> getMachineWarrantyReport() {
//	        return reportService.getWarrantyReport();
//	    }
//
//	    @GetMapping("/master")
//	    public List<MasterReportDTO> getMasterReport() {
//	        return reportService.getMasterReport();
//	    }
}
