package com.tblmonitoring.tblmonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tblmonitoring.tblmonitor.dto.TaskAssignmentRequestDTO;
import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;
import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.service.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    /**
     * Bulk Assign Installation Tasks
     */
    @PostMapping("/assign-installation-tasks")
    public String assignInstallationTasks(@RequestBody TaskAssignmentRequestDTO request) {
        if (request.getTechnicianId() == null || request.getAssignedById() == null || request.getMachineIds() == null || request.getMachineIds().isEmpty()) {
            throw new IllegalArgumentException("Technician ID, Assigned By ID, and Machine IDs must be provided");
        }
        assignmentService.assignInstallationTasks(
                request.getAssignedById(),
                request.getTechnicianId(),
                request.getMachineIds(),
                request.getStartDate(),
                request.getTargetDate()
        );
        return "Technician assigned successfully for bulk installation!";
    }

    /**
     * GET /api/tasks/my-tasks
     */
    @GetMapping("/tasks/my-tasks")
    public List<TaskAssingment> getMyTasks(@RequestParam("technicianId") Long technicianId) {
        return assignmentService.getTasksForTechnician(technicianId);
    }

    /**
     * GET /api/tasks/history
     */
    @GetMapping("/tasks/history")
    public List<AssignmentHistory> getTaskHistory() {
        return assignmentService.getAllAssignmentHistory();
    }
}
