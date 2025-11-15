package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)// This will ignore extra fields if any
public class TaskAssignmentRequestDTO {

	@JsonProperty("technicianId")
    private Long technicianId;

    @JsonProperty("assignedById")
    private Long assignedById;

    @JsonProperty("machineNumber")
    private String machineNumber;

    @JsonProperty("taskType")
    private String taskType;

    @JsonProperty("scheduleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @JsonProperty("section")
    private String section;

    @JsonProperty("division")
    private String division;

    @JsonProperty("serialNumber")
    private String serialNumber;

    // Constructors
    public TaskAssignmentRequestDTO() {}

    public TaskAssignmentRequestDTO(Long technicianId, Long assignedById, String machineNumber, String taskType,
                                    LocalDate scheduleDate, String section, String division, String serialNumber) {
        this.technicianId = technicianId;
        this.assignedById = assignedById;
        this.machineNumber = machineNumber;
        this.taskType = taskType;
        this.scheduleDate = scheduleDate;
        this.section = section;
        this.division = division;
        this.serialNumber = serialNumber;
    }

    // Getters and Setters
    public Long getTechnicianId() { return technicianId; }
    public void setTechnicianId(Long technicianId) { this.technicianId = technicianId; }

    public Long getAssignedById() { return assignedById; }
    public void setAssignedById(Long assignedById) { this.assignedById = assignedById; }

    public String getMachineNumber() { return machineNumber; }
    public void setMachineNumber(String machineNumber) { this.machineNumber = machineNumber; }

    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }

    public LocalDate getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
}
