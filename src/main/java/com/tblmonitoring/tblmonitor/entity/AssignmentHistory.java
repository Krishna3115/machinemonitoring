package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assignment_history")
public class AssignmentHistory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who assigned the task (admin or user assigning)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_user_id", nullable = false)
    private Users assignedBy;

    // To whom the task is assigned (technician)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id", nullable = false)
    private Users assignedTo;

    @Column(name = "machine_number", nullable = false)
    private String machineNumber;

    @Column(name = "task_type", nullable = false)
    private String taskType;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    public AssignmentHistory() {
		// TODO Auto-generated constructor stub
	}

	public AssignmentHistory(Long id, Users assignedBy, Users assignedTo, String machineNumber, String taskType,
			LocalDate scheduleDate, LocalDateTime assignedAt) {
		super();
		this.id = id;
		this.assignedBy = assignedBy;
		this.assignedTo = assignedTo;
		this.machineNumber = machineNumber;
		this.taskType = taskType;
		this.scheduleDate = scheduleDate;
		this.assignedAt = assignedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(Users assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Users getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Users assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public LocalDate getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDate scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public LocalDateTime getAssignedAt() {
		return assignedAt;
	}

	public void setAssignedAt(LocalDateTime assignedAt) {
		this.assignedAt = assignedAt;
	}
    
    
}
