package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parts_replacement_request")
public class PartsReplacementRequest {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String machineNo;
	    private LocalDateTime datetime;
	    private String reason;
	    private String dispatchMethod;  // e.g. "With Technician", "Via Courier"
	    private String courierName;
	    private String trackingNumber;
	    private String courierContact;

	    // The technician assigned by admin to bring the part
	    private Long technicianAssignedId;

	    // The technician who will do the replacement (which you call replacing technician)
	    private Long replacingTechnicianId;

	    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<PartInfo> parts;  // a child entity for part_name + part_no etc.

	    private boolean partReceived;  // whether technician has marked the part received
	    private LocalDateTime partReceivedAt;

	    private boolean completed;  // whether replacement is completed
	    private LocalDateTime completedAt;
	    
	    private String completionRemarks;

	    public PartsReplacementRequest() {
			// TODO Auto-generated constructor stub
		}

		public PartsReplacementRequest(Long id, String machineNo, LocalDateTime datetime, String reason,
				String dispatchMethod, String courierName, String trackingNumber, String courierContact,
				Long technicianAssignedId, Long replacingTechnicianId, List<PartInfo> parts, boolean partReceived,
				LocalDateTime partReceivedAt, boolean completed, LocalDateTime completedAt, String completionRemarks) {
			super();
			this.id = id;
			this.machineNo = machineNo;
			this.datetime = datetime;
			this.reason = reason;
			this.dispatchMethod = dispatchMethod;
			this.courierName = courierName;
			this.trackingNumber = trackingNumber;
			this.courierContact = courierContact;
			this.technicianAssignedId = technicianAssignedId;
			this.replacingTechnicianId = replacingTechnicianId;
			this.parts = parts;
			this.partReceived = partReceived;
			this.partReceivedAt = partReceivedAt;
			this.completed = completed;
			this.completedAt = completedAt;
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

		public List<PartInfo> getParts() {
			return parts;
		}

		public void setParts(List<PartInfo> parts) {
			this.parts = parts;
		}

		public boolean isPartReceived() {
			return partReceived;
		}

		public void setPartReceived(boolean partReceived) {
			this.partReceived = partReceived;
		}

		public LocalDateTime getPartReceivedAt() {
			return partReceivedAt;
		}

		public void setPartReceivedAt(LocalDateTime partReceivedAt) {
			this.partReceivedAt = partReceivedAt;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

		public LocalDateTime getCompletedAt() {
			return completedAt;
		}

		public void setCompletedAt(LocalDateTime completedAt) {
			this.completedAt = completedAt;
		}

		public String getCompletionRemarks() {
			return completionRemarks;
		}

		public void setCompletionRemarks(String completionRemarks) {
			this.completionRemarks = completionRemarks;
		}

		
	
		
		
	    
}
