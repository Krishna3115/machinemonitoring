package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.MachineStatusDTO;

public interface MachineStatusService {

	MachineStatusDTO getMachineStatus(String modelNo);
	
	 MachineStatusDTO getMachineStatusByModelNo(String modelNo);
	    long countMachinesUnderMaintenance();
	    List<MachineStatusDTO> getAllUnderMaintenanceMachines();

		void resolveMachineStatus(String modelNo);

		void updateMachineStatusToUnderMaintenance(String modelNo);

		List<MachineStatusDTO> getAllActiveMachines();

		List<MachineStatusDTO> getActiveMachines();
}
