package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {

	 	private String name;
	 	
	 	@JsonProperty("mobileNumber")
	    private String mobileNumber;
	    private String email;
	    private String city;
	    private String password;
	    private String designation;
	    
		public RegisterRequest() {
			// TODO Auto-generated constructor stub
		}

		public RegisterRequest(String name, String mobileNumber, String email, String city, String password,
				String designation) {
			super();
			this.name = name;
			this.mobileNumber = mobileNumber;
			this.email = email;
			this.city = city;
			this.password = password;
			this.designation = designation;
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}
		
		
	    
	    
}
