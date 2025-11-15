package com.tblmonitoring.tblmonitor.dto;

public class PendingPODTO {

	 private Long id;
	    private String poNumber;
	    private int quantity;
	    private int dispatchedCount;

	    public PendingPODTO(Long id, String poNumber, int quantity, int dispatchedCount) {
	        this.id = id;
	        this.poNumber = poNumber;
	        this.quantity = quantity;
	        this.dispatchedCount = dispatchedCount;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getPoNumber() {
	        return poNumber;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public int getDispatchedCount() {
	        return dispatchedCount;
	    }

	    public int getRemaining() {
	        return quantity - dispatchedCount;
	    }
	    
}
