package com.tblmonitoring.tblmonitor.dto;

public class MachineLocationDTO {

	private String section;
    private String division;
    private String poleNo;
    private String fromKm;
    private String toKm;
    
	public MachineLocationDTO() {
		// TODO Auto-generated constructor stub
	}

	public MachineLocationDTO(String section, String division, String poleNo, String fromKm, String toKm) {
		super();
		this.section = section;
		this.division = division;
		this.poleNo = poleNo;
		this.fromKm = fromKm;
		this.toKm = toKm;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPoleNo() {
		return poleNo;
	}

	public void setPoleNo(String poleNo) {
		this.poleNo = poleNo;
	}

	public String getFromKm() {
		return fromKm;
	}

	public void setFromKm(String fromKm) {
		this.fromKm = fromKm;
	}

	public String getToKm() {
		return toKm;
	}

	public void setToKm(String toKm) {
		this.toKm = toKm;
	}


	
}
