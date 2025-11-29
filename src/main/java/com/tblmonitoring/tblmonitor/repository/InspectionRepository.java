package com.tblmonitoring.tblmonitor.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.MachineInspection;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;

public interface InspectionRepository extends JpaRepository<MachineInspection, Long>{

	int countByMachineStatus(String machineStatus);
	
	List<MachineInspection> findByMachineIdOrderByMaintenanceDateDesc(Long machineId);

    // Get upcoming maintenance entries due within next X days
    @Query("SELECT mi FROM MachineInspection mi WHERE mi.maintenanceDate BETWEEN :from AND :to")
    List<MachineInspection> findUpcomingMaintenances
    		(@Param("from") LocalDateTime from,
    	    @Param("to") LocalDateTime to);

    // Get last maintenance by machine (optional if needed)
    @Query("SELECT mi FROM MachineInspection mi WHERE mi.machine.id = :machineId ORDER BY mi.maintenanceDate DESC LIMIT 1")
    MachineInspection findLatestByMachineId(Long machineId);
	    
    @Query("SELECT mi FROM MachineInspection mi")
    List<MachineInspection> findAllInspections();

    Optional<MachineInspection> findTopByModelNoOrderByMaintenanceStartedDesc(String modelNo);

    Optional<MachineInspection> findTopByModelNoAndMaintenanceTechnicianIdAndMaintenanceEndedIsNullOrderByMaintenanceStartedDesc(
    	    String modelNo,
    	    Long maintenanceTechnicianId
    	);
    
 // Fetch the latest maintenance date by modelNo
    @Query("SELECT MAX(mi.maintenanceDate) FROM MachineInspection mi WHERE mi.modelNo = :modelNo")
    LocalDateTime findLatestMaintenanceDateByModelNo(@Param("modelNo") String modelNo);

    Optional<MachineInspection> findByMachineAndModelNo(Machine machine, String modelNo);

}
