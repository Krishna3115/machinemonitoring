package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

public class GreaseRefillDTO {

	    private String modelNo;
	    private Double remainingGreaseKg;
	    private String remainingGreasePhoto;
	    private Double filledGreaseKg;
	    private String filledGreasePhoto;
	    private Boolean isFullTank;
	    private Long submittedBy;
	    private LocalDate fillDate = LocalDate.now();
	    private String remarks;
	    
	    
	    public GreaseRefillDTO() {
			// TODO Auto-generated constructor stub
		}


		public GreaseRefillDTO(String modelNo, Double remainingGreaseKg, String remainingGreasePhoto,
				Double filledGreaseKg, String filledGreasePhoto, Boolean isFullTank, Long submittedBy,
				LocalDate fillDate, String remarks) {
			super();
			this.modelNo = modelNo;
			this.remainingGreaseKg = remainingGreaseKg;
			this.remainingGreasePhoto = remainingGreasePhoto;
			this.filledGreaseKg = filledGreaseKg;
			this.filledGreasePhoto = filledGreasePhoto;
			this.isFullTank = isFullTank;
			this.submittedBy = submittedBy;
			this.fillDate = fillDate;
			this.remarks = remarks;
		}


		public String getModelNo() {
			return modelNo;
		}


		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
		}


		public Double getRemainingGreaseKg() {
			return remainingGreaseKg;
		}


		public void setRemainingGreaseKg(Double remainingGreaseKg) {
			this.remainingGreaseKg = remainingGreaseKg;
		}


		public String getRemainingGreasePhoto() {
			return remainingGreasePhoto;
		}


		public void setRemainingGreasePhoto(String remainingGreasePhoto) {
			this.remainingGreasePhoto = remainingGreasePhoto;
		}


		public Double getFilledGreaseKg() {
			return filledGreaseKg;
		}


		public void setFilledGreaseKg(Double filledGreaseKg) {
			this.filledGreaseKg = filledGreaseKg;
		}


		public String getFilledGreasePhoto() {
			return filledGreasePhoto;
		}


		public void setFilledGreasePhoto(String filledGreasePhoto) {
			this.filledGreasePhoto = filledGreasePhoto;
		}


		public Boolean getIsFullTank() {
			return isFullTank;
		}


		public void setIsFullTank(Boolean isFullTank) {
			this.isFullTank = isFullTank;
		}


		public Long getSubmittedBy() {
			return submittedBy;
		}


		public void setSubmittedBy(Long submittedBy) {
			this.submittedBy = submittedBy;
		}


		public LocalDate getFillDate() {
			return fillDate;
		}


		public void setFillDate(LocalDate fillDate) {
			this.fillDate = fillDate;
		}


		public String getRemarks() {
			return remarks;
		}


		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	    
		
	    
}
