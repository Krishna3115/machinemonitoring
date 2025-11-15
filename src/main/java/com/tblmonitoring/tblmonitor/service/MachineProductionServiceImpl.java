package com.tblmonitoring.tblmonitor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.MachineProductionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineStatusSummaryDTO;
import com.tblmonitoring.tblmonitor.entity.JobCard;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.MachineProduction.MachineStatus;
import com.tblmonitoring.tblmonitor.repository.JobCardRepository;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;

@Service
public class MachineProductionServiceImpl implements MachineProductionService {

    @Autowired
    private MachineProductionRepository repository;
    
    @Autowired
    private JobCardRepository jobCardRepository;

    @Override
    public MachineProductionDTO createMachine(MachineProductionDTO dto) {
        MachineProduction entity = new MachineProduction();
        entity.setMachineSerialNo(dto.getMachineSerialNo());
        entity.setJobCardNo(dto.getJobCardNo());
        entity.setMotorNo(dto.getMotorNo());
        entity.setSensorNo(dto.getSensorNo());
        entity.setApplicatorNo(dto.getApplicatorNo());
        entity.setBatteryNo(dto.getBatteryNo());
        entity.setSolarChargeControllerNo(dto.getSolarChargeControllerNo());
        entity.setSolarPanelNo1(dto.getSolarPanelNo1());
        entity.setSolarPanelNo2(dto.getSolarPanelNo2());
        entity.setCabinetNo(dto.getCabinetNo());
        entity.setBatchCounterNo(dto.getBatchCounterNo());
        entity.setMcbNo(dto.getMcbNo());
        entity.setGearPumpNo(dto.getGearPumpNo());
        entity.setStatus(MachineProduction.MachineStatus.AVAILABLE);

        entity = repository.save(entity);
        dto.setId(entity.getId());

        JobCard job = jobCardRepository.findByJobCardNumber(dto.getJobCardNo());
        if (job != null) {
            job.setProducedCount(job.getProducedCount() + 1);
            jobCardRepository.save(job);
        }

        return dto;
    }

    
    @Override
    public List<MachineProductionDTO> getAvailableMachines() {
        return repository.findByStatus(MachineProduction.MachineStatus.READY_TO_DISPATCH)
                .stream()
                .map(e -> new MachineProductionDTO(
                	    e.getId(),
                	    e.getMachineSerialNo(),
                	    e.getJobCardNo(),
                	    e.getMotorNo(),
                	    e.getSensorNo(),
                	    e.getApplicatorNo(),
                	    e.getBatteryNo(),
                	    e.getSolarChargeControllerNo(),
                	    e.getSolarPanelNo1(),
                	    e.getSolarPanelNo2(),
                	    e.getCabinetNo(),
                	    e.getBatchCounterNo(),
                	    e.getMcbNo(),
                	    e.getGearPumpNo(),
                	    e.getQcFilePath(),
                	    e.getQcInspectionDate(),
                	    e.getCreatedAt() 
                	))
                .collect(Collectors.toList());
    }

    @Override
    public void markAsDispatched(Long id) {
        MachineProduction entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Machine not found"));
        entity.setStatus(MachineProduction.MachineStatus.DISPATCHED);
        repository.save(entity);
    }
    
    
 // MachineProductionServiceImpl.java

    @Override
    public long countAvailableMachines() {
        return repository.countByStatus(MachineProduction.MachineStatus.AVAILABLE);
    }

    @Override
    public List<MachineProductionDTO> getMachinesPendingForQualityCheck() {
        return repository.findByStatus(MachineProduction.MachineStatus.AVAILABLE)
            .stream()
            .map(e -> new MachineProductionDTO(
            	    e.getId(),
            	    e.getMachineSerialNo(),
            	    e.getJobCardNo(),
            	    e.getMotorNo(),
            	    e.getSensorNo(),
            	    e.getApplicatorNo(),
            	    e.getBatteryNo(),
            	    e.getSolarChargeControllerNo(),
            	    e.getSolarPanelNo1(),
            	    e.getSolarPanelNo2(),
            	    e.getCabinetNo(),
            	    e.getBatchCounterNo(),
            	    e.getMcbNo(),
            	    e.getGearPumpNo(),
            	    e.getQcFilePath(),              // ✅ Add this
            	    e.getQcInspectionDate(),
            	    e.getCreatedAt() // ✅ And this
            	))
            .collect(Collectors.toList());
    }


    @Override
    public List<MachineProductionDTO> findByStatus(MachineStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(e -> new MachineProductionDTO(
                	    e.getId(),
                	    e.getMachineSerialNo(),
                	    e.getJobCardNo(),
                	    e.getMotorNo(),
                	    e.getSensorNo(),
                	    e.getApplicatorNo(),
                	    e.getBatteryNo(),
                	    e.getSolarChargeControllerNo(),
                	    e.getSolarPanelNo1(),
                	    e.getSolarPanelNo2(),
                	    e.getCabinetNo(),
                	    e.getBatchCounterNo(),
                	    e.getMcbNo(),
                	    e.getGearPumpNo(),
                	    e.getQcFilePath(),              // ✅ Add this
                	    e.getQcInspectionDate(),
                	    e.getCreatedAt() // ✅ And this
                	))
                .collect(Collectors.toList());
    }
    
    @Override
    public void completeQualityCheck(List<Long> machineIds, MultipartFile qcFile) throws IOException {
        // 1. Get absolute path to "uploads/qc-reports"
        Path projectRoot = Paths.get("").toAbsolutePath(); // Current working directory (e.g. your Spring Boot root)
        Path uploadDir = projectRoot.resolve("uploads/qc-reports");

        // 2. Ensure the directory exists
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 3. Build full path to save file
        String fileName = System.currentTimeMillis() + "_" + qcFile.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        // 4. Save the file
        qcFile.transferTo(filePath.toFile());

        // 5. Update DB with relative path
        List<MachineProduction> machines = repository.findByIdIn(machineIds);
        for (MachineProduction m : machines) {
            m.setQcFilePath("uploads/qc-reports/" + fileName);  // ✅ Store relative path
            m.setQcInspectionDate(LocalDateTime.now());
            m.setStatus(MachineProduction.MachineStatus.READY_TO_DISPATCH);
        }

        repository.saveAll(machines);
    }

    @Override
    public long countReadyToDispatchMachines() {
        return repository.countByStatus(MachineProduction.MachineStatus.READY_TO_DISPATCH);
    }
    
    
    @Override
    public List<MachineStatusSummaryDTO> getReadyToDispatchMachines() {
        return repository.findByStatus(MachineProduction.MachineStatus.READY_TO_DISPATCH)
                .stream()
                .map(machine -> new MachineStatusSummaryDTO(
                        machine.getMachineSerialNo(),
                        machine.getStatus().name(),
                        machine.getQcInspectionDate()
                ))
                .collect(Collectors.toList());
    }


}
