package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.tblmonitoring.tblmonitor.entity.Machine;

import lombok.Data;


public class DispatchFilterDTO {

	private String poNumber;
    private String division;
    private String section;
    private LocalDate dispatchDate;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean exportAll; // Add getter and setter
    
    private List<DispatchReportDTO> rows;

    

    
    public DispatchFilterDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public DispatchFilterDTO(String poNumber, String division, String section, LocalDate dispatchDate,
			LocalDate fromDate, LocalDate toDate, boolean exportAll, List<DispatchReportDTO> rows) {
		super();
		this.poNumber = poNumber;
		this.division = division;
		this.section = section;
		this.dispatchDate = dispatchDate;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.exportAll = exportAll;
		this.rows = rows;
	}



	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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

	public LocalDate getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(LocalDate dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
    
	public LocalDate getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	
	public LocalDate getToDate() {
		return toDate;
	}
	
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public boolean isExportAll() {
		return exportAll;
	}

	public void setExportAll(boolean exportAll) {
		this.exportAll = exportAll;
	}



	public List<DispatchReportDTO> getRows() {
		return rows;
	}



	public void setRows(List<DispatchReportDTO> rows) {
		this.rows = rows;
	}
    
	
	
}
