package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.entity.Users;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssingment, Long> {
	
    List<TaskAssingment> findByTechnician(Users technician);

	boolean existsByMachineNumberAndStatus(String modelNo, String string);
	
	List<TaskAssingment> findByTechnicianIdAndMachine_Status(Long technicianId, String status);

	// ✅ Correct method to fetch pending installation tasks
    List<TaskAssingment> findByTechnicianIdAndTaskTypeAndStatus(
            Long technicianId,
            String taskType,
            String status
    );

}
