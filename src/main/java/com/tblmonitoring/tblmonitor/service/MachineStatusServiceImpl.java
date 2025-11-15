package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.MachineStatusDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.repository.CustomerComplaintRepository;
import com.tblmonitoring.tblmonitor.repository.InspectionRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.VandalismReportRepository;

@Service
public class MachineStatusServiceImpl implements MachineStatusService {

    @Autowired
    private CustomerComplaintRepository complaintRepo;

    @Autowired
    private VandalismReportRepository vandalismRepo;

    @Autowired
    private InspectionRepository inspectionRepo;
    
    @Autowired
    private MachineRepository machineRepo;

    @Override
    public MachineStatusDTO getMachineStatus(String modelNo) {
        LocalDateTime latestComplaintDate = complaintRepo.findLatestComplaintDateByModelNo(modelNo);

        Date vandalismDate = vandalismRepo.findLatestVandalismDateByModelNo(modelNo);
        LocalDateTime latestVandalismDate = vandalismDate != null
                ? vandalismDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;

        LocalDateTime latestMaintenanceDate = inspectionRepo.findLatestMaintenanceDateByModelNo(modelNo);

        boolean hasComplaint = latestComplaintDate != null;
        boolean hasVandalism = latestVandalismDate != null;

        String status = "Active";

        if (!hasComplaint && !hasVandalism) {
            status = "Active";
        } else if (latestMaintenanceDate == null) {
            status = "Under Maintenance";
        } else {
            boolean maintenanceBeforeComplaint = hasComplaint && latestMaintenanceDate.isBefore(latestComplaintDate);
            boolean maintenanceBeforeVandalism = hasVandalism && latestMaintenanceDate.isBefore(latestVandalismDate);

            if (maintenanceBeforeComplaint || maintenanceBeforeVandalism) {
                status = "Under Maintenance";
            }
        }

        Machine machine = machineRepo.findByModelNo(modelNo).orElse(null);
        String division = machine != null ? machine.getDivision() : null;
        String section = machine != null ? machine.getSection() : null;

        return new MachineStatusDTO(
            modelNo,
            status,
            latestMaintenanceDate,
            latestComplaintDate,
            latestVandalismDate,
            division,
            section
        );
    } 

    
    @Override
    public long countMachinesUnderMaintenance() {
        List<Machine> allMachines = machineRepo.findAll(); // Fetch all machines

        return allMachines.stream()
            .map(Machine::getModelNo)
            .map(this::getMachineStatus) // This is safe now
            .filter(status -> status != null && "Under Maintenance".equalsIgnoreCase(status.getStatus()))
            .count();
    }


	@Override
	public MachineStatusDTO getMachineStatusByModelNo(String modelNo) {
		// TODO Auto-generated method stub
		return getMachineStatus(modelNo);
	}
	
	@Override
    public List<MachineStatusDTO> getAllUnderMaintenanceMachines() {
        return machineRepo.findAll().stream()
            .map(Machine::getModelNo)
            .map(this::getMachineStatus)
            .filter(dto -> "Under Maintenance".equalsIgnoreCase(dto.getStatus()))
            .collect(Collectors.toList());
    }

	
	@Override
	public void resolveMachineStatus(String modelNo) {
        Machine machine = machineRepo.findByModelNo(modelNo).orElse(null);
        if (machine != null) {
            // Update the machine status to ACTIVE once it has been resolved
            machine.setStatus("ACTIVE");
            machineRepo.save(machine);
        }
    }

    // Additional helper to update the status of a machine when vandalism or complaint is reported
	
	@Override
    public void updateMachineStatusToUnderMaintenance(String modelNo) {
        Machine machine = machineRepo.findByModelNo(modelNo).orElse(null);
        if (machine != null) {
            // Set machine to under maintenance if a vandalism or complaint is reported
            machine.setStatus("UNDER_MAINTENANCE");
            machineRepo.save(machine);
        }
    }
	
	
	@Override
	public List<MachineStatusDTO> getAllActiveMachines() {
	    return machineRepo.findAll().stream()
	        .map(Machine::getModelNo)
	        .map(this::getMachineStatus)
	        .filter(dto -> !"Under Maintenance".equalsIgnoreCase(dto.getStatus()))
	        .collect(Collectors.toList());
	}
	
	
	@Override
	public List<MachineStatusDTO> getActiveMachines() {
	    return machineRepo.findAll().stream()
	        .map(Machine::getModelNo)
	        .map(this::getMachineStatus)
	        .filter(dto -> "Active".equalsIgnoreCase(dto.getStatus())) // Filter only active machines
	        .collect(Collectors.toList());
	}

    
}
