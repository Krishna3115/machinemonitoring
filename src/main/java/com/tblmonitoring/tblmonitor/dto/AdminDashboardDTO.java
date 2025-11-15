package com.tblmonitoring.tblmonitor.dto;

import java.util.List;

public class AdminDashboardDTO {

	private long totalMachines;
    private long totalInstallations;
    private long submittedInspections;
    private long pendingInspections;
    private long totalTechnicians;
    
    private List<PendingUserDTO> pendingUsers;

    
    public AdminDashboardDTO() {
		// TODO Auto-generated constructor stub
	}
    
    
    public AdminDashboardDTO(long totalMachines, long totalInstallations, long submittedInspections,
                             long pendingInspections, long totalTechnicians, List<PendingUserDTO> pendingUsers) {
        this.totalMachines = totalMachines;
        this.totalInstallations = totalInstallations;
        this.submittedInspections = submittedInspections;
        this.pendingInspections = pendingInspections;
        this.totalTechnicians = totalTechnicians;
        this.pendingUsers = pendingUsers;
    }


	public long getTotalMachines() {
		return totalMachines;
	}


	public void setTotalMachines(long totalMachines) {
		this.totalMachines = totalMachines;
	}


	public long getTotalInstallations() {
		return totalInstallations;
	}


	public void setTotalInstallations(long totalInstallations) {
		this.totalInstallations = totalInstallations;
	}


	public long getSubmittedInspections() {
		return submittedInspections;
	}


	public void setSubmittedInspections(long submittedInspections) {
		this.submittedInspections = submittedInspections;
	}


	public long getPendingInspections() {
		return pendingInspections;
	}


	public void setPendingInspections(long pendingInspections) {
		this.pendingInspections = pendingInspections;
	}


	public long getTotalTechnicians() {
		return totalTechnicians;
	}


	public void setTotalTechnicians(long totalTechnicians) {
		this.totalTechnicians = totalTechnicians;
	}


	public List<PendingUserDTO> getPendingUsers() {
		return pendingUsers;
	}


	public void setPendingUsers(List<PendingUserDTO> pendingUsers) {
		this.pendingUsers = pendingUsers;
	}
    
}
