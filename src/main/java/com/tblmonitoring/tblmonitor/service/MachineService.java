package com.tblmonitoring.tblmonitor.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.DispatchFilterDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchFormDTO;
import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.MachineDivisionSectionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineWithInstallationDTO;
import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.dto.SitePendingInspectionDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;

public interface MachineService {

	String dispatchMachine(DispatchFormDTO dispatchFormDTO);

    // Method to update the delivery date of a machine
    String updateDeliveryDate(Long machineId, String deliveredDate, MultipartFile receivingLetter);
    Machine getMachineByModelNo(String modelNo);
    String submitMaintenanceForm(MaintenanceFormDTO form);
    List<Machine> getMachinesByStatus(String status);
 // Get all machines with site final inspection pending
    List<Machine> getPendingSiteInspections();

    // Mark site final inspection as complete with a date
    String completeSiteInspection(Long machineId, String inspectionDateStr);
    List<Machine> getMachinesInstalling();
    
    MachineDivisionSectionDTO getMachineDivisionSection(String modelNo);

	List<DispatchReportDTO> getDispatchReportsByFilters(DispatchFilterDTO filter);

	List<SitePendingInspectionDTO> getPendingSiteInspectionsWithInstallation();

	MaintenanceFormDTO createInspection(MaintenanceFormDTO dto);

	Object scheduleReinspection(Long machineId, String reinspectionDecidedDate, String reinspectionRemark);

	String updateSiteInspection(Long machineId, String action, String inspectionDateStr, String remark);

	//void dispatchMachines(DispatchFormDTO dto);

	void dispatchMachines(DispatchFormDTO dto, List<MultipartFile> pdiReports);

	List<MachineWithInstallationDTO> getMachinesWithInstallationDate(String status);

	List<Machine> getAllMachines();
	
    
    
}
