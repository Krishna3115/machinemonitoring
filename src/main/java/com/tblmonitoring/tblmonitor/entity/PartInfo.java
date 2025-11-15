package com.tblmonitoring.tblmonitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "part_info")
public class PartInfo {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String partName;
	    private String oldPartNo;

	    private String replacedPartNo;
	    private String machineSerialNo;

	    @ManyToOne
	    @JoinColumn(name = "request_id")
	    private PartsReplacementRequest request;
	    
	    
	    public PartInfo() {
			// TODO Auto-generated constructor stub
		}


		public PartInfo(Long id, String partName, String oldPartNo, String replacedPartNo, String machineSerialNo,
				PartsReplacementRequest request) {
			super();
			this.id = id;
			this.partName = partName;
			this.oldPartNo = oldPartNo;
			this.replacedPartNo = replacedPartNo;
			this.machineSerialNo = machineSerialNo;
			this.request = request;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getPartName() {
			return partName;
		}


		public void setPartName(String partName) {
			this.partName = partName;
		}


		public String getOldPartNo() {
			return oldPartNo;
		}


		public void setOldPartNo(String oldPartNo) {
			this.oldPartNo = oldPartNo;
		}


		public String getReplacedPartNo() {
			return replacedPartNo;
		}


		public void setReplacedPartNo(String replacedPartNo) {
			this.replacedPartNo = replacedPartNo;
		}


		public String getMachineSerialNo() {
			return machineSerialNo;
		}


		public void setMachineSerialNo(String machineSerialNo) {
			this.machineSerialNo = machineSerialNo;
		}


		public PartsReplacementRequest getRequest() {
			return request;
		}


		public void setRequest(PartsReplacementRequest request) {
			this.request = request;
		}
	    
	    
}
