package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.MaintenanceFormDTO;
import com.tblmonitoring.tblmonitor.service.MachineInspectionService;
import com.tblmonitoring.tblmonitor.service.MachineService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/inspections")
public class MachineInspectionController {

	@Autowired
    private MachineInspectionService service;
	
	@Autowired
	private MachineService machineService;

    @GetMapping("/machine/{id}")
    public ResponseEntity<List<MaintenanceFormDTO>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(service.getMaintenanceHistory(id));
    }

//    @GetMapping("/upcoming")
//    public ResponseEntity<List<MaintenanceFormDTO>> upcoming() {
//        return ResponseEntity.ok(service.getMaintenancesDueInNextDays(10));
//    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<MaintenanceFormDTO>> upcoming() {
        List<MaintenanceFormDTO> list = service.getMaintenancesDueInNextDays(15);
        System.out.println("Controller returning " + list.size() + " machines");
        return ResponseEntity.ok(list);
    }

    
    
    @GetMapping("/maintenance/upcoming")
    public List<MaintenanceFormDTO> getUpcomingMaintenances() {
        int days = 3;  // fixed 5 days window
        return service.getMaintenancesDueInNextDays(days);
    }
    
    @PostMapping("/create")
    public ResponseEntity<MaintenanceFormDTO> create(@RequestBody MaintenanceFormDTO dto) {
        return ResponseEntity.ok(machineService.createInspection(dto));
    }

   


}
