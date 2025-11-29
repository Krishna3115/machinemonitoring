package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskAssignmentRequestDTO {

    @JsonProperty("assignedById")
    private Long assignedById;

    @JsonProperty("technicianId")
    private Long technicianId;

    @JsonProperty("machineIds")
    private List<Long> machineIds;

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("targetDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @JsonProperty("taskType")
    private String taskType;
    
    public TaskAssignmentRequestDTO() {
		// TODO Auto-generated constructor stub
	}
    
    

    public TaskAssignmentRequestDTO(Long assignedById, Long technicianId, List<Long> machineIds, LocalDate startDate,
			LocalDate targetDate, String taskType) {
		super();
		this.assignedById = assignedById;
		this.technicianId = technicianId;
		this.machineIds = machineIds;
		this.startDate = startDate;
		this.targetDate = targetDate;
		this.taskType = taskType;
	}



	// Getters and Setters
    public Long getAssignedById() { return assignedById; }
    public void setAssignedById(Long assignedById) { this.assignedById = assignedById; }

    public Long getTechnicianId() { return technicianId; }
    public void setTechnicianId(Long technicianId) { this.technicianId = technicianId; }

    public List<Long> getMachineIds() { return machineIds; }
    public void setMachineIds(List<Long> machineIds) { this.machineIds = machineIds; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }
}
