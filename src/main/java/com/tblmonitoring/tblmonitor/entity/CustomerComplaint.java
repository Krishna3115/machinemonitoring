package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDateTime;

import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_complaints")
public class CustomerComplaint {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String division;
    private String section;
    private double fromKm;
    private double toKm;
    
    @Column(name = "model_no")
    private String modelNo;

    @Column(columnDefinition = "TEXT")
    private String machineIssue;

    @Column(columnDefinition = "TEXT")
    private String photoUrl;

    private LocalDateTime issueSubmitDate = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('pending','assigned','resolved','closed') default 'pending'")
    private ComplaintStatus status = ComplaintStatus.PENDING;
    
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;


    
    public CustomerComplaint() {
		// TODO Auto-generated constructor stub
	}

	public CustomerComplaint(Long id, String division, String section, double fromKm, double toKm, String modelNo,
			String machineIssue, String photoUrl, LocalDateTime issueSubmitDate, ComplaintStatus status) {
		super();
		this.id = id;
		this.division = division;
		this.section = section;
		this.fromKm = fromKm;
		this.toKm = toKm;
		this.modelNo = modelNo;
		this.machineIssue = machineIssue;
		this.photoUrl = photoUrl;
		this.issueSubmitDate = issueSubmitDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public double getFromKm() {
		return fromKm;
	}

	public void setFromKm(double fromKm) {
		this.fromKm = fromKm;
	}

	public double getToKm() {
		return toKm;
	}

	public void setToKm(double toKm) {
		this.toKm = toKm;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getMachineIssue() {
		return machineIssue;
	}

	public void setMachineIssue(String machineIssue) {
		this.machineIssue = machineIssue;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public LocalDateTime getIssueSubmitDate() {
		return issueSubmitDate;
	}

	public void setIssueSubmitDate(LocalDateTime issueSubmitDate) {
		this.issueSubmitDate = issueSubmitDate;
	}

	public ComplaintStatus getStatus() {
		return status;
	}

	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}
    
    
    
    

	
    
}
