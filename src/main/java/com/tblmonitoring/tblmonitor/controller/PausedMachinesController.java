package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.MachineDTO;
import com.tblmonitoring.tblmonitor.service.PausedMachineService;

@RestController
@RequestMapping("/api/admin/paused")
public class PausedMachinesController {

	private final PausedMachineService pausedService;

    @Autowired
    public PausedMachinesController(PausedMachineService pausedService) {
        this.pausedService = pausedService;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(pausedService.countPausedMachines());
    }

    @GetMapping("/list")
    public ResponseEntity<List<MachineDTO>> getList() {
        return ResponseEntity.ok(pausedService.getPausedMachines());
    }
}
