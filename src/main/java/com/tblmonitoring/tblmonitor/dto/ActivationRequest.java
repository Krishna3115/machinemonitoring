package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivationRequest {

	@JsonProperty("mobileNumber")
	 private String mobileNumber;
	    private String code;
	    
	    
		public String getMobileNumber() {
			return mobileNumber;
		}
		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	    
	    
}
