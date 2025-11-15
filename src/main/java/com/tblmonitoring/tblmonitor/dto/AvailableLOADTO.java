package com.tblmonitoring.tblmonitor.dto;

public class AvailableLOADTO {

	 private String poNumber;
	    private int totalQuantity;
	    private int remainingQuantity;
	    
	    
	    public AvailableLOADTO() {
			// TODO Auto-generated constructor stub
		}

		public AvailableLOADTO(String poNumber, int totalQuantity, int remainingQuantity) {
			super();
			this.poNumber = poNumber;
			this.totalQuantity = totalQuantity;
			this.remainingQuantity = remainingQuantity;
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
	    
	    
	    
	    
}
