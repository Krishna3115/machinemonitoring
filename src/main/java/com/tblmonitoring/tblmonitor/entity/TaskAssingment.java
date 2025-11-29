package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;

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
@Table(name = "task_assignments")
public class TaskAssingment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_number")
    private String machineNumber;

    @Column(name = "task_type", nullable = false)
    private String taskType; // Installation or Maintenance

    @Column(name = "schedule_date")
    private LocalDate scheduleDate; // For maintenance

    @Column(name = "start_date")
    private LocalDate startDate; // For installation

    @Column(name = "target_date")
    private LocalDate targetDate; // For installation

    @Column(name = "status")
    private String status = "Pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", nullable = false)
    private Users technician;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    
    public TaskAssingment() {
		// TODO Auto-generated constructor stub
	}


	public TaskAssingment(Long id, String machineNumber, String taskType, LocalDate scheduleDate,
			LocalDate startDate, LocalDate targetDate, String status, Users technician, Machine machine) {
		super();
		this.id = id;
		this.machineNumber = machineNumber;
		this.taskType = taskType;
		this.scheduleDate = scheduleDate;
		this.startDate = startDate;
		this.targetDate = targetDate;
		this.status = status;
		this.technician = technician;
		this.machine = machine;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getTargetDate() {
		return targetDate;
	}


	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Users getTechnician() {
		return technician;
	}


	public void setTechnician(Users technician) {
		this.technician = technician;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}
    
	
}
