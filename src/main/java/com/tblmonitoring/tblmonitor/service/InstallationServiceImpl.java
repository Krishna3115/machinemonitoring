package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.InstallationFilterDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationFormDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationReportDTO;
import com.tblmonitoring.tblmonitor.entity.CurveDetail;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.repository.CurveDetailRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class InstallationServiceImpl implements InstallationService {

	private final MachineRepository machineRepository;
	private final InstallationRecordRepository installationRecordRepository;
	
	@Autowired
	private CurveDetailRepository curveDetailRepo;

    //@Autowired
    public InstallationServiceImpl(
            InstallationRecordRepository installationRecordRepository,
            MachineRepository machineRepository) {
        this.installationRecordRepository = installationRecordRepository;
        this.machineRepository = machineRepository;
    }
    
    @Override
    public String startInstallation(String modelNo, Long technicianId) {
    	Machine machine = machineRepository.findByModelNo(modelNo)
    	        .orElseThrow(() -> new RuntimeException("Machine not found"));

        if (machine.getInstallationTechnicianId() != null)
            throw new RuntimeException("Installation already started");

        machine.setInstallationTechnicianId(technicianId);
        machine.setStatus("INSTALLING");
        machineRepository.save(machine);

        InstallationRecord record = new InstallationRecord();
        record.setModelNo(modelNo);
        record.setInstallationTechnicianId(technicianId);
        record.setInstallationStarted(LocalDateTime.now());
        record.setMachine(machine);

        installationRecordRepository.save(record);
        return "Installation Started";
    }

	@Override
	public String endInstallation(String modelNo, InstallationFormDTO dto) {
		InstallationRecord record = installationRecordRepository.findByModelNo(modelNo)
				.orElseThrow(() -> new RuntimeException("Installation record not found."));

		record.setInstallationEnded(LocalDateTime.now());
		record.setSection(dto.getSection());
		record.setCurveNo(dto.getCurveNo());
		record.setPoleNo(dto.getPoleNo());
		record.setFromKm(dto.getFromKm());
		record.setToKm(dto.getToKm());
		record.setRhLhRadius(dto.getRhLhRadius());
		record.setSrDen(dto.getSrDen());
		record.setLineSection(dto.getLineSection());
		record.setPwi(dto.getPwi());
		record.setMachineStatus(dto.getMachineStatus());
		record.setGreaseLevel(dto.getGreaseLevel());
		record.setGreaseLevelPhotoUrl(dto.getGreaseLevelPhotoUrl());
		record.setWheelCount(dto.getWheelCount());
		record.setTimeCount(dto.getTimeCount());
		record.setRemarks(dto.getRemarks());

		installationRecordRepository.save(record);
		
		
		return "Installation completed successfully.";
	}

	
	@Override
	public String completeInstallation(InstallationFormDTO request) {
	    List<InstallationRecord> records = installationRecordRepository.findActiveByModelNo(request.getModelNo());

	    if (records.isEmpty()) {
	        throw new RuntimeException("No active installation record found for model number: " + request.getModelNo());
	    }

//	    if (records.size() > 1) {
//	        throw new RuntimeException("Multiple active installation records found for model number: " + request.getModelNo());
//	    }
	    
	    InstallationRecord record = records.stream()
	    	    .sorted((a, b) -> b.getInstallationStarted().compareTo(a.getInstallationStarted())) // or b.getId() - a.getId()
	    	    .findFirst()
	    	    .orElseThrow(() -> new RuntimeException("Unable to determine latest active record"));

	  //  InstallationRecord record = records.get(0);

	    // Fetch the machine to get PO number
	    Machine machine = machineRepository.findByModelNo(request.getModelNo())
	        .orElseThrow(() -> new RuntimeException("Machine not found for model number: " + request.getModelNo()));

	    String poNumber = machine.getPurchaseOrder().getPoNumber();

	    // Step 1: Match Curve No.
	    CurveDetail expected = curveDetailRepo.findByPoNumberAndCurveNo(poNumber, request.getCurveNo())
	        .orElseThrow(() -> new RuntimeException("No curve detail found for given PO and curve number"));

	    // Step 2: Auto-verify other fields
	    StringBuilder mismatches = new StringBuilder();

	    if (!Objects.equals(expected.getPoleNo(), request.getPoleNo())) {
	        mismatches.append("Pole No mismatch. ");
	    }
	    
	    if (!compareDouble(String.valueOf(expected.getKmFrom()), request.getFromKm())) {
	        mismatches.append("From KM mismatch. ");
	    }

	    if (!compareDouble(String.valueOf(expected.getKmTo()), request.getToKm())) {
	        mismatches.append("To KM mismatch. ");
	    }

	    
	    if (!Objects.equals(expected.getLhRh(), request.getRhLhRadius())) {
	        mismatches.append("LH/RH mismatch. ");
	    }
	    
	    if (!Objects.equals(expected.getBlockSection(), request.getSection())) {
	        mismatches.append("Section mismatch. ");
	    }
	    
	    if (!Objects.equals(expected.getPwiSection(), request.getPwi())) {
	        mismatches.append("PWI mismatch. ");
	    }

	    if (mismatches.length() > 0) {
	        throw new RuntimeException("Data mismatch: " + mismatches.toString());
	    }

	    // ✅ If matched, continue saving installation details
	    record.setSection(request.getSection());
	    record.setCurveNo(request.getCurveNo());
	    record.setPoleNo(request.getPoleNo());
	    record.setFromKm(request.getFromKm());
	    record.setToKm(request.getToKm());
	    record.setRhLhRadius(request.getRhLhRadius());
	    record.setSrDen(request.getSrDen());
	    record.setLineSection(request.getLineSection());
	    record.setPwi(request.getPwi());
	    record.setMachineStatus(request.getMachineStatus());
	    record.setGreaseLevel(request.getGreaseLevel());
	    record.setGreaseLevelPhotoUrl(request.getGreaseLevelPhotoUrl());
	    record.setWheelCount(request.getWheelCount());
	    record.setTimeCount(request.getTimeCount());
	    record.setRemarks(request.getRemarks());
	    record.setInstallationEnded(LocalDateTime.now());
	    record.setGreaseLevelKg(request.getGreaseLevelKg());


	    installationRecordRepository.save(record);

	    machine.setStatus("COMPLETE");
	    machine.setSiteFinalInspectionPending(true);
	    
	    if (record.getInstallationEnded() != null && machine.getWarrantyMonths() != null) {
	        LocalDateTime warrantyEnd = record.getInstallationEnded().plusMonths(machine.getWarrantyMonths());
	        machine.setWarrantyEndDate(warrantyEnd);
	    }
	    
	    machineRepository.save(machine);

	    return "Installation completed and data verified successfully.";
	    
	}


	// ✅ Helper method to safely compare numeric strings
	private boolean compareDouble(String a, String b) {
	    try {
	        if (a == null || b == null) return false;
	        return Double.parseDouble(a.trim()) == Double.parseDouble(b.trim());
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	
	public List<InstallationProgressDTO> getInstallationInProgressList() {
	    return installationRecordRepository.findInstallationsInProgress();
	}

//	public List<InstallationProgressDTO> getInstallationInProgressByTechnician(Long technicianId) {
//	    List<InstallationRecord> records = installationRecordRepository.findByTechnicianIdAndNotCompleted(technicianId);
//	    return records.stream()
//	        .map(record -> new InstallationProgressDTO(record))
//	        .collect(Collectors.toList());
//	}

	@Override
	public List<InstallationProgressDTO> getInstallationInProgressByTechnician(Long technicianId) {
	    List<InstallationRecord> records = installationRecordRepository.findActiveInstallationsByTechnicianId(technicianId);

	    return records.stream().map(record -> {
	        Machine m = record.getMachine();
	        return new InstallationProgressDTO(
	            record.getModelNo(),
	            record.getInstallationStarted(),
	            m.getDivision(),
	            m.getSection()
	        );
	    }).collect(Collectors.toList());
	}
	
	
	public List<InstallationReportDTO> getInstallationReport() {
	    List<InstallationRecord> records = installationRecordRepository.findAll();

	    int[] counter = {1}; // for Sr. No.

	    return records.stream().map(record -> {
	        InstallationReportDTO dto = new InstallationReportDTO();
	        dto.setSrNo(counter[0]++);
	        dto.setModelNo(record.getModelNo());
	        dto.setInstallationStarted(record.getInstallationStarted());
	        dto.setInstallationEnded(record.getInstallationEnded());
	        dto.setSection(record.getSection());
	        dto.setPoleNo(record.getPoleNo());
	        dto.setFromKm(record.getFromKm());
	        dto.setToKm(record.getToKm());
	        dto.setWheelCount(record.getWheelCount());
	        dto.setTimeCount(record.getTimeCount());

	        // ✅ Status logic
	        if (record.getInstallationStarted() == null && record.getInstallationEnded() == null) {
	            dto.setStatus("Installation Not Started");
	        } else if (record.getInstallationStarted() != null && record.getInstallationEnded() == null) {
	            dto.setStatus("Installation In Process");
	        } else if (record.getInstallationStarted() != null && record.getInstallationEnded() != null) {
	            dto.setStatus("Installation Complete");
	        } else {
	            dto.setStatus("Unknown"); // fallback, unlikely
	        }

	        return dto;
	    }).collect(Collectors.toList());
	}

	@Override
	public void exportInstallationReport(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public List<InstallationReportDTO> getInstallationReportFiltered(InstallationFilterDTO filter) {
	    List<InstallationRecord> records = installationRecordRepository.findAll();

	    return records.stream()
	        .filter(record -> {
	            if (filter.getModelNo() != null && !filter.getModelNo().isEmpty() &&
	                !record.getModelNo().equalsIgnoreCase(filter.getModelNo())) return false;

	            if (filter.getDivision() != null && !filter.getDivision().isEmpty() &&
	                (record.getMachine() == null || !filter.getDivision().equalsIgnoreCase(record.getMachine().getDivision()))) return false;

	            if (filter.getSection() != null && !filter.getSection().isEmpty() &&
	                (record.getSection() == null || !record.getSection().equalsIgnoreCase(filter.getSection()))) return false;

	            if (filter.getFromDate() != null &&
	                (record.getInstallationStarted() == null || record.getInstallationStarted().toLocalDate().isBefore(filter.getFromDate()))) return false;

	            if (filter.getToDate() != null &&
	                (record.getInstallationEnded() == null || record.getInstallationEnded().toLocalDate().isAfter(filter.getToDate()))) return false;

	            return true;
	        })
	        .map(record -> {
	            InstallationReportDTO dto = new InstallationReportDTO();
	            dto.setSrNo(0); // optional, you can add index later
	            dto.setModelNo(record.getModelNo());
	            dto.setInstallationStarted(record.getInstallationStarted());
	            dto.setInstallationEnded(record.getInstallationEnded());
	            dto.setSection(record.getSection());
	            dto.setPoleNo(record.getPoleNo());
	            dto.setFromKm(record.getFromKm());
	            dto.setToKm(record.getToKm());
	            dto.setWheelCount(record.getWheelCount());
	            dto.setTimeCount(record.getTimeCount());

	            // Set status
	            if (record.getInstallationStarted() == null && record.getInstallationEnded() == null) {
	                dto.setStatus("Installation Not Started");
	            } else if (record.getInstallationStarted() != null && record.getInstallationEnded() == null) {
	                dto.setStatus("Installation In Process");
	            } else {
	                dto.setStatus("Installation Complete");
	            }

	            return dto;
	        })
	        .collect(Collectors.toList());
	}

}
