package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.MachineLocationDTO;
import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.MachineInspection;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.repository.InspectionRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;

@Service
public class MachineInspectionServiceImpl implements MachineInspectionService{

	@Autowired
    private InspectionRepository inspectionRepo;

	@Autowired
    private MachineRepository machineRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private InstallationRecordRepository installationRecordRepo;
    
    
    @Autowired
    public MachineInspectionServiceImpl(
        MachineRepository machineRepo,
        InstallationRecordRepository installRepo
    ) {
        this.machineRepo = machineRepo;
        this.installationRecordRepo = installRepo;
    }
    

    
    @Override
    public List<MaintenanceFormDTO> getMaintenanceHistory(Long machineId) {
        return inspectionRepo.findByMachineIdOrderByMaintenanceDateDesc(machineId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

//    @Override
//    public List<MaintenanceFormDTO> getMaintenancesDueInNextDays(int days) {
//    	 LocalDateTime now = LocalDateTime.now();
//    	    LocalDateTime target = now.plusDays(days);
//    	    System.out.println("Checking maintenance from " + now + " to " + target);
//
//    	    List<MachineInspection> results = inspectionRepo.findUpcomingMaintenances(now, target);
//    	    System.out.println("Found: " + results.size() + " machines");
//
//    	    return results.stream()
//    	        .map(this::convertToDTO)
//    	        .collect(Collectors.toList());
//    }
    
    @Override
    public List<MaintenanceFormDTO> getMaintenancesDueInNextDays(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.plusDays(days);

        List<InstallationRecord> installations = installationRecordRepo.findAllWithInstallationEnded();
        List<MachineInspection> inspections = inspectionRepo.findAllInspections();

        Map<Long, MachineInspection> latestInspectionMap = inspections.stream()
            .filter(i -> i.getMaintenanceDate() != null)
            .collect(Collectors.groupingBy(
                mi -> mi.getMachine().getId(),
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(MachineInspection::getMaintenanceDate)),
                    Optional::get
                )
            ));

        List<MaintenanceFormDTO> upcomingOrOverdue = new ArrayList<>();

        for (InstallationRecord ir : installations) {
            Long machineId = ir.getMachine().getId();
            LocalDateTime dueDate = null;

            MachineInspection latestInspection = latestInspectionMap.get(machineId);

            if (latestInspection != null && latestInspection.getMaintenanceDate() != null) {
                dueDate = latestInspection.getMaintenanceDate().plusDays(29);
            } else if (ir.getInstallationEnded() != null) {
                dueDate = ir.getInstallationEnded().plusDays(29);
            } else {
                System.out.println("Skipping machine ID " + machineId + ": Missing maintenance & installation date");
                continue;
            }

            if (dueDate.isBefore(now) || (!dueDate.isBefore(now) && !dueDate.isAfter(target))) {
                MaintenanceFormDTO dto = new MaintenanceFormDTO();
                dto.setMachineId(machineId);

                if (latestInspection != null && latestInspection.getModelNo() != null) {
                    dto.setModelNo(latestInspection.getModelNo());
                } else {
                    dto.setModelNo(ir.getMachine().getModelNo());
                }

                dto.setDueDate(dueDate);
                dto.setStatus(dueDate.isBefore(now) ? "Overdue" : "Due soon");

                upcomingOrOverdue.add(dto);
            }
        }

        return upcomingOrOverdue;
    }



    @Override
    public MaintenanceFormDTO createInspection(MaintenanceFormDTO dto) {
        MachineInspection inspection = new MachineInspection();

        inspection.setModelNo(dto.getModelNo());
        inspection.setGreaseLevel(dto.getGreaseLevel());
        inspection.setBatteryReading(dto.getBatteryReading());
        inspection.setSolarPanelReading1(dto.getSolarPanelReading1()); // ✅
        inspection.setSolarPanelReading2(dto.getSolarPanelReading2()); // ✅
        inspection.setRemark(dto.getRemark());                         // ✅
        inspection.setMaintenanceDate(dto.getMaintenanceDate());

        inspection.setMachine(machineRepo.findById(dto.getMachineId())
                .orElseThrow(() -> new RuntimeException("Machine not found")));

        inspection.setInspectedBy(userRepo.findById(dto.getInspectedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        MachineInspection saved = inspectionRepo.save(inspection);
        return convertToDTO(saved);
    }

    
    private MaintenanceFormDTO convertToDTO(MachineInspection ins) {
        MaintenanceFormDTO dto = new MaintenanceFormDTO();
        dto.setId(ins.getId());
        dto.setMachineId(ins.getMachine().getId());
        dto.setModelNo(ins.getModelNo());
        dto.setGreaseLevel(ins.getGreaseLevel());
        dto.setBatteryReading(ins.getBatteryReading());
        dto.setSolarPanelReading1(ins.getSolarPanelReading1()); // ✅
        dto.setSolarPanelReading2(ins.getSolarPanelReading2()); // ✅
        dto.setRemark(ins.getRemark());                         // ✅
        dto.setMaintenanceDate(ins.getMaintenanceDate());
        dto.setMaintenanceStarted(ins.getMaintenanceStarted());
        dto.setMaintenanceEnded(ins.getMaintenanceEnded());

        dto.setInspectedByUserId(ins.getInspectedBy().getId());
        dto.setInspectedByName(ins.getInspectedBy().getName());

        dto.setDivision(ins.getMachine().getDivision());
        dto.setSection(ins.getMachine().getSection());

        if (ins.getMaintenanceStarted() != null && ins.getMaintenanceEnded() != null) {
            dto.setStatus("Completed");
        } else if (ins.getMaintenanceStarted() != null) {
            dto.setStatus("In Process");
        } else {
            dto.setStatus("Not Started");
        }

        return dto;
    }

    
    @Override
    public MachineLocationDTO getCombinedInfo(String modelNo) {
        Machine machine = machineRepo.findByModelNo(modelNo)
            .orElseThrow(() -> new RuntimeException("Machine not found"));

        InstallationRecord record = installationRecordRepo.findTopByModelNoOrderByInstallationStartedDesc(modelNo)
            .orElseThrow(() -> new RuntimeException("Installation record not found"));

        return new MachineLocationDTO(
            machine.getSection(),
            machine.getDivision(),
            record.getPoleNo(),
            record.getFromKm(),
            record.getToKm()
        );
    }



	@Override
	public MachineLocationDTO getMachineLocationByModelNo(String modelNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public String startMaintenance(String modelNo, Long technicianId) {
		 Machine machine = machineRepo.findByModelNo(modelNo)
			        .orElseThrow(() -> new RuntimeException("Machine not found"));

			    // ✅ Fetch the technician as a User entity
			    Users technician = userRepo.findById(technicianId)
			        .orElseThrow(() -> new RuntimeException("Technician not found"));

			    MachineInspection inspection = new MachineInspection();
			    inspection.setModelNo(modelNo);
			    inspection.setMaintenanceStarted(LocalDateTime.now());
			    inspection.setMachine(machine);
			    inspection.setMaintenanceTechnicianId(technicianId);  // You may keep this
			    inspection.setInspectedBy(technician);               // ✅ This fixes the null issue

			    inspectionRepo.save(inspection);
			    return "Maintenance started successfully.";
			}
	
	@Override
	public List<MaintenanceFormDTO> getFilteredMaintenanceRecords(String modelNo, String fromDateStr, String toDateStr) {
	    List<MachineInspection> inspections = inspectionRepo.findAllInspections();

	    return inspections.stream()
	        .filter(ins -> {
	            boolean matches = true;

	            if (modelNo != null && !modelNo.trim().isEmpty()) {
	                matches = ins.getModelNo() != null && ins.getModelNo().equalsIgnoreCase(modelNo.trim());
	            }

	            if (matches && fromDateStr != null && !fromDateStr.isEmpty()) {
	                LocalDateTime fromDate = LocalDateTime.parse(fromDateStr + "T00:00:00");
	                matches = ins.getMaintenanceDate() != null && !ins.getMaintenanceDate().isBefore(fromDate);
	            }

	            if (matches && toDateStr != null && !toDateStr.isEmpty()) {
	                LocalDateTime toDate = LocalDateTime.parse(toDateStr + "T23:59:59");
	                matches = ins.getMaintenanceDate() != null && !ins.getMaintenanceDate().isAfter(toDate);
	            }

	            return matches;
	        })
	        .map(this::convertToDTO)
	        .collect(Collectors.toList());
	}

	@Override
	public List<Map<String, Object>> getMonthlyVisitCounts(String modelNo) {
	    List<MachineInspection> inspections = inspectionRepo.findAllInspections();

	    return inspections.stream()
	        .filter(mi -> modelNo == null || modelNo.trim().isEmpty() || modelNo.equalsIgnoreCase(mi.getModelNo()))
	        .filter(mi -> mi.getMaintenanceDate() != null)
	        .collect(Collectors.groupingBy(
	            mi -> mi.getMaintenanceDate().getYear() + "-" + String.format("%02d", mi.getMaintenanceDate().getMonthValue()),
	            Collectors.counting()
	        ))
	        .entrySet()
	        .stream()
	        .map(entry -> {
	            Map<String, Object> map = new java.util.HashMap<>();
	            map.put("month", entry.getKey());
	            map.put("modelNo", modelNo);
	            map.put("totalVisits", entry.getValue());
	            return map;
	        })
	        .collect(Collectors.toList());
	}

}
