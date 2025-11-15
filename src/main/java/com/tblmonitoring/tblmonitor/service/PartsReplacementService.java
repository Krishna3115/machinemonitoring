package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.ConfirmReplacedPartsRequestDTO;
import com.tblmonitoring.tblmonitor.dto.PartsReplacementRequestDTO;

public interface PartsReplacementService {

	PartsReplacementRequestDTO createRequest(PartsReplacementRequestDTO dto);
    List<PartsReplacementRequestDTO> getAssignmentsForReplacingTechnician(Long techId);
    void markPartReceived(Long requestId);
    PartsReplacementRequestDTO confirmReplacedParts(ConfirmReplacedPartsRequestDTO req);
	PartsReplacementRequestDTO getRequestById(Long requestId);
    
}
