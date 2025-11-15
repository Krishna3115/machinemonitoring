package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "machine_installation")
public class MachineInstallation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_number")
    private String modelNumber;

    @ManyToOne
    @JoinColumn(name = "installed_by_id")
    private Users installedBy;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "location")
    private String location;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "machine_photo_url")
    private String machinePhotoUrl;

    // Constructors
    public MachineInstallation() {
    	
    }

    public MachineInstallation(String modelNumber, Users installedBy, LocalDate installationDate, String location, String remarks, String machinePhotoUrl) {
        this.modelNumber = modelNumber;
        this.installedBy = installedBy;
        this.installationDate = installationDate;
        this.location = location;
        this.remarks = remarks;
        this.machinePhotoUrl = machinePhotoUrl;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public Users getInstalledBy() {
		return installedBy;
	}

	public void setInstalledBy(Users installedBy) {
		this.installedBy = installedBy;
	}

	public LocalDate getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(LocalDate installationDate) {
		this.installationDate = installationDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMachinePhotoUrl() {
		return machinePhotoUrl;
	}

	public void setMachinePhotoUrl(String machinePhotoUrl) {
		this.machinePhotoUrl = machinePhotoUrl;
	}
    
    
}
