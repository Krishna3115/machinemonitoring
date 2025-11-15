package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.entity.Users;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssingment, Long> {
	
    List<TaskAssingment> findByTechnician(Users technician);
}
