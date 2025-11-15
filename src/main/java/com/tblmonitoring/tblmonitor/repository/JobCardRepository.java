package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tblmonitoring.tblmonitor.entity.JobCard;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long>{

	 @Query("SELECT j FROM JobCard j WHERE j.jobCardNumber LIKE :prefix ORDER BY j.id DESC LIMIT 1")
	    JobCard findLastByJobCardNumberPrefix(@Param("prefix") String prefix);
	 
	 @Query(value = "SELECT MAX(CAST(SUBSTRING_INDEX(machine_serial_numbers, '-', -1) AS UNSIGNED)) " +
             "FROM job_card_machine_serial_numbers " +
             "WHERE machine_serial_numbers LIKE CONCAT(:prefix, '%')", nativeQuery = true)
	 		Integer findMaxSerialNumberByType(@Param("prefix") String prefix);

	 
	 @Query("SELECT j FROM JobCard j WHERE j.producedCount < j.quantity")
	 List<JobCard> findByQuantityGreaterThanProduced();

	 @Query("SELECT j FROM JobCard j WHERE j.producedCount IS NULL OR j.producedCount < j.quantity")
	 List<JobCard> findInProgressJobCards();


	 @Query("SELECT COUNT(j) FROM JobCard j WHERE j.producedCount IS NULL OR j.producedCount < j.quantity")
	 int countInProgressJobCards();

	 JobCard findByJobCardNumber(String jobCardNumber);

}
