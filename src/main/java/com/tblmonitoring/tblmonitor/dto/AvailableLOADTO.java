package com.tblmonitoring.tblmonitor.dto;

public class AvailableLOADTO {

	 private String poNumber;
	    private int totalQuantity;
	    private int remainingQuantity;
	    
	    private String division;
	    private String section;
	    private String finalDispatchDate; // or String if you want formatted

	    
	    public AvailableLOADTO() {
			// TODO Auto-generated constructor stub
		}


		public AvailableLOADTO(String poNumber, int totalQuantity, int remainingQuantity, String division,
				String section, String finalDispatchDate) {
			super();
			this.poNumber = poNumber;
			this.totalQuantity = totalQuantity;
			this.remainingQuantity = remainingQuantity;
			this.division = division;
			this.section = section;
			this.finalDispatchDate = finalDispatchDate;
		}


		public String getPoNumber() {
			return poNumber;
		}


		public void setPoNumber(String poNumber) {
			this.poNumber = poNumber;
		}


		public int getTotalQuantity() {
			return totalQuantity;
		}


		public void setTotalQuantity(int totalQuantity) {
			this.totalQuantity = totalQuantity;
		}


		public int getRemainingQuantity() {
			return remainingQuantity;
		}


		public void setRemainingQuantity(int remainingQuantity) {
			this.remainingQuantity = remainingQuantity;
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


		public String getFinalDispatchDate() {
			return finalDispatchDate;
		}


		public void setFinalDispatchDate(String finalDispatchDate) {
			this.finalDispatchDate = finalDispatchDate;
		}

		
	    
	    
	    
}