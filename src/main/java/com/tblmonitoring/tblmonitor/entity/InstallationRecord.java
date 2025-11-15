package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "installation_records")
public class InstallationRecord {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "machine_id", nullable = false)
	    private Machine machine;
	    
	    @Column(name = "model_no") 
	    private String modelNo;
	    private LocalDateTime installationStarted;
	    private LocalDateTime installationEnded;
	    private String section;
	    private String curveNo;
	    private String poleNo;
	    private String fromKm;
	    private String toKm;
	    private String rhLhRadius;
	    private String srDen;
	    private String lineSection;
	    private String pwi;
	    private String machineStatus;
	    @Column(name = "grease_level")
	    private String greaseLevel;
	    private String greaseLevelPhotoUrl;
	    private Integer wheelCount;
	    private Integer timeCount;
	    private String remarks;
	    @Column(name = "grease_level_kg")
	    private Double greaseLevelKg;


	    @Column(name = "installation_technician_id")
	    private Long installationTechnicianId;
	    
	    

	    
	    public InstallationRecord() {
			// TODO Auto-generated constructor stub
		}

		public InstallationRecord(Long id, Machine machine, LocalDateTime installationStarted,
				LocalDateTime installationEnded, String section, String curveNo, String poleNo, String fromKm,
				String toKm, String rhLhRadius, String srDen, String lineSection, String pwi, String machineStatus,
				String greaseLevel, String greaseLevelPhotoUrl, Integer wheelCount, Integer timeCount, String remarks,
				Long installationTechnicianId, Double greaseLevelKg) {
			super();
			this.id = id;
			this.machine = machine;
			this.installationStarted = installationStarted;
			this.installationEnded = installationEnded;
			this.section = section;
			this.curveNo = curveNo;
			this.poleNo = poleNo;
			this.fromKm = fromKm;
			this.toKm = toKm;
			this.rhLhRadius = rhLhRadius;
			this.srDen = srDen;
			this.lineSection = lineSection;
			this.pwi = pwi;
			this.machineStatus = machineStatus;
			this.greaseLevel = greaseLevel;
			this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
			this.wheelCount = wheelCount;
			this.timeCount = timeCount;
			this.remarks = remarks;
			this.installationTechnicianId = installationTechnicianId;
			this.greaseLevelKg = greaseLevelKg;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Machine getMachine() {
			return machine;
		}

		public void setMachine(Machine machine) {
			this.machine = machine;
		}

		public LocalDateTime getInstallationStarted() {
			return installationStarted;
		}

		public void setInstallationStarted(LocalDateTime installationStarted) {
			this.installationStarted = installationStarted;
		}

		public LocalDateTime getInstallationEnded() {
			return installationEnded;
		}

		public void setInstallationEnded(LocalDateTime installationEnded) {
			this.installationEnded = installationEnded;
		}

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public String getCurveNo() {
			return curveNo;
		}

		public void setCurveNo(String curveNo) {
			this.curveNo = curveNo;
		}

		public String getPoleNo() {
			return poleNo;
		}

		public void setPoleNo(String poleNo) {
			this.poleNo = poleNo;
		}

		public String getFromKm() {
			return fromKm;
		}

		public void setFromKm(String fromKm) {
			this.fromKm = fromKm;
		}

		public String getToKm() {
			return toKm;
		}

		public void setToKm(String toKm) {
			this.toKm = toKm;
		}

		public String getRhLhRadius() {
			return rhLhRadius;
		}

		public void setRhLhRadius(String rhLhRadius) {
			this.rhLhRadius = rhLhRadius;
		}

		public String getSrDen() {
			return srDen;
		}

		public void setSrDen(String srDen) {
			this.srDen = srDen;
		}

		public String getLineSection() {
			return lineSection;
		}

		public void setLineSection(String lineSection) {
			this.lineSection = lineSection;
		}

		public String getPwi() {
			return pwi;
		}

		public void setPwi(String pwi) {
			this.pwi = pwi;
		}

		public String getMachineStatus() {
			return machineStatus;
		}

		public void setMachineStatus(String machineStatus) {
			this.machineStatus = machineStatus;
		}

		public String getGreaseLevel() {
			return greaseLevel;
		}

		public void setGreaseLevel(String greaseLevel) {
			this.greaseLevel = greaseLevel;
		}

		public String getGreaseLevelPhotoUrl() {
			return greaseLevelPhotoUrl;
		}

		public void setGreaseLevelPhotoUrl(String greaseLevelPhotoUrl) {
			this.greaseLevelPhotoUrl = greaseLevelPhotoUrl;
		}

		public Integer getWheelCount() {
			return wheelCount;
		}

		public void setWheelCount(Integer wheelCount) {
			this.wheelCount = wheelCount;
		}

		public Integer getTimeCount() {
			return timeCount;
		}

		public void setTimeCount(Integer timeCount) {
			this.timeCount = timeCount;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	    
		public String getModelNo() {
		    return modelNo;
		}

		public void setModelNo(String modelNo) {
		    this.modelNo = modelNo;
		}

		public Long getInstallationTechnicianId() {
			return installationTechnicianId;
		}

		public void setInstallationTechnicianId(Long installationTechnicianId) {
			this.installationTechnicianId = installationTechnicianId;
		}

		public Double getGreaseLevelKg() {
			return greaseLevelKg;
		}

		public void setGreaseLevelKg(Double greaseLevelKg) {
			this.greaseLevelKg = greaseLevelKg;
		}
		

}
