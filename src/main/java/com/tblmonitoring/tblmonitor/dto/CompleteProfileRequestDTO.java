package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompleteProfileRequestDTO {

	private String address;
	@JsonProperty("profilePhotoUrl")
    private String profilePhotoUrl;
	@JsonProperty("idProofUrl")
    private String idProofUrl;
    @JsonProperty("emergencyContactNumber")
    private String emergencyContactNumber;
    
    public CompleteProfileRequestDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompleteProfileRequestDTO(String address, String profilePhotoUrl, String idProofUrl,
			String emergencyContactNumber) {
		super();
		this.address = address;
		this.profilePhotoUrl = profilePhotoUrl;
		this.idProofUrl = idProofUrl;
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}

	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}

	public String getIdProofUrl() {
		return idProofUrl;
	}

	public void setIdProofUrl(String idProofUrl) {
		this.idProofUrl = idProofUrl;
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}
    
    
}
