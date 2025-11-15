package com.tblmonitoring.tblmonitor.dto;


public class LoginResponse {

	    private String status;
	    private String message;
	    private Long userId;
	    private String name;
	    private String role;
	    private String dashboardPath;
	    private boolean isProfileComplete;

	    public LoginResponse(String status, String message, Long userId, String name, String role, String dashboardPath, boolean isProfileComplete) {
	        this.status = status;
	        this.message = message;
	        this.userId = userId;
	        this.name = name;
	        this.role = role;
	        this.dashboardPath = dashboardPath;
	        this.isProfileComplete = isProfileComplete;
	    }

	    // Getters and Setters
	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

	    public String getDashboardPath() {
	        return dashboardPath;
	    }

	    public void setDashboardPath(String dashboardPath) {
	        this.dashboardPath = dashboardPath;
	    }

		public boolean isProfileComplete() {
			return isProfileComplete;
		}

		public void setProfileComplete(boolean isProfileComplete) {
			this.isProfileComplete = isProfileComplete;
		}
    
	    
}
