package com.tblmonitoring.tblmonitor.dto;

public class MachineDTO {

	private String modelNo;
    private String section;
    private String status;
    
    public MachineDTO() {
		// TODO Auto-generated constructor stub
	}

	public MachineDTO(String modelNo, String section, String status) {
		super();
		this.modelNo = modelNo;
		this.section = section;
		this.status = status;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
