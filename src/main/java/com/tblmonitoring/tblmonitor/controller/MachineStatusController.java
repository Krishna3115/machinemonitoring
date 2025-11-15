package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.MachineStatusDTO;
import com.tblmonitoring.tblmonitor.service.MachineStatusService;

@RestController
@RequestMapping("/api/machines/status")
public class MachineStatusController {

	@Autowired
    private MachineStatusService machineStatusService;

    @GetMapping("/{modelNo}/status")
    public MachineStatusDTO status(@PathVariable("modelNo") String modelNo) {
        return machineStatusService.getMachineStatus(modelNo);
    }
    
    @GetMapping("/machine-status/under-maintenance-count")
    public ResponseEntity<Long> getUnderMaintenanceMachineCount() {
        long count = machineStatusService.countMachinesUnderMaintenance();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/under-maintenance-list")
    public ResponseEntity<List<MachineStatusDTO>> getUnderMaintenanceMachineList() {
        List<MachineStatusDTO> list = machineStatusService.getAllUnderMaintenanceMachines();
        return ResponseEntity.ok(list);
    }
    

    @GetMapping("/active-machines")
    public ResponseEntity<List<MachineStatusDTO>> getActiveMachines() {
        List<MachineStatusDTO> activeMachines = machineStatusService.getActiveMachines();
        return ResponseEntity.ok(activeMachines);
    }

    
}
