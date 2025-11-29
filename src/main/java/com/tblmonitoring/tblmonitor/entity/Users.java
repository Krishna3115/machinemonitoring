package com.tblmonitoring.tblmonitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String name;
    
    @Column(name = "mobile_number", unique = true, nullable = false)
    private String mobileNumber;
    private String email;
    private String city;
    private String password;
    
    @Column(name = "role", nullable = false)
    private String role;      // NEW: role = "USER" or "ADMIN"
    
    @Column(name = "is_active")
    private boolean isActive = false; // NEW: default false
    
    @Column(name = "activation_code")
    private String activationCode;
    
    @Column(name = "address")
    private String address;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "id_proof_url")
    private String idProofUrl;
    
    @Column(name = "designation") // NEW
    private String designation;

    @Column(name = "is_profile_complete")
    private boolean isProfileComplete ;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;
    
    @Column(name = "is_blocked")
    private boolean isBlocked = false;  
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users reportedByUser;

    

    public Users() { 
    	
    }

    
	public Users(Long id, String name, String mobileNumber, String email, String city, String password, String role,
			boolean isActive, String activationCode, String address, String profilePhotoUrl, String idProofUrl,
			String designation, boolean isProfileComplete, String emergencyContactNumber, boolean isBlocked, Users reportedByUser) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.city = city;
		this.password = password;
		this.role = role;
		this.isActive = isActive;
		this.activationCode = activationCode;
		this.address = address;
		this.profilePhotoUrl = profilePhotoUrl;
		this.idProofUrl = idProofUrl;
		this.designation = designation;
		this.isProfileComplete = isProfileComplete;
		this.emergencyContactNumber = emergencyContactNumber;
		this.isBlocked = isBlocked;
		this.reportedByUser = reportedByUser;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
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

	public void setProfileComplete(boolean isProfileComplete) {
	    this.isProfileComplete = isProfileComplete;
	}

	public boolean isProfileComplete() {
	    return isProfileComplete;
	}

	public boolean isBlocked() {
	    return isBlocked;
	}

	public void setBlocked(boolean blocked) {
	    this.isBlocked = blocked;
	}


	public Users getReportedByUser() {
		return reportedByUser;
	}


	public void setReportedByUser(Users reportedByUser) {
		this.reportedByUser = reportedByUser;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
    
}
