package com.tblmonitoring.tblmonitor.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tblmonitoring.tblmonitor.dto.MachineProductionDTO;
import com.tblmonitoring.tblmonitor.dto.MachineQRDTO;
import com.tblmonitoring.tblmonitor.dto.MachineStatusSummaryDTO;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.MachineProduction.MachineStatus;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;
import com.tblmonitoring.tblmonitor.service.MachineProductionService;
import com.tblmonitoring.tblmonitor.util.QRCodeGenerator;

@RestController
@RequestMapping("/api/machines-production")
public class MachineProductionController {

	 @Autowired
	    private MachineProductionService service;

	 @Autowired
	 private MachineProductionRepository machineProductionRespository;
	 
	 @Autowired
	    private ObjectMapper objectMapper;  // Jackson mapper

	    @GetMapping("/{serialNo}/qrcode")
	    public ResponseEntity<byte[]> generateQRCode(@PathVariable("serialNo") String serialNo) throws Exception {
	    	 List<MachineProduction> productions = machineProductionRespository.findByMachineSerialNo(serialNo);
	    	    if (productions == null || productions.isEmpty()) {
	    	        throw new RuntimeException("MachineProduction not found");
	    	    }

	    	    // Use the first production in the list or implement your own selection logic
	    	    MachineProduction production = productions.get(0);
	        // Prepare DTO to serialize to JSON
	        MachineQRDTO dto = new MachineQRDTO();
	        dto.setMachineSerialNo(production.getMachineSerialNo());
	        dto.setJobCardNo(production.getJobCardNo());
	        dto.setMotorNo(production.getMotorNo());
	        dto.setSensorNo(production.getSensorNo());
	        dto.setApplicatorNo(production.getApplicatorNo());
	        dto.setBatteryNo(production.getBatteryNo());
	        dto.setSolarChargeControllerNo(production.getSolarChargeControllerNo());
	        dto.setSolarPanelNo1(production.getSolarPanelNo1());
	        dto.setSolarPanelNo2(production.getSolarPanelNo2());
	        dto.setCabinetNo(production.getCabinetNo());
	        dto.setBatchCounterNo(production.getBatchCounterNo());
	        dto.setMcbNo(production.getMcbNo());
	        dto.setGearPumpNo(production.getGearPumpNo());
	        dto.setFinalQCDate(production.getQcInspectionDate());

	        // Convert DTO to JSON string
	        String json = objectMapper.writeValueAsString(dto);

	        // Generate QR Code image
	        BufferedImage qrImage = QRCodeGenerator.generateQRCodeImage(json, 300, 300);

	        // Convert BufferedImage to byte[]
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(qrImage, "PNG", baos);
	        byte[] imageBytes = baos.toByteArray();

	        // Return as PNG image
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);

	        return ResponseEntity.ok().headers(headers).body(imageBytes);
	    }
	 
	 
	    @PostMapping("/create")
	    public MachineProductionDTO createMachine(@RequestBody MachineProductionDTO dto) {
	        return service.createMachine(dto);
	    }

	    @GetMapping("/available")
	    public List<MachineProductionDTO> getAvailableMachines() {
	        return service.getAvailableMachines();
	    }

	    @PostMapping("/dispatch/{id}")
	    public void markAsDispatched(@PathVariable Long id) {
	        service.markAsDispatched(id);
	    }
	    
	    
	 // MachineProductionController.java

	    @GetMapping("/available/count")
	    public long getAvailableMachineCount() {
	        return service.countAvailableMachines();
	    }

	    @GetMapping("/production/pending-quality-check")
	    public List<MachineProductionDTO> getPendingForQualityCheck() {
	        return service.getMachinesPendingForQualityCheck();
	    }

	    @GetMapping("/status/{status}")
	    public ResponseEntity<List<MachineProductionDTO>> getByStatus(@PathVariable("status") String status) {
	        MachineStatus enumStatus;
	        try {
	            enumStatus = MachineStatus.valueOf(status.toUpperCase()); // ✅ Convert to uppercase
	        } catch (IllegalArgumentException e) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
	        }

	        List<MachineProductionDTO> dtoList = service.findByStatus(enumStatus) 
	            .stream()
	            .map(e -> new MachineProductionDTO(
	            	    e.getId(),
	            	    e.getMachineSerialNo(),
	            	    e.getJobCardNo(),
	            	    e.getMotorNo(),
	            	    e.getSensorNo(),
	            	    e.getApplicatorNo(),
	            	    e.getBatteryNo(),
	            	    e.getSolarChargeControllerNo(),
	            	    e.getSolarPanelNo1(),
	            	    e.getSolarPanelNo2(),
	            	    e.getCabinetNo(),
	            	    e.getBatchCounterNo(),
                	    e.getMcbNo(),
                	    e.getGearPumpNo(),
	            	    e.getQcFilePath(),
	            	    e.getQcInspectionDate(),
	            	    e.getCreatedAt() 
	            	))
	            .collect(Collectors.toList());

	        return ResponseEntity.ok(dtoList);
	    }

	    
	    @PostMapping("/qc-complete")
	    public void completeQualityCheck(
	        @RequestParam("machineIds") String machineIds,
	        @RequestParam("qcFile") MultipartFile qcFile
	    ) throws IOException {
	        List<Long> ids = Arrays.stream(machineIds.replaceAll("[\\[\\]\\s]", "").split(","))
	                               .map(Long::parseLong)
	                               .collect(Collectors.toList());

	        service.completeQualityCheck(ids, qcFile);
	    }


	    @GetMapping("/count/ready-to-dispatch")
	    public long getReadyToDispatchCount() {
	        return service.countReadyToDispatchMachines();
	    }
	    
	    @GetMapping("/ready-to-dispatch")
	    public List<MachineStatusSummaryDTO> getReadyToDispatchMachines() {
	        return service.getReadyToDispatchMachines();
	    }


}
