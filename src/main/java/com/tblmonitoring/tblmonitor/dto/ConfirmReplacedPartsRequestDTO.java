package com.tblmonitoring.tblmonitor.dto;

import java.util.List;

public class ConfirmReplacedPartsRequestDTO {

	private Long requestId;
    private List<PartInfoDTO> replacedParts;
    
    public ConfirmReplacedPartsRequestDTO() {
		// TODO Auto-generated constructor stub
	}

	public ConfirmReplacedPartsRequestDTO(Long requestId, List<PartInfoDTO> replacedParts) {
		super();
		this.requestId = requestId;
		this.replacedParts = replacedParts;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public List<PartInfoDTO> getReplacedParts() {
		return replacedParts;
	}

	public void setReplacedParts(List<PartInfoDTO> replacedParts) {
		this.replacedParts = replacedParts;
	}
    
    
	
}
