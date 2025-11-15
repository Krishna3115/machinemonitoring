package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.MachineProductionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineStatusSummaryDTO;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.MachineProduction.MachineStatus;

public interface MachineProductionService {

	MachineProductionDTO createMachine(MachineProductionDTO dto);
    List<MachineProductionDTO> getAvailableMachines();
    void markAsDispatched(Long id);
    
 // MachineProductionService.java
    long countAvailableMachines();
	List<MachineProductionDTO> getMachinesPendingForQualityCheck();
	List<MachineProductionDTO> findByStatus(MachineStatus status);
	void completeQualityCheck(List<Long> machineIds, MultipartFile qcFile) throws IOException;
	long countReadyToDispatchMachines();
	List<MachineStatusSummaryDTO> getReadyToDispatchMachines();

}
