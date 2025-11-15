package com.tblmonitoring.tblmonitor.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.entity.CustomerComplaint;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;

public interface CustomerComplaintRepository extends JpaRepository<CustomerComplaint, Long>{

	long countByStatus(ComplaintStatus status);
	List<CustomerComplaint> findByStatus(ComplaintStatus status);
	
	@Query("SELECT DISTINCT c.machine FROM CustomerComplaint c WHERE c.status = :status")
	List<Machine> findMachinesByStatus(@Param("status") ComplaintStatus status);

	@Query("SELECT MAX(c.issueSubmitDate) FROM CustomerComplaint c WHERE c.modelNo = :modelNo")
	LocalDateTime findLatestComplaintDateByModelNo(@Param("modelNo") String modelNo);


}
