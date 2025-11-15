package com.tblmonitoring.tblmonitor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tblmonitoring.tblmonitor.entity.PartsReplacementRequest;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartInfoDTO {

	  	private Long id;
	  	
	  	@JsonProperty("part_name")
	  	private String partName;

	  	@JsonProperty("old_part_no")
	  	private String oldPartNo;

	  	@JsonProperty("replaced_part_no")
	  	private String replacedPartNo;

	  	@JsonProperty("machine_serial_no")
	  	private String machineSerialNo;
	    

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "request_id")
	    private PartsReplacementRequest request;
	  	
	    public PartInfoDTO() {
			// TODO Auto-generated constructor stub
		}

		public PartInfoDTO(Long id, String partName, String oldPartNo, String replacedPartNo, String machineSerialNo) {
			super();
			this.id = id;
			this.partName = partName;
			this.oldPartNo = oldPartNo;
			this.replacedPartNo = replacedPartNo;
			this.machineSerialNo = machineSerialNo;
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
