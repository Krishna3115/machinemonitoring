package com.tblmonitoring.tblmonitor.controller;

import com.tblmonitoring.tblmonitor.entity.TaskAssingment;
import com.tblmonitoring.tblmonitor.dto.AssignTaskRequest;
import com.tblmonitoring.tblmonitor.dto.TaskAssignmentRequestDTO;
import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;
import com.tblmonitoring.tblmonitor.service.AssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    /**
     * POST /api/assign-technician
     * Assigns a task to a technician and stores in history.
     */
    @PostMapping("/assign-technician")
    public String assignTask(@RequestBody TaskAssignmentRequestDTO request) {
        if (request.getTechnicianId() == null || request.getAssignedById() == null) {
            throw new IllegalArgumentException("Technician ID and Assigned By ID must be provided");
        }
        if (request.getTaskType() == null || request.getScheduleDate() == null) {
            throw new IllegalArgumentException("taskType and scheduleDate must not be null");
        }

        assignmentService.assignTask(
            request.getAssignedById(),
            request.getTechnicianId(),
            request.getMachineNumber(),
            request.getTaskType(),
            request.getScheduleDate()
        );

        return "Technician assigned successfully";
    }


    /**
     * GET /api/tasks/my-tasks
     * Returns tasks assigned to the currently logged-in technician.
     */
    @GetMapping("/tasks/my-tasks")
    public List<TaskAssingment> getMyTasks(@RequestParam("technicianId") Long technicianId) {
        return assignmentService.getTasksForTechnician(technicianId);
    }

    /**
     * GET /api/tasks/history
     * Returns full task assignment history (for admin view).
     */
    @GetMapping("/tasks/history")
    public List<AssignmentHistory> getTaskHistory() {
        return assignmentService.getAllAssignmentHistory();
    }

    // Mock method – replace with proper JWT decoding or Spring Security integration
    private Long getCurrentUserIdFromPrincipal(Principal principal) {
        return Long.parseLong(principal.getName()); // assuming the user ID is stored in principal name
    }
}
