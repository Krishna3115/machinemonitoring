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

    @Column(name = "machine_number", nullable = true)
    private String machineNumber;

    @Column(name = "task_type", nullable = false)
    private String taskType;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "status")
    private String status = "Pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", nullable = false)
    private Users technician;


    public TaskAssingment() {
		// TODO Auto-generated constructor stub
	}


	public TaskAssingment(Long id, String machineNumber, String taskType, LocalDate scheduleDate, String status,
			Users technician) {
		super();
		this.id = id;
		this.machineNumber = machineNumber;
		this.taskType = taskType;
		this.scheduleDate = scheduleDate;
		this.status = status;
		this.technician = technician;
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
    
    
    
}
