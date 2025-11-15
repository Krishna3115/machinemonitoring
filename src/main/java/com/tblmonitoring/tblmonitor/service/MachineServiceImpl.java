package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.DispatchFilterDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchFormDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
import com.tblmonitoring.tblmonitor.dto.MachineDivisionSectionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineWithInstallationDTO;
import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.dto.SitePendingInspectionDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.MachineInspection;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.MachineProduction.MachineStatus;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.exception.ResourceNotFoundException;
import com.tblmonitoring.tblmonitor.repository.InspectionRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.PurchaseOrderRepository;
//import com.tblmonitoring.tblmonitor.repository.MaintenanceRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;

import jakarta.mail.MessagingException;



import jakarta.transaction.Transactional;

@Service
public class MachineServiceImpl implements MachineService{

	@Autowired
    private MachineRepository machineRepository;
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private InspectionRepository maintenanceRepository;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private InstallationRecordRepository installationRecordRepository;
    
    @Autowired
    private MachineProductionRepository machineProductionRepository;
    
    @Autowired
    private MachineProductionService machineProductionService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    
    @Autowired
    private EmailService emailService;

    //This Form Is used to Add Machine Delivery Details At the time of dispatch.
//	@Override
//    public String dispatchMachine(DispatchFormDTO dto) {
//        Machine machine = new Machine();
//        machine.setModelNo(dto.getModelNo());
//        machine.setMachineName(dto.getMachineName());
//        machine.setFinalInspectionDoneBy(dto.getFinalInspectionDoneBy());
//        machine.setLocation(dto.getLocation());
//        machine.setDispatchDate(dto.getDispatchDate());
//        machine.setDivision(dto.getDivision());
//        machine.setSection(dto.getSection());
//        machine.setMotorNo(dto.getMotorNo());
//        machine.setSensorNo(dto.getSensorNo());
//        machine.setApplicatorNo(dto.getApplicatorNo());
//        machine.setBatteryNo(dto.getBatteryNo());
//        machine.setSolarChargeControllerNo(dto.getSolarChargeControllerNo());
//        machine.setSolarPanelNo(dto.getSolarPanelNo());
//        machine.setSolarPanelNo1(dto.getSolarPanelNo1());
//        machine.setCabinetNo(dto.getCabinetNo());
//
//        machine.setStatus("PENDING");
//        machine.setTechnicianAssigned(false);
//        
//        PurchaseOrder po = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
//                .orElseThrow(() -> new RuntimeException("PO not found with ID: " + dto.getPurchaseOrderId()));
//            machine.setPurchaseOrder(po);
//            machine.setWarrantyMonths(po.getWarrantyMonths());
//
//        machineRepository.save(machine);
//        return "Machine dispatched successfully.";
//        
//    }
    
    
    @Transactional
    @Override
    public void dispatchMachines(DispatchFormDTO dto, List<MultipartFile> pdiReports) {

        PurchaseOrder po = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        List<String> serialNos = dto.getSelectedModelNos();

        if (serialNos == null || serialNos.isEmpty()) {
            throw new RuntimeException("No machines selected for dispatch.");
        }

        if (pdiReports == null || pdiReports.size() != serialNos.size()) {
            throw new RuntimeException("Mismatch between machines and uploaded PDI reports.");
        }

        for (int i = 0; i < serialNos.size(); i++) {
            String machineSerialNo = serialNos.get(i);
            MultipartFile pdiFile = pdiReports.get(i);

            List<MachineProduction> productionList = machineProductionRepository.findByMachineSerialNo(machineSerialNo);

            if (productionList == null || productionList.isEmpty()) {
                throw new RuntimeException("MachineProduction not found for serial no: " + machineSerialNo);
            }

            // Use the first record — or you could sort by created_at or status
            MachineProduction production = productionList.get(0);
            
            Machine machine = new Machine();

            machine.setModelNo(production.getMachineSerialNo());
            machine.setMachineName(inferMachineNameFromModelNo(production.getMachineSerialNo()));

            machine.setMotorNo(production.getMotorNo());
            machine.setSensorNo(production.getSensorNo());
            machine.setApplicatorNo(production.getApplicatorNo());
            machine.setBatteryNo(production.getBatteryNo());
            machine.setCabinetNo(production.getCabinetNo());
            machine.setSolarChargeControllerNo(production.getSolarChargeControllerNo());
            machine.setSolarPanelNo(production.getSolarPanelNo1());
            machine.setSolarPanelNo1(production.getSolarPanelNo2());

            machine.setDispatchDate(dto.getDispatchDate().toLocalDateTime());
            machine.setLocation(dto.getLocation());
            machine.setFinalInspectionDoneBy(dto.getFinalInspectionDoneBy());
            machine.setDivision(dto.getDivision());
            machine.setSection(dto.getSection());
            machine.setPurchaseOrder(po);
            machine.setStatus("DISPATCHED");
            machine.setWarrantyMonths(po.getWarrantyMonths());
            machine.setTechnicianAssigned(false);

            // ✅ Save the PDI file (you can replace this with actual file storage logic)
            String pdiPath = savePdiReport(pdiFile, machineSerialNo);
            machine.setPdiFileUrl(pdiPath);

            production.setStatus(MachineProduction.MachineStatus.DISPATCHED);
            machineProductionRepository.save(production);
            
            machineRepository.save(machine);
        }
    }

    private String inferMachineNameFromModelNo(String serialNo) {
        if (serialNo.contains("HYD")) return "TBL HYDRAULIC";
        if (serialNo.contains("ELE")) return "TBL ELECTRONIC";
        return "Unknown";
    }

	public List<Machine> getMachinesInstalling() {
	    return machineRepository.findByStatus("INSTALLING");
	}

	private String savePdiReport(MultipartFile file, String serialNo) {
	    try {
	        String fileName = serialNo + "_PDI_" + file.getOriginalFilename();
	        Path path = Paths.get("uploads/pdi/" + fileName);
	        Files.createDirectories(path.getParent());
	        Files.write(path, file.getBytes());
	        return path.toString();
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to save PDI report", e);
	    }
	}
	
	//This API is used to Update Product Delivery Date
	@Override
	public String updateDeliveryDate(Long machineId, String deliveredDateStr, MultipartFile receivingLetter) {
	    Optional<Machine> optionalMachine = machineRepository.findById(machineId);

	    if (optionalMachine.isEmpty()) {
	        throw new RuntimeException("Machine not found with ID: " + machineId);
	    }

	    Machine machine = optionalMachine.get();

	    if (deliveredDateStr != null && !deliveredDateStr.isEmpty()) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	            LocalDateTime deliveredDate = LocalDateTime.parse(deliveredDateStr, formatter);
	            machine.setDeliveredDate(deliveredDate);
	        } catch (Exception e) {
	            throw new RuntimeException("Invalid delivered date format.");
	        }
	    }

	    if (receivingLetter != null && !receivingLetter.isEmpty()) {
	        String letterUrl = fileStorageService.storeFile(receivingLetter);
	        machine.setReceivingLetterUrl(letterUrl);
	    }

	    machine.setStatus("DELIVERED");

	    machineRepository.save(machine);

	    // Fix: define letterUploaded variable here
	    boolean letterUploaded = (receivingLetter != null && !receivingLetter.isEmpty());

	    try {
	        String subject;
	        String body;

	        if (deliveredDateStr != null && !letterUploaded) {
	            subject = "🚚 Delivery Date Updated - Receiving Letter Pending";
	            body = "Delivery date has been updated for Machine ID: " + machineId +
	                   "\nModel No: " + machine.getModelNo() +
	                   "\nBut the receiving letter is still pending.";
	            emailService.sendDeliveryStatusEmail(subject, body, null);

	        } else if (deliveredDateStr != null && letterUploaded) {
	            subject = "✅ Delivery Completed - Receiving Letter Submitted";
	            body = "Delivery has been completed for Machine ID: " + machineId +
	                   "\nModel No: " + machine.getModelNo() +
	                   "\nReceiving letter is attached.";

	            emailService.sendDeliveryStatusEmail(subject, body, receivingLetter);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Delivery updated, but failed to send email: " + e.getMessage());
	    }

	    return "Delivery info updated and email sent successfully.";
	}


	@Override
	public List<Machine> getAllMachines() {
	    return machineRepository.findAll();
	}

	
	//This API is used to Machine Maintenance form 
	@Override
    public String submitMaintenanceForm(MaintenanceFormDTO form) {
		Machine machine = machineRepository.findByModelNo(form.getModelNo())
			    .orElseThrow(() -> new RuntimeException("Machine not found with modelNo: " + form.getModelNo()));


        Users technician = userRepository.findById(form.getTechnicianUserId())
            .orElseThrow(() -> new RuntimeException("Technician not found with ID: " + form.getTechnicianUserId()));

        if (!"USER".equalsIgnoreCase(technician.getRole())) {
            throw new RuntimeException("Provided user is not a technician.");
        }

        MachineInspection record = new MachineInspection();
        record.setMachine(machine);
        record.setModelNo(form.getModelNo());
        record.setGreaseLevelPhotoUrl(form.getGreaseLevelPhotoUrl());
        record.setGreaseLevel(form.getGreaseLevel());
        record.setBatteryReading(form.getBatteryReading());
        record.setSolarPanelReading(form.getSolarPanelReading());
        record.setTimeCount(form.getTimeCount());
        record.setWheelCount(form.getWheelCount());
        record.setMachineInfoPlatePhotoUrl(form.getMachineInfoPlatePhotoUrl());
        record.setSensor(form.getSensor());
        record.setApplicator(form.getApplicator());
        record.setMachineStatus(form.getMachineStatus());
        record.setMaintenanceDate(LocalDateTime.now());
        record.setInspectedBy(technician);

        maintenanceRepository.save(record);

        machine.setStatus("Under Maintenance");
        machineRepository.save(machine);

        return "Maintenance record saved successfully.";
    }

	@Override
	public Machine getMachineByModelNo(String modelNo) {
		// TODO Auto-generated method stub
		return machineRepository.findByModelNo(modelNo)
		        .orElseThrow(() -> new RuntimeException("Machine not found with modelNo: " + modelNo));
	}

	@Override
    public List<Machine> getMachinesByStatus(String status) {
        return machineRepository.findByStatus(status.toUpperCase());
    }

	@Override
	public List<Machine> getPendingSiteInspections() {
	    return machineRepository.findPendingSiteInspections();
	}

	@Override
	public String completeSiteInspection(Long machineId, String inspectionDateStr) {
	    Optional<Machine> optionalMachine = machineRepository.findById(machineId);

	    if (optionalMachine.isEmpty()) {
	        return "Machine not found";
	    }

	    Machine machine = optionalMachine.get();

	    try {
	        LocalDateTime inspectionDate = LocalDateTime.parse(inspectionDateStr);
	        machine.setSiteFinalInspectionPending(false);
	        machine.setSiteFinalInspectionDate(inspectionDate);
	        

	        

	        machineRepository.save(machine);
	        return "Site final inspection marked as complete.";
	    } catch (Exception e) {
	        return "Invalid date format. Please use ISO format (e.g., 2025-06-02T15:00)";
	    }
	}
	
	
	@Override
	public String updateSiteInspection(Long machineId, String action, String inspectionDateStr, String remark) {
	    Optional<Machine> optionalMachine = machineRepository.findById(machineId);

	    if (optionalMachine.isEmpty()) {
	        return "Machine not found";
	    }

	    Machine machine = optionalMachine.get();

	    try {
	        if ("markDone".equalsIgnoreCase(action)) {
	            LocalDateTime inspectionDate = LocalDateTime.parse(inspectionDateStr);
	            machine.setSiteFinalInspectionPending(false);
	            machine.setSiteFinalInspectionDate(inspectionDate);

	            if (machine.getWarrantyMonths() != null) {
	                LocalDateTime warrantyEnd = inspectionDate.plusMonths(machine.getWarrantyMonths());
	                machine.setWarrantyEndDate(warrantyEnd);
	            }

	            machine.setInspectionStatus("Completed");
	            machineRepository.save(machine);
	            return "Site final inspection marked as complete.";
	        } 
	        
	        else if ("reinspect".equalsIgnoreCase(action) || "reinspection".equalsIgnoreCase(action)) {
	            LocalDateTime reinspectionDate = LocalDateTime.parse(inspectionDateStr);
	            machine.setReinspectionDecidedDate(reinspectionDate);
	            machine.setReinspectionRemark(remark);
	            machine.setInspectionStatus("Re-Inspection");
	            machine.setSiteFinalInspectionPending(true);

	            machineRepository.save(machine);
	            return "Marked for reinspection.";
	        } 
	        
	        else {
	            throw new IllegalArgumentException("Invalid action: " + action);
	        }

	    } catch (Exception e) {
	        return "Invalid date format. Please use ISO format (e.g., 2025-06-02T15:00)";
	    }
	}

	
	@Override
    public MachineDivisionSectionDTO getMachineDivisionSection(String modelNo) {
		Machine machine = machineRepository.findByModelNo(modelNo)
		  .orElseThrow(() -> new RuntimeException("Machine not found with modelNo: " + modelNo));


        MachineDivisionSectionDTO dto = new MachineDivisionSectionDTO();
        dto.setDivision(machine.getDivision());
        dto.setSection(machine.getSection());
        return dto;
    }

	@Override
	public List<DispatchReportDTO> getDispatchReportsByFilters(DispatchFilterDTO filter) {
		LocalDateTime fromDateTime = null;
	    LocalDateTime toDateTime = null;

	    // ✅ Use fromDate and toDate from the DTO
	    if (filter.getFromDate() != null) {
	        fromDateTime = filter.getFromDate().atStartOfDay(); // 00:00:00
	    }

	    if (filter.getToDate() != null) {
	        toDateTime = filter.getToDate().atTime(23, 59, 59); // 23:59:59 for inclusive filtering
	    }

	    return machineRepository.findDispatchReportsByFilters(
	        filter.getPoNumber(),
	        filter.getDivision(),
	        filter.getSection(),
	        fromDateTime,
	        toDateTime
	    );
	}
	
	
	//This is Used to Save Site Inspection data of the machine
	@Override
	public List<SitePendingInspectionDTO> getPendingSiteInspectionsWithInstallation() {
	    List<Machine> machines = machineRepository.findPendingSiteInspections();

	    return machines.stream().map(machine -> {
	        SitePendingInspectionDTO dto = new SitePendingInspectionDTO();
	        dto.setId(machine.getId());
	        dto.setModelNo(machine.getModelNo());
	        dto.setMachineName(machine.getMachineName());
	        dto.setDivision(machine.getDivision());
	        dto.setSection(machine.getSection());
	        dto.setDispatchDate(machine.getDispatchDate());
	        dto.setDeliveredDate(machine.getDeliveredDate());

	        // ✅ Set inspectionStatus so UI shows correct status
	        dto.setInspectionStatus(machine.getInspectionStatus());

	        // ✅ Add latest installation end date if available
	        installationRecordRepository.findTopByMachineIdOrderByIdDesc(machine.getId())
	            .ifPresent(record -> dto.setInstallationEnded(record.getInstallationEnded()));

	        return dto;
	    }).toList();
	}


	
	// This is used the process of the Machine Maintenance Service form
	public MaintenanceFormDTO createInspection(MaintenanceFormDTO dto) {
	    Machine machine = machineRepository.findByModelNo(dto.getModelNo())
	        .orElseThrow(() -> new RuntimeException("Machine not found"));

	    Users inspector = userRepository.findById(dto.getInspectedByUserId())
	        .orElseThrow(() -> new RuntimeException("Inspector not found"));

	    MachineInspection inspection = maintenanceRepository
	            .findTopByModelNoAndMaintenanceTechnicianIdAndMaintenanceEndedIsNullOrderByMaintenanceStartedDesc(
	                dto.getModelNo(), dto.getTechnicianUserId()
	            ).orElseThrow(() -> new RuntimeException("No active maintenance record found for this machine and technician."));

	    // ✅ Update all fields
	    inspection.setGreaseLevel(dto.getGreaseLevel());
	    inspection.setGreaseLevelPhotoUrl(dto.getGreaseLevelPhotoUrl());
	    inspection.setBatteryReading(dto.getBatteryReading());
	    inspection.setSolarPanelReading1(dto.getSolarPanelReading1());
	    inspection.setSolarPanelReading2(dto.getSolarPanelReading2());
	    inspection.setTimeCount(dto.getTimeCount());
	    inspection.setWheelCount(dto.getWheelCount());
	    inspection.setMachineInfoPlatePhotoUrl(dto.getMachineInfoPlatePhotoUrl());
	    inspection.setSensor(dto.getSensor());
	    inspection.setApplicator(dto.getApplicator());
	    inspection.setMachineStatus(dto.getMachineStatus());
	    inspection.setRemark(dto.getRemark());
	    inspection.setMaintenanceDate(LocalDateTime.now());
	    inspection.setMaintenanceEnded(LocalDateTime.now());
	    inspection.setInspectedBy(inspector);

	    maintenanceRepository.save(inspection);

	    return dto;
	}

	@Override
	public Object scheduleReinspection(Long machineId, String reinspectionDecidedDate, String reinspectionRemark) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Transactional
    public void updateMachineStatus(String modelNo, String status) {
        int updatedCount = machineRepository.updateMachineStatus(modelNo, status);
        if (updatedCount == 0) {
            throw new RuntimeException("No machine found with model number: " + modelNo);
        }
	}

	@Override
	public String dispatchMachine(DispatchFormDTO dispatchFormDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<MachineWithInstallationDTO> getMachinesWithInstallationDate(String status) {
	    List<Machine> machines = machineRepository.findByStatus(status.toUpperCase());

	    return machines.stream().map(machine -> {
	        LocalDateTime installationDate = installationRecordRepository
	                .findTopByMachineIdOrderByIdDesc(machine.getId())
	                .map(record -> record.getInstallationStarted())  // Or `.getInstallationEnded()`
	                .orElse(null);

	        return new MachineWithInstallationDTO(
	                machine.getId(),
	                machine.getModelNo(),
	                machine.getMachineName(),
	                machine.getDivision(),
	                machine.getSection(),
	                machine.getStatus(),
	                machine.getDeliveredDate(),
	                installationDate,
	                machine.getWarrantyEndDate()
	        );
	    }).toList();
	}

	


	
}
 
	

