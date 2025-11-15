package com.tblmonitoring.tblmonitor.dto;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DispatchFormDTO {

	  private Long purchaseOrderId;
	    private OffsetDateTime  dispatchDate;
	    private String location;
	    private String finalInspectionDoneBy;
	    private String division;
	    private String section;

	    private List<String> selectedModelNos;

	    private List<MultipartFile> pdiReports;

	    
    public DispatchFormDTO() {
		// TODO Auto-generated constructor stub
	}


	public DispatchFormDTO(Long purchaseOrderId, OffsetDateTime  dispatchDate, String location,
			String finalInspectionDoneBy, String division, String section, List<String> selectedModelNos,
			List<MultipartFile> pdiReports) {
		super();
		this.purchaseOrderId = purchaseOrderId;
		this.dispatchDate = dispatchDate;
		this.location = location;
		this.finalInspectionDoneBy = finalInspectionDoneBy;
		this.division = division;
		this.section = section;
		this.selectedModelNos = selectedModelNos;
		this.pdiReports = pdiReports;
	}


	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}


	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}


	public OffsetDateTime  getDispatchDate() {
		return dispatchDate;
	}


	public void setDispatchDate(OffsetDateTime  dispatchDate) {
		this.dispatchDate = dispatchDate;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getFinalInspectionDoneBy() {
		return finalInspectionDoneBy;
	}


	public void setFinalInspectionDoneBy(String finalInspectionDoneBy) {
		this.finalInspectionDoneBy = finalInspectionDoneBy;
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


	public List<String> getSelectedModelNos() {
		return selectedModelNos;
	}


	public void setSelectedModelNos(List<String> selectedModelNos) {
		this.selectedModelNos = selectedModelNos;
	}


	public List<MultipartFile> getPdiReports() {
		return pdiReports;
	}


	public void setPdiReports(List<MultipartFile> pdiReports) {
		this.pdiReports = pdiReports;
	}
	
	
    
    
				
}