package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.opencsv.CSVWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.DispatchFilterDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.VandalismReportDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.VandalismReportRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
    private MachineRepository machineRepository;
	
	@Autowired
	private VandalismReportRepository vandalismRepository;

	@Override
	public List<Machine> getDispatchReport(DispatchFilterDTO filter) {
	    LocalDateTime fromDate = null;
	    LocalDateTime toDate = null;

	    if (filter.getFromDate() != null) {
	        fromDate = filter.getFromDate().atStartOfDay();
	    }
	    if (filter.getToDate() != null) {
	        toDate = filter.getToDate().atTime(23, 59, 59); // Include the full day
	    }

	    String division = (filter.getDivision() == null || filter.getDivision().trim().isEmpty()) ? null : filter.getDivision();
	    String section = (filter.getSection() == null || filter.getSection().trim().isEmpty()) ? null : filter.getSection();

	    return machineRepository.findMachinesByFilters(
	        filter.getPoNumber(),
	        division,
	        section,
	        fromDate,
	        toDate
	    );
	}


	@Override
	public List<DispatchReportDTO> getDispatchReportsByFilters(DispatchFilterDTO filter) {
	    LocalDateTime startOfDay = null;
	    LocalDateTime endOfDay = null;

	    if (filter.getDispatchDate() != null) {
	        startOfDay = filter.getDispatchDate().atStartOfDay();
	        endOfDay = startOfDay.plusDays(1);
	    }

	    // ✅ Handle blank values
	    String division = (filter.getDivision() == null || filter.getDivision().trim().isEmpty()) ? null : filter.getDivision();
	    String section = (filter.getSection() == null || filter.getSection().trim().isEmpty()) ? null : filter.getSection();

	    return machineRepository.findDispatchReportsByFilters(
	        filter.getPoNumber(),
	        division,
	        section,
	        startOfDay,
	        endOfDay
	    );
	}
	
	public void exportDispatchReport(HttpServletResponse response, boolean exportAll) throws IOException {
        // Fetch data based on the 'exportAll' flag
        List<Machine> machines = exportAll ? machineRepository.findAll() : machineRepository.findFilteredData(null);

        // Set up the response
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=dispatch_report.csv");

        // Write data to CSV
        try (OutputStream out = response.getOutputStream();
        	     CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {

            // Write header
            writer.writeNext(new String[]{"ID", "Model No.", "Machine Name", "Final Inspection Done By", "Dispatch Date", "Delivered Location", "Delivered Date", "Motor No.", "Sensor No.", "Applicator No.", "Battery No.", "Status", "Technician Assigned", "Division", "Section", "Site Final Inspection Pending", "PO Number"});

            // Write data rows
            for (Machine machine : machines) {
                writer.writeNext(new String[]{
                    String.valueOf(machine.getId()),
                    machine.getModelNo(),
                    machine.getMachineName(),
                    machine.getFinalInspectionDoneBy(),
                    machine.getDispatchDate() != null ? machine.getDispatchDate().toLocalDate().toString() : "",
                    machine.getLocation(),
                    machine.getDeliveredDate() != null ? machine.getDeliveredDate().toLocalDate().toString() : "",
                    machine.getMotorNo(),
                    machine.getSensorNo(),
                    machine.getApplicatorNo(),
                    machine.getBatteryNo(),
                    machine.getStatus(),
                    machine.getTechnicianAssigned() != null ? machine.getTechnicianAssigned().toString() : "",
                    machine.getDivision(),
                    machine.getSection(),
                    machine.getSiteFinalInspectionPending() != null ? machine.getSiteFinalInspectionPending().toString() : "",
                    machine.getPurchaseOrder() != null ? machine.getPurchaseOrder().getPoNumber() : ""
                });
            }
        }
	}


	@Override
	public void exportDispatchReportWithFilters(HttpServletResponse response, DispatchFilterDTO filter,
			boolean exportAll) throws IOException {
		// TODO Auto-generated method stub
		
		List<Machine> machines;

	    if (exportAll) {
	        machines = machineRepository.findAll();
	    } else {
	        machines = getDispatchReport(filter);
	    }

	    response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", "attachment; filename=dispatch_report.csv");

	    try (OutputStream out = response.getOutputStream();
	         CSVWriter writer = new CSVWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

	        // CSV Header
	        writer.writeNext(new String[]{"ID", "Model No.", "Machine Name", "Final Inspection Done By", "Dispatch Date", "Delivered Location", "Delivered Date", "Motor No.", "Sensor No.", "Applicator No.", "Battery No.", "Status", "Technician Assigned", "Division", "Section", "Site Final Inspection Pending", "PO Number"});

	        // CSV Rows
	        for (Machine machine : machines) {
	            writer.writeNext(new String[]{
	                    machine.getId() != null ? String.valueOf(machine.getId()) : "",
	                    machine.getModelNo() != null ? machine.getModelNo() : "",
	                    machine.getMachineName() != null ? machine.getMachineName() : "",
	                    machine.getFinalInspectionDoneBy() != null ? machine.getFinalInspectionDoneBy() : "",
	                    machine.getDispatchDate() != null ? machine.getDispatchDate().toLocalDate().toString() : "",
	                    machine.getLocation() != null ? machine.getLocation() : "",
	                    machine.getDeliveredDate() != null ? machine.getDeliveredDate().toLocalDate().toString() : "",
	                    machine.getMotorNo() != null ? machine.getMotorNo() : "",
	                    machine.getSensorNo() != null ? machine.getSensorNo() : "",
	                    machine.getApplicatorNo() != null ? machine.getApplicatorNo() : "",
	                    machine.getBatteryNo() != null ? machine.getBatteryNo() : "",
	                    machine.getStatus() != null ? machine.getStatus() : "",
	                    machine.getTechnicianAssigned() != null ? machine.getTechnicianAssigned().toString() : "",
	                    machine.getDivision() != null ? machine.getDivision() : "",
	                    machine.getSection() != null ? machine.getSection() : "",
	                    machine.getSiteFinalInspectionPending() != null ? machine.getSiteFinalInspectionPending().toString() : "",
	                    machine.getPurchaseOrder() != null && machine.getPurchaseOrder().getPoNumber() != null ? machine.getPurchaseOrder().getPoNumber() : ""
	            });
	        }
	    }
	}
	
	@Override
	public List<VandalismReport> saveMultipleReports(List<VandalismReportDTO> reportDTOs) {
	    List<VandalismReport> savedReports = new ArrayList<>();

	    for (VandalismReportDTO dto : reportDTOs) {
	        VandalismReport report = saveReport(dto);  // Reuse existing single save method
	        savedReports.add(report);
	    }

	    return savedReports;
	}


	private VandalismReport saveReport(VandalismReportDTO dto) {
	    VandalismReport report = new VandalismReport();
	    report.setInspectionId(dto.getInspectionId());
	    report.setModelNo(dto.getModelNo());
	    report.setComponentName(dto.getComponentName());
	    report.setIssueDescription(dto.getIssueDescription());
	    report.setPhotoUrl(dto.getPhotoUrl());
	    report.setReportedByUserId(dto.getReportedByUserId());
	    report.setReportedAtDateTime(new Date());  // Use java.util.Date

	    return vandalismRepository.save(report);
	}
		
	}
