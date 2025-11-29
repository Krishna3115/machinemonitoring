package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;

@Repository
public interface InstallationRecordRepository extends JpaRepository<InstallationRecord, Long> {
	
	@Query("SELECT ir FROM InstallationRecord ir WHERE ir.modelNo = :modelNo AND ir.installationEnded IS NULL")
	List<InstallationRecord> findActiveByModelNo(@Param("modelNo") String modelNo);

    Optional<InstallationRecord> findByModelNo(String modelNo);

    @Query("SELECT ir FROM InstallationRecord ir WHERE ir.installationEnded IS NOT NULL")
    List<InstallationRecord> findAllWithInstallationEnded();
    
    @Query("SELECT new com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO(" +
    	       "m.modelNo, ir.installationStarted, m.division, m.section) " +
    	       "FROM InstallationRecord ir JOIN ir.machine m " +
    	       "WHERE m.status = 'INSTALLING'")
    	List<InstallationProgressDTO> findInstallationsInProgress();

    @Query("SELECT ir FROM InstallationRecord ir WHERE ir.installationTechnicianId = :techId AND ir.installationEnded IS NULL")
    List<InstallationRecord> findByTechnicianIdAndNotCompleted(@Param("techId") Long technicianId);


    @Query("SELECT i FROM InstallationRecord i WHERE i.installationTechnicianId = :techId AND i.installationEnded IS NULL")
    List<InstallationRecord> findActiveInstallationsByTechnicianId(@Param("techId") Long technicianId);

    Optional<InstallationRecord> findTopByModelNoOrderByInstallationStartedDesc(String modelNo);


	
	Optional<InstallationRecord> findTopByMachineIdOrderByIdDesc(Long machineId);
	
	@Query("SELECT ir.machine FROM InstallationRecord ir WHERE ir.machine.status = :status")
	List<Machine> findMachinesByInstallationStatus(@Param("status") String status);

	InstallationRecord findByMachine(Machine machine);

	Optional<InstallationRecord> findByMachine_ModelNo(String modelNo);
	
}
