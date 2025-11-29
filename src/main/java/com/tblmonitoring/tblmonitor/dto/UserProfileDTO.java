package com.tblmonitoring.tblmonitor.dto;

public class UserProfileDTO {

	private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String city;
    private String address;
    private String role;
    private String profilePhotoUrl;
    private String idProofUrl;
    private String emergencyContactNumber;
    private String designation;
    
    public UserProfileDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserProfileDTO(Long id, String name, String email, String mobileNumber, String city, String address,
			String role, String profilePhotoUrl, String idProofUrl, String emergencyContactNumber, String designation) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.city = city;
		this.address = address;
		this.role = role;
		this.profilePhotoUrl = profilePhotoUrl;
		this.idProofUrl = idProofUrl;
		this.emergencyContactNumber = emergencyContactNumber;
		this.designation = designation;
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
	
		public String getEmail() {
			return email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getMobileNumber() {
			return mobileNumber;
		}
	
		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
	
		public String getCity() {
			return city;
		}
	
		public void setCity(String city) {
			this.city = city;
		}
	
		public String getAddress() {
			return address;
		}
	
		public void setAddress(String address) {
			this.address = address;
		}
	
		public String getRole() {
			return role;
		}
	
		public void setRole(String role) {
			this.role = role;
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

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}
    
		
    
		
    
}
