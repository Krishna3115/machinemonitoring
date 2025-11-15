package com.tblmonitoring.tblmonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.GreaseFrequencyDTO;
import com.tblmonitoring.tblmonitor.service.GreaseFrequencyService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/grease-frequency")
@CrossOrigin
public class GreaseFrequencyController {

	@Autowired
    private GreaseFrequencyService service;

    @PostMapping("/add")
    public GreaseFrequencyDTO addOrUpdate(@RequestBody GreaseFrequencyDTO dto, HttpServletRequest request) {
        String updatedBy = request.getHeader("username"); // or extract from token
        if (updatedBy == null || updatedBy.isBlank()) {
            updatedBy = "admin"; // fallback
        }
        return service.saveOrUpdate(dto, updatedBy);
    }
}
