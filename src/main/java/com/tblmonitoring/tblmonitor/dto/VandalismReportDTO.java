package com.tblmonitoring.tblmonitor.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class VandalismReportDTO {

	   	private Long inspectionId;
	    private String modelNo;
	    private String componentName;
	    private String issueDescription;
	    private String photoUrl;
	    
	    
	    private Boolean isDamaged;
	    
	    private List<String> damageTypes;
	    
	    private List<String> photoUrls;
	    private Long reportedByUserId;
	    private String claimStatus; 
	    
	    public VandalismReportDTO() {
			// TODO Auto-generated constructor stub
		}

		public VandalismReportDTO(Long inspectionId, String modelNo, String componentName, String issueDescription,
				String photoUrl, Long reportedByUserId,Boolean isDamaged, String claimStatus, List<String> photoUrls,
				List<String> damageTypes) {
			super();
			this.inspectionId = inspectionId;
			this.modelNo = modelNo;
			this.componentName = componentName;
			this.issueDescription = issueDescription;
			this.photoUrl = photoUrl;
			this.reportedByUserId = reportedByUserId;
			this.claimStatus = claimStatus;
			this.photoUrls = photoUrls;
			this.isDamaged = isDamaged;
			this.damageTypes = damageTypes;
		}

		public Long getInspectionId() {
			return inspectionId;
		}

		public void setInspectionId(Long inspectionId) {
			this.inspectionId = inspectionId;
		}

		public String getModelNo() {
			return modelNo;
		}

		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
		}

		public String getComponentName() {
			return componentName;
		}

		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}

		public String getIssueDescription() {
			return issueDescription;
		}

		public void setIssueDescription(String issueDescription) {
			this.issueDescription = issueDescription;
		}

		public String getPhotoUrl() {
			return photoUrl;
		}

		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}

		public Long getReportedByUserId() {
			return reportedByUserId;
		}

		public void setReportedByUserId(Long reportedByUserId) {
			this.reportedByUserId = reportedByUserId;
		}

		public String getClaimStatus() {
			return claimStatus;
		}

		public void setClaimStatus(String claimStatus) {
			this.claimStatus = claimStatus;
		}

		public List<String> getPhotoUrls() {
			return photoUrls;
		}

		public void setPhotoUrls(List<String> photoUrls) {
			this.photoUrls = photoUrls;
		}
		
		public Boolean getIsDamaged() {
		    return isDamaged;
		}

		public void setIsDamaged(Boolean isDamaged) {
		    this.isDamaged = isDamaged;
		}

		public List<String> getDamageTypes() {
			return damageTypes;
		}

		public void setDamageTypes(List<String> damageTypes) {
			this.damageTypes = damageTypes;
		}
		
		
		
}
