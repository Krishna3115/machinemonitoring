package com.tblmonitoring.tblmonitor.dto;

public class MachineDivisionSectionDTO {

	private String division;
    private String section;

    public MachineDivisionSectionDTO() {
		// TODO Auto-generated constructor stub
	}

	public MachineDivisionSectionDTO(String division, String section) {
		super();
		this.division = division;
		this.section = section;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
    
    
}
