package com.tblmonitoring.tblmonitor.dto;

public class MarkPartReceivedRequestDTO {

	 private Long requestId;
	 
	 public MarkPartReceivedRequestDTO() {
		// TODO Auto-generated constructor stub
	}

	 public MarkPartReceivedRequestDTO(Long requestId) {
		super();
		this.requestId = requestId;
	 }

	 public Long getRequestId() {
		 return requestId;
	 }

	 public void setRequestId(Long requestId) {
		 this.requestId = requestId;
	 }
	 
	 
	 
}
