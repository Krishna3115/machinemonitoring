package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
public class MachineInspectionServiceImpl implements MachineInspectionService {

    @Autowired
    private InspectionRepository inspectionRepo;
    @Autowired
    private MachineRepository machineRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private InstallationRecordRepository installationRepo;

    // ----------------------------------------------------------
    //  GET MAINTENANCE HISTORY
    // ----------------------------------------------------------
    @Override
    public List<MaintenanceFormDTO> getMaintenanceHistory(Long machineId) {
        return inspectionRepo.findByMachineIdOrderByMaintenanceDateDesc(machineId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------------
    //  UPCOMING MAINTENANCE
    // ----------------------------------------------------------
    @Override
    public List<MaintenanceFormDTO> getMaintenancesDueInNextDays(int days) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = now.plusDays(days);

        List<InstallationRecord> installations = installationRepo.findAllWithInstallationEnded();
        List<MachineInspection> inspections = inspectionRepo.findAllInspections();

        // latest inspection per machine
        Map<Long, MachineInspection> latestInspection =
                inspections.stream()
                        .filter(i -> i.getMaintenanceDate() != null)
                        .collect(Collectors.groupingBy(
                                i -> i.getMachine().getId(),
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparing(MachineInspection::getMaintenanceDate)),
                                        Optional::get
                                )
                        ));

        List<MaintenanceFormDTO> list = new ArrayList<>();

        for (InstallationRecord ir : installations) {

            Long machineId = ir.getMachine().getId();
            LocalDateTime dueDate;

            MachineInspection last = latestInspection.get(machineId);

            if (last != null) {
                dueDate = last.getMaintenanceDate().plusDays(29);
            } else {
                dueDate = ir.getInstallationEnded().plusDays(29);
            }

            if (dueDate.isBefore(now) || (dueDate.isAfter(now) && dueDate.isBefore(target))) {

                MaintenanceFormDTO dto = new MaintenanceFormDTO();
                dto.setMachineId(machineId);
                dto.setModelNo(ir.getMachine().getModelNo());
                dto.setDueDate(dueDate);
                dto.setStatus(dueDate.isBefore(now) ? "Overdue" : "Due Soon");

                list.add(dto);
            }
        }

        return list;
    }

    // ----------------------------------------------------------
    //  START MAINTENANCE
    // ----------------------------------------------------------
    @Override
    public MachineInspection startMaintenance(String modelNo, Long technicianId) {
        Users technician = userRepo.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        Machine machine = machineRepo.findByModelNo(modelNo)
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        Optional<MachineInspection> optionalInspection = inspectionRepo
                .findTopByModelNoAndMaintenanceTechnicianIdAndMaintenanceEndedIsNullOrderByMaintenanceStartedDesc(
                        modelNo, technicianId);

        MachineInspection inspection;
        if (optionalInspection.isPresent()) {
            inspection = optionalInspection.get();
        } else {
            inspection = new MachineInspection();
            inspection.setMachine(machine);
            inspection.setModelNo(modelNo);
            inspection.setMaintenanceTechnicianId(technicianId);
            inspection.setInspectedByUserId(technicianId);
            inspection.setMaintenanceStarted(LocalDateTime.now());
            inspection.setMaintenanceDate(LocalDateTime.now());
            inspectionRepo.save(inspection);
        }

        return inspection;
    }


    // ----------------------------------------------------------
    //  END MAINTENANCE (Just sets maintenanceEnded)
    // ----------------------------------------------------------
    @Override
    public String endMaintenance(String modelNo, Long technicianId) {

        MachineInspection inspection = inspectionRepo
                .findTopByModelNoAndMaintenanceTechnicianIdAndMaintenanceEndedIsNullOrderByMaintenanceStartedDesc(
                        modelNo, technicianId)
                .orElseThrow(() -> new RuntimeException("No active maintenance found"));

        inspection.setMaintenanceEnded(LocalDateTime.now());
        inspectionRepo.save(inspection);

        return "Maintenance ended.";
    }

    // ----------------------------------------------------------
    //  COMPLETE MAINTENANCE (Final form)
    // ----------------------------------------------------------
    @Override
    public String completeMaintenance(MaintenanceFormDTO dto) {

        MachineInspection inspection = inspectionRepo
                .findTopByModelNoAndMaintenanceTechnicianIdAndMaintenanceEndedIsNullOrderByMaintenanceStartedDesc(
                        dto.getModelNo(), dto.getInspectedByUserId())
                .orElseThrow(() -> new RuntimeException("No maintenance session found"));

        // Set all new fields
        inspection.setDateOfInspection(dto.getDateOfInspection());
        inspection.setGreaseLevel(dto.getGreaseLevel());
        inspection.setGreaseLevelPhotoUrl(dto.getGreaseLevelPhotoUrl());
        inspection.setBatteryVoltage(dto.getBatteryVoltage());
        inspection.setSolarPanelVoltage(dto.getSolarPanelVoltage());
        inspection.setWheelCount(dto.getWheelCount());
        inspection.setCycleTime(dto.getCycleTime());
        inspection.setSolarChargeController(dto.getSolarChargeController());
        inspection.setBatchCounter(dto.getBatchCounter());
        inspection.setDoorLock(dto.getDoorLock());
        inspection.setSensorCondition(dto.getSensorCondition());
        inspection.setApplicatorStatus(dto.getApplicatorStatus());
        inspection.setMotorPumpStatus(dto.getMotorPumpStatus());
        inspection.setMachineInfoPlatePhotoUrl(dto.getMachineInfoPlatePhotoUrl());
        inspection.setApplicatorPhotoUrl(dto.getApplicatorPhotoUrl());
        inspection.setRemark(dto.getRemark());
        inspection.setMachineStatus(dto.getMachineStatus());
        inspection.setMaintenanceDate(LocalDateTime.now());
        inspection.setMaintenanceEnded(LocalDateTime.now());

        Users inspector = userRepo.findById(dto.getInspectedByUserId())
                .orElseThrow(() -> new RuntimeException("Inspector not found"));
        inspection.setInspectedByUserId(inspector.getId());  // ✅ pass the ID


        inspectionRepo.save(inspection);

        return "Maintenance completed successfully.";
    }

    // ----------------------------------------------------------
    //  FETCH MACHINE LOCATION INFO
    // ----------------------------------------------------------
    @Override
    public MachineLocationDTO getCombinedInfo(String modelNo) {
        Machine machine = machineRepo.findByModelNo(modelNo)
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        InstallationRecord record = installationRepo
                .findTopByModelNoOrderByInstallationStartedDesc(modelNo)
                .orElseThrow(() -> new RuntimeException("Installation record not found"));

        return new MachineLocationDTO(
                machine.getId(),             // ✅ machineId
                machine.getModelNo(),        // ✅ modelNo
                machine.getSection(),
                machine.getDivision(),
                record.getPoleNo(),
                record.getFromKm(),
                record.getToKm()
        );
    }


    @Override
    public MachineLocationDTO getMachineLocationByModelNo(String modelNo) {
        return getCombinedInfo(modelNo);
    }

    // ----------------------------------------------------------
    //  LIST FILTER API
    // ----------------------------------------------------------
    @Override
    public List<MaintenanceFormDTO> getFilteredMaintenanceRecords(String modelNo, String fromDate, String toDate) {

        List<MachineInspection> list = inspectionRepo.findAllInspections();

        return list.stream()
                .filter(ins -> modelNo == null || modelNo.isEmpty() ||
                        modelNo.equalsIgnoreCase(ins.getModelNo()))
                .filter(ins -> {
                    if (fromDate != null && !fromDate.isEmpty()) {
                        LocalDateTime f = LocalDate.parse(fromDate).atStartOfDay();
                        return ins.getMaintenanceDate() != null && !ins.getMaintenanceDate().isBefore(f);
                    }
                    return true;
                })
                .filter(ins -> {
                    if (toDate != null && !toDate.isEmpty()) {
                        LocalDateTime t = LocalDate.parse(toDate).atTime(23, 59, 59);
                        return ins.getMaintenanceDate() != null && !ins.getMaintenanceDate().isAfter(t);
                    }
                    return true;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------------
    //  MONTHLY SUMMARY
    // ----------------------------------------------------------
    @Override
    public List<Map<String, Object>> getMonthlyVisitCounts(String modelNo) {

        return inspectionRepo.findAllInspections()
                .stream()
                .filter(mi -> mi.getMaintenanceDate() != null)
                .filter(mi -> modelNo == null || modelNo.isEmpty()
                        || modelNo.equalsIgnoreCase(mi.getModelNo()))
                .collect(Collectors.groupingBy(
                        mi -> mi.getMaintenanceDate().getYear() + "-" +
                                String.format("%02d", mi.getMaintenanceDate().getMonthValue()),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("month", e.getKey());
                    m.put("modelNo", modelNo);
                    m.put("totalVisits", e.getValue());
                    return m;
                })
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------------
    //  CONVERT ENTITY → DTO
    // ----------------------------------------------------------
    private MaintenanceFormDTO convertToDTO(MachineInspection ins) {

        MaintenanceFormDTO dto = new MaintenanceFormDTO();

        dto.setId(ins.getId());
        dto.setMachineId(ins.getMachine().getId());
        dto.setModelNo(ins.getModelNo());
        dto.setDateOfInspection(ins.getDateOfInspection());
        dto.setGreaseLevel(ins.getGreaseLevel());
        dto.setBatteryVoltage(ins.getBatteryVoltage());
        dto.setSolarPanelVoltage(ins.getSolarPanelVoltage());
        dto.setCycleTime(ins.getCycleTime());
        dto.setWheelCount(ins.getWheelCount());
        dto.setMotorPumpStatus(ins.getMotorPumpStatus());
        dto.setSolarChargeController(ins.getSolarChargeController());
        dto.setSensorCondition(ins.getSensorCondition());
        dto.setApplicatorStatus(ins.getApplicatorStatus());
        dto.setBatchCounter(ins.getBatchCounter());
        dto.setDoorLock(ins.getDoorLock());
        dto.setApplicatorPhotoUrl(ins.getApplicatorPhotoUrl());
        dto.setMachineInfoPlatePhotoUrl(ins.getMachineInfoPlatePhotoUrl());
        dto.setRemark(ins.getRemark());
        dto.setMachineStatus(ins.getMachineStatus());
        dto.setGreaseLevelPhotoUrl(ins.getGreaseLevelPhotoUrl());
        dto.setMaintenanceDate(ins.getMaintenanceDate());
        dto.setMaintenanceStarted(ins.getMaintenanceStarted());
        dto.setMaintenanceEnded(ins.getMaintenanceEnded());

        if (ins.getInspectedByUserId() != null) {
        	dto.setInspectedByUserId(ins.getInspectedByUserId()); // ✅ it's already Long

           // dto.setInspectedByName(ins.getInspectedBy().getName());
        }

        dto.setDivision(ins.getMachine().getDivision());
        dto.setSection(ins.getMachine().getSection());

        return dto;
    }

	@Override
	public MaintenanceFormDTO createInspection(MaintenanceFormDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
}
