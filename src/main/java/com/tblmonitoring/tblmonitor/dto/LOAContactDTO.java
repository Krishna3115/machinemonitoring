package com.tblmonitoring.tblmonitor.dto;

import java.util.List;

public class LOAContactDTO {

	 private String poNumber;
	    private String division;
	    private String section;
	    private List<ConsigneeContactDTO> consigneeContactList;

	    public static class ConsigneeContactDTO {
	        private String name;
	        private String designation;
	        private String mobile;

	        // Constructors
	        public ConsigneeContactDTO() {}

	        public ConsigneeContactDTO(String name, String designation, String mobile) {
	            this.name = name;
	            this.designation = designation;
	            this.mobile = mobile;
	        }

	        // Getters and setters
	        public String getName() { return name; }
	        public void setName(String name) { this.name = name; }

	        public String getDesignation() { return designation; }
	        public void setDesignation(String designation) { this.designation = designation; }

	        public String getMobile() { return mobile; }
	        public void setMobile(String mobile) { this.mobile = mobile; }
	    }
	    
	    public LOAContactDTO() {
			// TODO Auto-generated constructor stub
		}


		public LOAContactDTO(String poNumber, String division, String section,
				List<ConsigneeContactDTO> consigneeContactList) {
			super();
			this.poNumber = poNumber;
			this.division = division;
			this.section = section;
			this.consigneeContactList = consigneeContactList;
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


		public List<ConsigneeContactDTO> getConsigneeContactList() {
			return consigneeContactList;
		}


		public void setConsigneeContactList(List<ConsigneeContactDTO> consigneeContactList) {
			this.consigneeContactList = consigneeContactList;
		}
	    
	    
}
