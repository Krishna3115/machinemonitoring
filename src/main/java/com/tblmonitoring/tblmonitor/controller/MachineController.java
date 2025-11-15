 package com.tblmonitoring.tblmonitor.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.DispatchFormDTO;
import com.tblmonitoring.tblmonitor.dto.InstallationProgressDTO;
import com.tblmonitoring.tblmonitor.dto.MachineDivisionSectionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineLocationDTO;
import com.tblmonitoring.tblmonitor.dto.MachineWithInstallationDTO;
import com.tblmonitoring.tblmonitor.dto.SiteInspectionUpdateDTO;
import com.tblmonitoring.tblmonitor.dto.SitePendingInspectionDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.service.EmailService;
import com.tblmonitoring.tblmonitor.service.InstallationService;
import com.tblmonitoring.tblmonitor.service.MachineInspectionService;
import com.tblmonitoring.tblmonitor.service.MachineService;

import jakarta.mail.MessagingException;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/machines")
public class MachineController {

	@Autowired
    private MachineService machineService;

	@Autowired
	private MachineRepository machineRepository;
	
	@Autowired
	private InstallationService installationService;

	@Autowired
	private MachineInspectionService machineInspectionService;
	
	@Autowired
	private EmailService emailService;
	
	
	@PostMapping("/dispatch")
	public ResponseEntity<String> dispatchMachines(
	        @ModelAttribute DispatchFormDTO dispatchForm,
	        @RequestParam("pdiReports") List<MultipartFile> pdiReports) {

	    machineService.dispatchMachines(dispatchForm, pdiReports);
	    return ResponseEntity.ok("Machines dispatched successfully!");
	}
	
	
	
//	@PostMapping(value = "/dispatch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<String> dispatchMachines(@ModelAttribute DispatchFormDTO dispatchFormDTO) {
//	    List<Long> machineIds = dispatchFormDTO.getMachineIds();
//	    List<MultipartFile> pdiReports = dispatchFormDTO.getPdiReports();
//
//	    if (machineIds == null || pdiReports == null || machineIds.size() != pdiReports.size()) {
//	        return ResponseEntity.badRequest().body("Machine IDs and PDI reports count mismatch or missing.");
//	    }
//
//	    for (int i = 0; i < machineIds.size(); i++) {
//	        Long machineId = machineIds.get(i);
//	        MultipartFile pdiFile = pdiReports.get(i);
//
//	        // Build a single DispatchFormDTO for this machine
//	        DispatchFormDTO singleMachineDto = new DispatchFormDTO();
//	        singleMachineDto.setPurchaseOrderId(dispatchFormDTO.getPurchaseOrderId());
//	        singleMachineDto.setDispatchDate(dispatchFormDTO.getDispatchDate());
//	        singleMachineDto.setLocation(dispatchFormDTO.getLocation());
//	        singleMachineDto.setFinalInspectionDoneBy(dispatchFormDTO.getFinalInspectionDoneBy());
//	        singleMachineDto.setDivision(dispatchFormDTO.getDivision());
//	        singleMachineDto.setSection(dispatchFormDTO.getSection());
//	        singleMachineDto.setMachineIds(List.of(machineId));
//	        singleMachineDto.setPdiReports(List.of(pdiFile));
//
//	        // Call your service method to save this machine's dispatch record
//	        machineService.dispatchMachine(singleMachineDto);
//	    }
//
//	    return ResponseEntity.ok("Machines dispatched successfully");
//	}
    
//    public static class DeliveredDateUpdateRequest {
//        private String deliveredDate;
//
//        // Getter and Setter
//        public String getDeliveredDate() {
//            return deliveredDate;
//        }
//
//        public void setDeliveredDate(String deliveredDate) {
//            this.deliveredDate = deliveredDate;
//        }
//    }
//    
//    @PutMapping("/{machineId}/delivered-date")
//    public ResponseEntity<String> updateDeliveryDate(
//    		@PathVariable("machineId") Long machineId,
//            @RequestBody DeliveredDateUpdateRequest request) {
//
//        String result = machineService.updateDeliveryDate(machineId, request.getDeliveredDate());
//        return ResponseEntity.ok(result);
//    }
	
	
	@PutMapping(value = "/{machineId}/deliver", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updateDeliveryInfo(
	    @PathVariable("machineId") Long machineId,
	    @RequestParam(value = "deliveredDate", required = false) String deliveredDate,
	    @RequestParam(value = "receivingLetter", required = false) MultipartFile receivingLetter) throws IOException {
	    
	    String result = machineService.updateDeliveryDate(machineId, deliveredDate, receivingLetter);
	    
	    String subject = "Delivery Status Updated for Machine " + machineId;
	    String body = "The delivery status for machine ID " + machineId + " has been updated.\n" +
	                  "Delivered Date: " + deliveredDate + "\n" +
	                  (receivingLetter != null ? "Receiving letter has been uploaded." : "No receiving letter uploaded.");

	    try {
	        emailService.sendDeliveryStatusEmail(subject, body, receivingLetter);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
	    }
	    return ResponseEntity.ok(result);
	}

	
	@GetMapping("/pending-receiving-letter")
	public ResponseEntity<List<Machine>> getMachinesPendingReceivingLetter() {
	    List<Machine> machines = machineService.getAllMachines()
	        .stream()
	        .filter(m -> m.getDeliveredDate() != null && (m.getReceivingLetterUrl() == null || m.getReceivingLetterUrl().isEmpty()))
	        .collect(Collectors.toList());
	    return ResponseEntity.ok(machines);
	}


    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Machine>> getMachinesByStatus(@PathVariable("status") String status) {
        List<Machine> machines = machineService.getMachinesByStatus(status);
        return ResponseEntity.ok(machines);
    }
    
    
    @GetMapping("/status/{status}/with-installation")
    public ResponseEntity<List<MachineWithInstallationDTO>> getMachinesWithInstallationInfo(@PathVariable("status") String status) {
        return ResponseEntity.ok(machineService.getMachinesWithInstallationDate(status));
    }

    

    
    @GetMapping("/find-model")
    public ResponseEntity<List<String>> findModelNo(
        @RequestParam("division") String division,
        @RequestParam("section") String section,
        @RequestParam("kmFrom") String kmFrom,
        @RequestParam("kmTo") String kmTo) {

        List<String> modelNos = machineRepository.findModelNoByDivisionSectionAndKm(division, section, kmFrom, kmTo);
        return ResponseEntity.ok(modelNos);
    }

    
    @GetMapping("/{modelNo}/division-section")
    public ResponseEntity<MachineDivisionSectionDTO> getMachineDivisionSection(@PathVariable("modelNo") String modelNo) {
        MachineDivisionSectionDTO dto = machineService.getMachineDivisionSection(modelNo);
        return ResponseEntity.ok(dto);
    }
    
    @Autowired
    public MachineController(MachineInspectionService machineInspectionService) {
        this.machineInspectionService = machineInspectionService;
    }

    @GetMapping("/location/{modelNo}")
    public ResponseEntity<MachineLocationDTO> getMachineLocation(@PathVariable("modelNo") String modelNo) {
        try {
            MachineLocationDTO dto = machineInspectionService.getCombinedInfo(modelNo);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
     }

    
    @PutMapping("/{machineId}/site-inspection")
    public ResponseEntity<?> completeSiteInspection(
            @PathVariable("machineId") Long machineId,
            @RequestBody SiteInspectionUpdateDTO dto) {

        try {
            String result = machineService.updateSiteInspection(
                machineId,
                dto.getAction(),
                dto.getInspectionDate(),        // this will hold inspectionDate or reinspectionDate depending on action
                dto.getReinspectionRemark()
            );
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }




//    
//    @GetMapping("/site-inspection/pending")
//    public ResponseEntity<List<Machine>> getPendingSiteInspections() {
//        List<Machine> pendingMachines = machineService.getPendingSiteInspections();
//        return ResponseEntity.ok(pendingMachines);
//    }
    
    @GetMapping("/site-inspection/pending")
    public ResponseEntity<List<SitePendingInspectionDTO>> getPendingSiteInspections() {
        List<SitePendingInspectionDTO> pending = machineService.getPendingSiteInspectionsWithInstallation();
        return ResponseEntity.ok(pending);
    }


    @GetMapping("/installing")
    public ResponseEntity<List<InstallationProgressDTO>> getInProgressInstallations() {
        return ResponseEntity.ok(installationService.getInstallationInProgressList());
    }

    @PostMapping("/start-maintenance")
    public ResponseEntity<String> startMaintenance(
    		 @RequestParam(name = "modelNo") String modelNo,
    		 @RequestParam(name = "technicianId") Long technicianId
    ) {
        String result = machineInspectionService.startMaintenance(modelNo, technicianId);
        return ResponseEntity.ok(result);
    }
}
