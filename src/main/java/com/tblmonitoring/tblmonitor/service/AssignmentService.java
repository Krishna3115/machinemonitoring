package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.util.List;

import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;
import com.tblmonitoring.tblmonitor.entity.TaskAssingment;

public interface AssignmentService {
	
	void assignTask(Long assignedById, Long technicianId, String machineNumber, String taskType, LocalDate scheduleDate);
    List<TaskAssingment> getTasksForTechnician(Long technicianId);
    List<AssignmentHistory> getAllAssignmentHistory();

}
