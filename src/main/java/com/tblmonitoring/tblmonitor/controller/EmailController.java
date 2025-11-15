package com.tblmonitoring.tblmonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.service.EmailService;


@RestController
@RequestMapping("/api/email")
public class EmailController {

	
	 @Autowired
	    private EmailService emailService;

	    @PostMapping("/send-delivery-status")
	    public ResponseEntity<String> sendDeliveryStatusEmail(
	            @RequestParam("subject") String subject,
	            @RequestParam("body") String body,
	            @RequestPart(value = "attachment", required = false) MultipartFile attachment) {
	        try {
	            emailService.sendDeliveryStatusEmail(subject, body, attachment);
	            return ResponseEntity.ok("Email sent successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
	        }
	    }
}
