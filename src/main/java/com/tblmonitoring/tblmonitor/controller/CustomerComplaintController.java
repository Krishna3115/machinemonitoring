package com.tblmonitoring.tblmonitor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.CustomerComplaintDTO;
import com.tblmonitoring.tblmonitor.entity.CustomerComplaint;
import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;
import com.tblmonitoring.tblmonitor.repository.CustomerComplaintRepository;
import com.tblmonitoring.tblmonitor.service.CustomerComplaintService;


@RestController
@RequestMapping("/api/complaints")
//@CrossOrigin(origins = "*")
public class CustomerComplaintController {

	@Autowired
	 private final CustomerComplaintService service;
	
	@Autowired
	CustomerComplaintRepository complaintRepository;

	    public CustomerComplaintController(CustomerComplaintService service) {
	        this.service = service;
	    }

	    @PostMapping("/create")
	    public ResponseEntity<CustomerComplaint> createComplaint(@RequestBody CustomerComplaintDTO dto) {
	        CustomerComplaint saved = service.createComplaint(dto);
	        return ResponseEntity.ok(saved);
	    }
	 
	    @GetMapping("/by-status")
	    public ResponseEntity<List<CustomerComplaint>> getComplaintsByStatus(@RequestParam("status") String status) {
	        try {
	            ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status.trim().toUpperCase()); // ✅ case-insensitive
	            List<CustomerComplaint> complaints = service.getComplaintsByStatus(complaintStatus);
	            return ResponseEntity.ok(complaints);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().build(); // ❌ Invalid status
	        }
	    }

	    @GetMapping("/status/{status}")
	    public ResponseEntity<List<CustomerComplaint>> getByStatusPath(@PathVariable String status) {
	        try {
	            ComplaintStatus complaintStatus = ComplaintStatus.valueOf(status.trim().toUpperCase());
	            return ResponseEntity.ok(service.getComplaintsByStatus(complaintStatus));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }

	    @GetMapping("/counts")
	    public ResponseEntity<Map<String, Long>> getComplaintCounts() {
	        return ResponseEntity.ok(service.getComplaintStatusCounts());
	    }

	    @GetMapping("/pending/count")
	    public ResponseEntity<Long> getPendingComplaintCount() {
	        long count = service.getPendingComplaintCount();
	        return ResponseEntity.ok(count);
	    }

	    @GetMapping("/pending")
	    public ResponseEntity<List<CustomerComplaint>> getPendingComplaintList() {
	        return ResponseEntity.ok(service.getComplaintsByStatus(ComplaintStatus.PENDING));
	    }
}
