package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.repository.AssignmentHistoryRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.TaskAssignmentRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final TaskAssignmentRepository taskAssignmentRepo;
    private final AssignmentHistoryRepository historyRepo;
    private final UserRepository userRepo;

    @Autowired
    private MachineRepository machinesRepository;

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

    // Single task assignment
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
        task.setStatus("Pending");
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

    // Bulk installation assignment
    @Override
    public void assignInstallationTasks(Long assignedById, Long technicianId, List<Long> machineIds,
                                        LocalDate startDate, LocalDate targetDate) {

        Users technician = userRepo.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));
        Users assignedBy = userRepo.findById(assignedById)
                .orElseThrow(() -> new RuntimeException("Admin/User not found"));

        for (Long machineId : machineIds) {
            Machine machine = machinesRepository.findById(machineId)
                    .orElseThrow(() -> new RuntimeException("Machine not found"));

            // Prevent duplicate task assignment
            boolean exists = taskAssignmentRepo.existsByMachineNumberAndStatus(machine.getModelNo(), "Pending");
            if (exists) continue;

            TaskAssingment task = new TaskAssingment();
            task.setTechnician(technician);
            task.setMachineNumber(machine.getModelNo());
            task.setTaskType("Installation");
            task.setScheduleDate(startDate);
            task.setStartDate(startDate);
            task.setTargetDate(targetDate);
            task.setStatus("Pending");
            taskAssignmentRepo.save(task);

            AssignmentHistory history = new AssignmentHistory();
            history.setAssignedBy(assignedBy);
            history.setAssignedTo(technician);
            history.setMachineNumber(machine.getModelNo());
            history.setTaskType("Installation");
            history.setScheduleDate(startDate);
            history.setAssignedAt(LocalDateTime.now());
            historyRepo.save(history);
        }
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
    
    @Override
    public List<TaskAssingment> getPendingInstallationTasks(Long technicianId) {
        return taskAssignmentRepo.findByTechnicianIdAndTaskTypeAndStatus(
                technicianId,
                "Installation",
                "Pending"
        );
    }

}
