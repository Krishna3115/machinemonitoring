package com.tblmonitoring.tblmonitor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.GreaseRefillDTO;
import com.tblmonitoring.tblmonitor.entity.GreaseFillRecord;
import com.tblmonitoring.tblmonitor.service.GreaseRefillService;

@RestController
@RequestMapping("/api/grease-refill")
public class GreaseRefillController {

	private final GreaseRefillService service;

    @Autowired
    public GreaseRefillController(GreaseRefillService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody GreaseRefillDTO dto) {
        GreaseFillRecord saved = service.submitGreaseRefill(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Report submitted",
            "recordId", saved.getId()
        ));
    }
}
