package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;
import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.repository.AssignmentHistoryRepository;
import com.tblmonitoring.tblmonitor.repository.TaskAssignmentRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

	 private final TaskAssignmentRepository taskAssignmentRepo;
	    private final AssignmentHistoryRepository historyRepo;
	    private final UserRepository userRepo;

	    @Autowired
	    public AssignmentServiceImpl(
	        TaskAssignmentRepository taskAssignmentRepo,
	        AssignmentHistoryRepository historyRepo,
	        UserRepository userRepo
	    ) {
	        this.taskAssignmentRepo = taskAssignmentRepo;
	        this.historyRepo = historyRepo;
	        this.userRepo = userRepo;
	    }

	    @Override
	    public void assignTask(Long assignedById, Long technicianId, String machineNumber, String taskType, LocalDate scheduleDate) {
	        if (assignedById == null || technicianId == null) {
	            throw new IllegalArgumentException("assignedById and technicianId must not be null");
	        }
	        
	        Users technician = userRepo.findById(technicianId)
	            .orElseThrow(() -> new RuntimeException("Technician not found with id " + technicianId));
	        Users assignedBy = userRepo.findById(assignedById)
	            .orElseThrow(() -> new RuntimeException("Admin/User not found with id " + assignedById));

	        TaskAssingment task = new TaskAssingment();
	        task.setTechnician(technician);
	        task.setMachineNumber(machineNumber);
	        task.setTaskType(taskType);
	        task.setScheduleDate(scheduleDate);
	        // Optionally, set additional fields like status, createdAt etc.
	        taskAssignmentRepo.save(task);

	        AssignmentHistory history = new AssignmentHistory();
	        history.setAssignedBy(assignedBy);
	        history.setAssignedTo(technician);
	        history.setMachineNumber(machineNumber);
	        history.setTaskType(taskType);
	        history.setScheduleDate(scheduleDate);
	        history.setAssignedAt(LocalDateTime.now());
	        historyRepo.save(history);
	    }

	    @Override
	    public List<TaskAssingment> getTasksForTechnician(Long technicianId) {
	        if (technicianId == null) {
	            throw new IllegalArgumentException("technicianId must not be null");
	        }

	        Users technician = userRepo.findById(technicianId)
	            .orElseThrow(() -> new RuntimeException("Technician not found with id " + technicianId));

	        return taskAssignmentRepo.findByTechnician(technician);
	    }

	    @Override
	    public List<AssignmentHistory> getAllAssignmentHistory() {
	        return historyRepo.findAll();
	    }
}
