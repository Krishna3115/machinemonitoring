package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "machines")
public class Machine {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "model_no", unique = true, nullable = false)
    private String modelNo;
    private String machineName;
    private String finalInspectionDoneBy;
    private String location;
    private LocalDateTime dispatchDate;
    
    @Column(name = "division")
    private String division;

    @Column(name = "section")
    private String section;

    private LocalDateTime deliveredDate;
    private String motorNo;
    private String sensorNo;
    private String applicatorNo;
    
    private String batteryNo;
    
    @Column(name = "solar_charge_controller_no")
    private String solarChargeControllerNo;

    @Column(name = "solar_panel_no")
    private String solarPanelNo;
    
    @Column (name = "solar_panel_no1")
    private String solarPanelNo1;

    @Column(name = "cabinet_no")
    private String cabinetNo;
    
    private String status = "PENDING";
    private Boolean technicianAssigned = false;
    
    @Column(name = "site_final_inspection_pending")
    private Boolean siteFinalInspectionPending;
    
    @Column(name = "site_final_inspection_date")
    private LocalDateTime siteFinalInspectionDate;
    
    @Column(name = "installation_technician_id")
    private Long installationTechnicianId;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;
    
    @Column(name = "warranty_months")
    private Integer warrantyMonths;

    @Column(name = "warranty_end_date")
    private LocalDateTime warrantyEndDate;

    @Column(name = "inspection_status")
    private String inspectionStatus;

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    @Column(name = "reinspection_decided_date")
    private LocalDateTime reinspectionDecidedDate;

    @Column(name = "reinspection_remark")
    private String reinspectionRemark;
    
    private String pdiFileUrl;
    
    @Column(name = "receiving_letter_url")
    private String receivingLetterUrl;

    
	    public Machine() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Machine [id=" + id + ", modelNo=" + modelNo + ", machineName=" + machineName
					+ ", finalInspectionDoneBy=" + finalInspectionDoneBy + ", location=" + location + ", dispatchDate="
					+ dispatchDate + ", division=" + division + ", section=" + section + ", deliveredDate="
					+ deliveredDate + ", motorNo=" + motorNo + ", sensorNo=" + sensorNo + ", applicatorNo="
					+ applicatorNo + ", batteryNo=" + batteryNo + ", solarChargeControllerNo=" + solarChargeControllerNo
					+ ", solarPanelNo=" + solarPanelNo + ", solarPanelNo1=" + solarPanelNo1 + ", cabinetNo=" + cabinetNo
					+ ", status=" + status + ", technicianAssigned=" + technicianAssigned
					+ ", siteFinalInspectionPending=" + siteFinalInspectionPending + ", siteFinalInspectionDate="
					+ siteFinalInspectionDate + ", installationTechnicianId=" + installationTechnicianId
					+ ", purchaseOrder=" + purchaseOrder + ", warrantyMonths=" + warrantyMonths + ", warrantyEndDate="
					+ warrantyEndDate + ", inspectionStatus=" + inspectionStatus + ", inspectionDate=" + inspectionDate
					+ ", reinspectionDecidedDate=" + reinspectionDecidedDate + ", reinspectionRemark="
					+ reinspectionRemark + ", pdiFileUrl=" + pdiFileUrl + "]";
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getModelNo() {
			return modelNo;
		}

		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
		}

		public String getMachineName() {
			return machineName;
		}

		public void setMachineName(String machineName) {
			this.machineName = machineName;
		}

		public String getFinalInspectionDoneBy() {
			return finalInspectionDoneBy;
		}

		public void setFinalInspectionDoneBy(String finalInspectionDoneBy) {
			this.finalInspectionDoneBy = finalInspectionDoneBy;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public LocalDateTime getDispatchDate() {
			return dispatchDate;
		}

		public void setDispatchDate(LocalDateTime dispatchDate) {
			this.dispatchDate = dispatchDate;
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

		public LocalDateTime getDeliveredDate() {
			return deliveredDate;
		}

		public void setDeliveredDate(LocalDateTime deliveredDate) {
			this.deliveredDate = deliveredDate;
		}

		public String getMotorNo() {
			return motorNo;
		}

		public void setMotorNo(String motorNo) {
			this.motorNo = motorNo;
		}

		public String getSensorNo() {
			return sensorNo;
		}

		public void setSensorNo(String sensorNo) {
			this.sensorNo = sensorNo;
		}

		public String getApplicatorNo() {
			return applicatorNo;
		}

		public void setApplicatorNo(String applicatorNo) {
			this.applicatorNo = applicatorNo;
		}

		public String getBatteryNo() {
			return batteryNo;
		}

		public void setBatteryNo(String batteryNo) {
			this.batteryNo = batteryNo;
		}

		public String getSolarChargeControllerNo() {
			return solarChargeControllerNo;
		}

		public void setSolarChargeControllerNo(String solarChargeControllerNo) {
			this.solarChargeControllerNo = solarChargeControllerNo;
		}

		public String getSolarPanelNo() {
			return solarPanelNo;
		}

		public void setSolarPanelNo(String solarPanelNo) {
			this.solarPanelNo = solarPanelNo;
		}

		public String getSolarPanelNo1() {
			return solarPanelNo1;
		}

		public void setSolarPanelNo1(String solarPanelNo1) {
			this.solarPanelNo1 = solarPanelNo1;
		}

		public String getCabinetNo() {
			return cabinetNo;
		}

		public void setCabinetNo(String cabinetNo) {
			this.cabinetNo = cabinetNo;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Boolean getTechnicianAssigned() {
			return technicianAssigned;
		}

		public void setTechnicianAssigned(Boolean technicianAssigned) {
			this.technicianAssigned = technicianAssigned;
		}

		public Boolean getSiteFinalInspectionPending() {
			return siteFinalInspectionPending;
		}

		public void setSiteFinalInspectionPending(Boolean siteFinalInspectionPending) {
			this.siteFinalInspectionPending = siteFinalInspectionPending;
		}

		public LocalDateTime getSiteFinalInspectionDate() {
			return siteFinalInspectionDate;
		}

		public void setSiteFinalInspectionDate(LocalDateTime siteFinalInspectionDate) {
			this.siteFinalInspectionDate = siteFinalInspectionDate;
		}

		public Long getInstallationTechnicianId() {
			return installationTechnicianId;
		}

		public void setInstallationTechnicianId(Long installationTechnicianId) {
			this.installationTechnicianId = installationTechnicianId;
		}

		public PurchaseOrder getPurchaseOrder() {
			return purchaseOrder;
		}

		public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
			this.purchaseOrder = purchaseOrder;
		}

		public Integer getWarrantyMonths() {
			return warrantyMonths;
		}

		public void setWarrantyMonths(Integer warrantyMonths) {
			this.warrantyMonths = warrantyMonths;
		}

		public LocalDateTime getWarrantyEndDate() {
			return warrantyEndDate;
		}

		public void setWarrantyEndDate(LocalDateTime warrantyEndDate) {
			this.warrantyEndDate = warrantyEndDate;
		}

		public String getInspectionStatus() {
			return inspectionStatus;
		}

		public void setInspectionStatus(String inspectionStatus) {
			this.inspectionStatus = inspectionStatus;
		}

		public LocalDateTime getInspectionDate() {
			return inspectionDate;
		}

		public void setInspectionDate(LocalDateTime inspectionDate) {
			this.inspectionDate = inspectionDate;
		}

		public LocalDateTime getReinspectionDecidedDate() {
			return reinspectionDecidedDate;
		}

		public void setReinspectionDecidedDate(LocalDateTime reinspectionDecidedDate) {
			this.reinspectionDecidedDate = reinspectionDecidedDate;
		}

		public String getReinspectionRemark() {
			return reinspectionRemark;
		}

		public void setReinspectionRemark(String reinspectionRemark) {
			this.reinspectionRemark = reinspectionRemark;
		}

		public String getPdiFileUrl() {
			return pdiFileUrl;
		}

		public void setPdiFileUrl(String pdiFileUrl) {
			this.pdiFileUrl = pdiFileUrl;
		}
		
		public String getReceivingLetterUrl() {
		    return receivingLetterUrl;
		}

		public void setReceivingLetterUrl(String receivingLetterUrl) {
		    this.receivingLetterUrl = receivingLetterUrl;
		}

}	



		