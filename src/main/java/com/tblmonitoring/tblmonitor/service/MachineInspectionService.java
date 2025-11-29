package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.Map;

import com.tblmonitoring.tblmonitor.dto.MachineLocationDTO;
import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.entity.MachineInspection;

public interface MachineInspectionService {

	List<MaintenanceFormDTO> getMaintenanceHistory(Long machineId);
    List<MaintenanceFormDTO> getMaintenancesDueInNextDays(int days);
    MaintenanceFormDTO createInspection(MaintenanceFormDTO dto);
    MachineLocationDTO getMachineLocationByModelNo(String modelNo);
    MachineLocationDTO getCombinedInfo(String modelNo);
	//String startMaintenance(String modelNo, Long technicianId);
	List<MaintenanceFormDTO> getFilteredMaintenanceRecords(String modelNo, String fromDateStr, String toDateStr);
	List<Map<String, Object>> getMonthlyVisitCounts(String modelNo);
	String endMaintenance(String modelNo, Long technicianId);
	String completeMaintenance(MaintenanceFormDTO dto);
	// Before
	//String startMaintenance(String modelNo, Long technicianId);

	// After
	MachineInspection startMaintenance(String modelNo, Long technicianId);


}
