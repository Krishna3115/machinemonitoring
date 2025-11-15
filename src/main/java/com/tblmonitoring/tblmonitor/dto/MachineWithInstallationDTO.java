package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;

public class MachineWithInstallationDTO {

	 	private Long id;
	    private String modelNo;
	    private String machineName;
	    private String division;
	    private String section;
	    private String status;
	    private LocalDateTime deliveredDate;
	    private LocalDateTime installationDate;
	    private LocalDateTime warrantyEndDate;
	    
	    public MachineWithInstallationDTO() {
			// TODO Auto-generated constructor stub
		}

		public MachineWithInstallationDTO(Long id, String modelNo, String machineName, String division, String section,
				String status, LocalDateTime deliveredDate, LocalDateTime installationDate, LocalDateTime warrantyEndDate) {
			super();
			this.id = id;
			this.modelNo = modelNo;
			this.machineName = machineName;
			this.division = division;
			this.section = section;
			this.status = status;
			this.deliveredDate = deliveredDate;
			this.installationDate = installationDate;
			this.warrantyEndDate = warrantyEndDate;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getModelNo() {
			return modelNo;
		}

		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
		}

		public String getMachineName() {
			return machineName;
		}

		public void setMachineName(String machineName) {
			this.machineName = machineName;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public LocalDateTime getDeliveredDate() {
			return deliveredDate;
		}

		public void setDeliveredDate(LocalDateTime deliveredDate) {
			this.deliveredDate = deliveredDate;
		}

		public LocalDateTime getInstallationDate() {
			return installationDate;
		}

		public void setInstallationDate(LocalDateTime installationDate) {
			this.installationDate = installationDate;
		}
		
		public LocalDateTime getWarrantyEndDate() {
			return warrantyEndDate;
		}

		public void setWarrantyEndDate(LocalDateTime warrantyEndDate) {
			this.warrantyEndDate = warrantyEndDate;
		}
	    
	    
	    
}
