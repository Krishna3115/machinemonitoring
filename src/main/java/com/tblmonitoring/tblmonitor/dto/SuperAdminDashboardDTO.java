package com.tblmonitoring.tblmonitor.dto;

public class SuperAdminDashboardDTO{

	private long totalMachines;
    private long totalUsers;
    private long totalAdmins;
    private long pendingInspections;
    private long vandalismReports;
    

    // Constructors
    public SuperAdminDashboardDTO() {
    	
    }


	public SuperAdminDashboardDTO(long totalMachines, long totalUsers, long totalAdmins, long pendingInspections,
			long vandalismReports) {
		super();
		this.totalMachines = totalMachines;
		this.totalUsers = totalUsers;
		this.totalAdmins = totalAdmins;
		this.pendingInspections = pendingInspections;
		this.vandalismReports = vandalismReports;
	}


	public long getTotalMachines() {
		return totalMachines;
	}


	public void setTotalMachines(long totalMachines) {
		this.totalMachines = totalMachines;
	}


	public long getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}


	public long getTotalAdmins() {
		return totalAdmins;
	}


	public void setTotalAdmins(long totalAdmins) {
		this.totalAdmins = totalAdmins;
	}


	public long getPendingInspections() {
		return pendingInspections;
	}


	public void setPendingInspections(long pendingInspections) {
		this.pendingInspections = pendingInspections;
	}


	public long getVandalismReports() {
		return vandalismReports;
	}


	public void setVandalismReports(long vandalismReports) {
		this.vandalismReports = vandalismReports;
	}

    
    
}
