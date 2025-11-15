package com.tblmonitoring.tblmonitor.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.dto.DispatchReportDTO;
import com.tblmonitoring.tblmonitor.dto.SitePendingInspectionDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

Optional<Machine> findByModelNo(String modelNo);
	
	List<Machine> findByStatus(String status);

	@Query("SELECT DISTINCT m.section FROM Machine m WHERE m.division = :division")
	List<String> findSectionsByDivision(@Param("division") String division);

	@Query("SELECT m.modelNo FROM InstallationRecord i " +
	       "JOIN i.machine m " +
	       "WHERE m.division = :division AND i.section = :section " +
	       "AND i.fromKm <= :kmFrom AND i.toKm >= :kmTo")
	List<String> findModelNoByDivisionSectionAndKm(
	    @Param("division") String division,
	    @Param("section") String section,
	    @Param("kmFrom") String kmFrom,
	    @Param("kmTo") String kmTo
	);

	@Query("SELECT m FROM Machine m WHERE m.siteFinalInspectionPending = true")
	List<Machine> findPendingSiteInspections();

	List<Machine> findByInspectionStatus(String status);  // example

	@Query("SELECT m FROM Machine m WHERE m.siteFinalInspectionPending = true")
	List<Machine> getPendingSiteInspectionsWithInstallation();

	int countByPurchaseOrder_Id(Long poId);

	@Query("SELECT m FROM Machine m LEFT JOIN m.purchaseOrder p " +
		       "WHERE (:poNumber IS NULL OR p.poNumber = :poNumber) " +
		       "AND (:division IS NULL OR m.division = :division) " +
		       "AND (:section IS NULL OR m.section = :section) " +
		       "AND (:fromDate IS NULL OR m.dispatchDate >= :fromDate) " +
		       "AND (:toDate IS NULL OR m.dispatchDate <= :toDate)")
		List<Machine> findMachinesByFilters(
		    @Param("poNumber") String poNumber,
		    @Param("division") String division,
		    @Param("section") String section,
		    @Param("fromDate") LocalDateTime fromDate,
		    @Param("toDate") LocalDateTime toDate
		);


	@Query("SELECT new com.tblmonitoring.tblmonitor.dto.DispatchReportDTO(" +
		       "m.id, m.modelNo, m.machineName, m.status, m.dispatchDate, m.deliveredDate, " +
		       "m.division, m.section, p.poNumber, m.finalInspectionDoneBy) " + // include poNumber
		       "FROM Machine m LEFT JOIN m.purchaseOrder p " +
		       "WHERE (:poNumber IS NULL OR p.poNumber = :poNumber) " +
		       "AND (:division IS NULL OR m.division = :division) " +
		       "AND (:section IS NULL OR m.section = :section) " +
		       "AND (:startOfDay IS NULL OR (m.dispatchDate >= :startOfDay AND m.dispatchDate < :endOfDay))")
	List<DispatchReportDTO> findDispatchReportsByFilters(
	    @Param("poNumber") String poNumber,
	    @Param("division") String division,
	    @Param("section") String section,
	    @Param("startOfDay") LocalDateTime startOfDay,
	    @Param("endOfDay") LocalDateTime endOfDay
	);


	List<Machine> findAll();

    // Method to fetch filtered data based on user input
    @Query("SELECT m FROM Machine m WHERE m.status = :status") // example filter
    List<Machine> findFilteredData(@Param("status") String status);
    
    @Modifying
    @Query("UPDATE Machine m SET m.status = :status WHERE m.modelNo = :modelNo")
    int updateMachineStatus(@Param("modelNo") String modelNo, @Param("status") String status);
    
    long countByStatus(String status);
    
}

		//List<Machine> findMachinesByFilters(String poNumber, String division, String section, LocalDate dispatchDate);


		
	
