package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.ConfirmReplacedPartsRequestDTO;
import com.tblmonitoring.tblmonitor.dto.MarkPartReceivedRequestDTO;
import com.tblmonitoring.tblmonitor.dto.PartsReplacementRequestDTO;
import com.tblmonitoring.tblmonitor.service.PartsReplacementService;

@RestController
@RequestMapping("/api/parts-replacement")
public class PartsReplacementController {

	 private final PartsReplacementService service;

	    public PartsReplacementController(PartsReplacementService service) {
	        this.service = service;
	    }

	    @PostMapping("/create")
	    public ResponseEntity<PartsReplacementRequestDTO> create(@RequestBody PartsReplacementRequestDTO dto) {
	        PartsReplacementRequestDTO created = service.createRequest(dto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(created);
	    }

	    @GetMapping("/assigned-to-replacing-technician")
	    public ResponseEntity<List<PartsReplacementRequestDTO>> getAssignments(
	        @RequestParam(name = "technicianId") Long technicianId
	    ) {
	        List<PartsReplacementRequestDTO> list = service.getAssignmentsForReplacingTechnician(technicianId);
	        return ResponseEntity.ok(list);
	    }


	    @PostMapping("/mark-received")
	    public ResponseEntity<Void> markReceived(@RequestBody MarkPartReceivedRequestDTO req) {
	        service.markPartReceived(req.getRequestId());
	        return ResponseEntity.ok().build();
	    }

	    @PostMapping("/confirm")
	    public ResponseEntity<PartsReplacementRequestDTO> confirmReplacement(@RequestBody ConfirmReplacedPartsRequestDTO req) {
	        PartsReplacementRequestDTO updated = service.confirmReplacedParts(req);
	        return ResponseEntity.ok(updated);
	    }
	
	
	    @GetMapping("/{requestId}")
	    public ResponseEntity<PartsReplacementRequestDTO> getRequestById(@PathVariable Long requestId) {
	        PartsReplacementRequestDTO request = service.getRequestById(requestId);
	        if (request == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(request);
	    }

	
	
}
