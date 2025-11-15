package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class DispatchReportDTO {

	 	private Long id;
	    private String modelNo;
	    private String machineName;
	    private String status;
	    private LocalDateTime dispatchDate;
	    private LocalDateTime deliveredDate;
	    private String division;
	    private String section;
	    private String poNumber;
	    private String finalInspectionDoneBy;
	    
	     
	    public DispatchReportDTO() {
			// TODO Auto-generated constructor stub
		}

		public DispatchReportDTO(Long id, String modelNo, String machineName, String status, LocalDateTime dispatchDate,
				LocalDateTime deliveredDate, String division, String section, String poNumber, String finalInspectionDoneBy) {
			super();
			this.id = id;
			this.modelNo = modelNo;
			this.machineName = machineName;
			this.status = status;
			this.dispatchDate = dispatchDate;
			this.deliveredDate = deliveredDate;
			this.division = division;
			this.section = section;
			this.poNumber = poNumber;
			this.finalInspectionDoneBy = finalInspectionDoneBy;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public LocalDateTime getDispatchDate() {
			return dispatchDate;
		}

		public void setDispatchDate(LocalDateTime dispatchDate) {
			this.dispatchDate = dispatchDate;
		}

		public LocalDateTime getDeliveredDate() {
			return deliveredDate;
		}

		public void setDeliveredDate(LocalDateTime deliveredDate) {
			this.deliveredDate = deliveredDate;
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

		public String getPoNumber() {
			return poNumber;
		}

		public void setPoNumber(String poNumber) {
			this.poNumber = poNumber;
		}
	    
		public String getFinalInspectionDoneBy() {
			return finalInspectionDoneBy;
		}

		public void setFinalInspectionDoneBy(String finalInspectionDoneBy) {
			this.finalInspectionDoneBy = finalInspectionDoneBy;
		}
	    
}
