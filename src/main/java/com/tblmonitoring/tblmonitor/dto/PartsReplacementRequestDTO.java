package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartsReplacementRequestDTO {

	  private Long id;
	  
	  	@JsonProperty("machine_no")
	  	private String machineNo;
	  	
	  	@JsonProperty("datetime")
	  	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	    private LocalDateTime datetime;	  	
	    private String reason;
	    
	    @JsonProperty("dispatch_method")
	    private String dispatchMethod;
	    
	    @JsonProperty("technician_assigned_id")
	    private Long technicianAssignedId;
	    
	    @JsonProperty("replacing_technician_id")
	    private Long replacingTechnicianId;
	    
	    
	    private boolean partReceived;
	    private boolean completed;
	    
	    @JsonProperty("courier_name")
	    private String courierName;
	    

	    @JsonProperty("tracking_number")
	    private String trackingNumber;
	    

	    @JsonProperty("courier_contact")
	    private String courierContact;
	    private List<PartInfoDTO> parts;
	    private String completionRemarks;
	    
	    
	    public PartsReplacementRequestDTO() {
			// TODO Auto-generated constructor stub
		}


		public PartsReplacementRequestDTO(Long id, String machineNo, LocalDateTime datetime, String reason,
				String dispatchMethod, Long technicianAssignedId, Long replacingTechnicianId, boolean partReceived,
				boolean completed, String courierName, String trackingNumber, String courierContact,
				List<PartInfoDTO> parts, String completionRemarks) {
			super();
			this.id = id;
			this.machineNo = machineNo;
			this.datetime = datetime;
			this.reason = reason;
			this.dispatchMethod = dispatchMethod;
			this.technicianAssignedId = technicianAssignedId;
			this.replacingTechnicianId = replacingTechnicianId;
			this.partReceived = partReceived;
			this.completed = completed;
			this.courierName = courierName;
			this.trackingNumber = trackingNumber;
			this.courierContact = courierContact;
			this.parts = parts;
			this.completionRemarks = completionRemarks;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getMachineNo() {
			return machineNo;
		}


		public void setMachineNo(String machineNo) {
			this.machineNo = machineNo;
		}


		public LocalDateTime getDatetime() {
			return datetime;
		}


		public void setDatetime(LocalDateTime datetime) {
			this.datetime = datetime;
		}


		public String getReason() {
			return reason;
		}


		public void setReason(String reason) {
			this.reason = reason;
		}


		public String getDispatchMethod() {
			return dispatchMethod;
		}


		public void setDispatchMethod(String dispatchMethod) {
			this.dispatchMethod = dispatchMethod;
		}


		public Long getTechnicianAssignedId() {
			return technicianAssignedId;
		}


		public void setTechnicianAssignedId(Long technicianAssignedId) {
			this.technicianAssignedId = technicianAssignedId;
		}


		public Long getReplacingTechnicianId() {
			return replacingTechnicianId;
		}


		public void setReplacingTechnicianId(Long replacingTechnicianId) {
			this.replacingTechnicianId = replacingTechnicianId;
		}


		public boolean isPartReceived() {
			return partReceived;
		}


		public void setPartReceived(boolean partReceived) {
			this.partReceived = partReceived;
		}


		public boolean isCompleted() {
			return completed;
		}


		public void setCompleted(boolean completed) {
			this.completed = completed;
		}


		public String getCourierName() {
			return courierName;
		}


		public void setCourierName(String courierName) {
			this.courierName = courierName;
		}


		public String getTrackingNumber() {
			return trackingNumber;
		}


		public void setTrackingNumber(String trackingNumber) {
			this.trackingNumber = trackingNumber;
		}


		public String getCourierContact() {
			return courierContact;
		}


		public void setCourierContact(String courierContact) {
			this.courierContact = courierContact;
		}


		public List<PartInfoDTO> getParts() {
			return parts;
		}


		public void setParts(List<PartInfoDTO> parts) {
			this.parts = parts;
		}


		public String getCompletionRemarks() {
			return completionRemarks;
		}


		public void setCompletionRemarks(String completionRemarks) {
			this.completionRemarks = completionRemarks;
		}
	      
	    
}
