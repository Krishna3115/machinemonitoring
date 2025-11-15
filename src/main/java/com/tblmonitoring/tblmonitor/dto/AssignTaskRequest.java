package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class AssignTaskRequest {

	 private Long technicianId;
	 @Column(name = "machine_number", nullable = true)
	    private String machineNumber;
	    private String taskType;
	    private LocalDate scheduleDate;
	    
	    private Long assignedById;
	    
	    public AssignTaskRequest() {
			// TODO Auto-generated constructor stub
		}

		public AssignTaskRequest(Long technicianId, String machineNumber, String taskType, LocalDate scheduleDate,
				Long assignedById) {
			super();
			this.technicianId = technicianId;
			this.machineNumber = machineNumber;
			this.taskType = taskType;
			this.scheduleDate = scheduleDate;
			this.assignedById = assignedById;
		}

		public Long getTechnicianId() {
			return technicianId;
		}

		public void setTechnicianId(Long technicianId) {
			this.technicianId = technicianId;
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

		public Long getAssignedById() {
			return assignedById;
		}

		public void setAssignedById(Long assignedById) {
			this.assignedById = assignedById;
		}
	    
	    

}
