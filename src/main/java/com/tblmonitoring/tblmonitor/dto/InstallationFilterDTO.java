package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

public class InstallationFilterDTO {

	  	private String modelNo;
	    private String division;
	    private String section;
	    private LocalDate fromDate;
	    private LocalDate toDate;

	    public InstallationFilterDTO() {
			// TODO Auto-generated constructor stub
		}

		public InstallationFilterDTO(String modelNo, String division, String section, LocalDate fromDate,
				LocalDate toDate) {
			super();
			this.modelNo = modelNo;
			this.division = division;
			this.section = section;
			this.fromDate = fromDate;
			this.toDate = toDate;
		}

		public String getModelNo() {
			return modelNo;
		}

		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
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

		public LocalDate getFromDate() {
			return fromDate;
		}

		public void setFromDate(LocalDate fromDate) {
			this.fromDate = fromDate;
		}

		public LocalDate getToDate() {
			return toDate;
		}

		public void setToDate(LocalDate toDate) {
			this.toDate = toDate;
		}
	    
	    
}
