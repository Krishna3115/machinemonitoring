package com.tblmonitoring.tblmonitor.dto;

public class PendingUserDTO {

	
	 	private Long id;
	    private String name;
	    private String mobileNumber;
	    private String activationCode;
	    
	    
	    public PendingUserDTO() {
	    	// TODO Auto-generated constructor stub
	    
	    }
	    
	    public PendingUserDTO(Long id, String name, String mobileNumber, String activationCode) {
	        this.id = id;
	        this.name = name;
	        this.mobileNumber = mobileNumber;
	        this.activationCode = activationCode;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getActivationCode() {
			return activationCode;
		}

		public void setActivationCode(String activationCode) {
			this.activationCode = activationCode;
		}
	    
	    

}
