package com.tblmonitoring.tblmonitor.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.MachineProduction.MachineStatus;
import com.tblmonitoring.tblmonitor.repository.InspectionRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.service.MachineProductionService;
import com.tblmonitoring.tblmonitor.service.MachineQrService;
import com.tblmonitoring.tblmonitor.util.QRCodeGenerator;

@RestController
@RequestMapping("/api/machines-production")
public class MachineProductionController {

	@Autowired
	private MachineRepository machineRepo;

	@Autowired
	private InstallationRecordRepository installationRepo;

	@Autowired
	private InspectionRepository inspectionRepo;

	
	 @Autowired
	    private MachineProductionService service;

	 @Autowired
	 private MachineProductionRepository machineProductionRespository;
	 
	 @Autowired
	    private ObjectMapper objectMapper;  // Jackson mapper
	 	 
	 @Autowired
	 private MachineQrService qrService;
	 

	    @GetMapping("/{serialNo}")
	    public ResponseEntity<MachineQRDTO> getMachineData(@PathVariable String serialNo) {
	        MachineQRDTO dto = qrService.getMachineQRData(serialNo);
	        return ResponseEntity.ok(dto);
	    }
	 

	    @GetMapping("/{serialNo}/qrcode")
	    public ResponseEntity<byte[]> generateQRCode(@PathVariable("serialNo") String serialNo) throws Exception {
	        // QR will point to your domain login page or machine page
	        String domainLink = "https://cditbl.cloud/machine/" + serialNo;

	        BufferedImage qrImage = QRCodeGenerator.generateQRCodeImage(domainLink, 300, 300);

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(qrImage, "PNG", baos);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);

	        return ResponseEntity.ok().headers(headers).body(baos.toByteArray());
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
	            enumStatus = MachineStatus.valueOf(status.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
	        }

	        List<MachineProductionDTO> dtoList = service.findByStatus(enumStatus)
	            .stream()
	            .map(e -> {
	                MachineProductionDTO dto = new MachineProductionDTO();
	                dto.setId(e.getId());
	                dto.setMachineSerialNo(e.getMachineSerialNo());
	                dto.setJobCardNo(e.getJobCardNo());
	                dto.setMotorNo(e.getMotorNo());
	                dto.setSensorNo(e.getSensorNo());
	                dto.setApplicatorNo(e.getApplicatorNo());
	                dto.setBatteryNo(e.getBatteryNo());
	                dto.setSolarChargeControllerNo(e.getSolarChargeControllerNo());
	                dto.setSolarPanelNo1(e.getSolarPanelNo1());
	                dto.setSolarPanelNo2(e.getSolarPanelNo2());
	                dto.setCabinetNo(e.getCabinetNo());
	                dto.setBatchCounterNo(e.getBatchCounterNo());
	                dto.setMcbNo(e.getMcbNo());
	                dto.setGearPumpNo(e.getGearPumpNo());
	                dto.setQcFilePath(e.getQcFilePath());
	                dto.setQcInspectionDate(e.getQcInspectionDate());
	                dto.setCreatedAt(e.getCreatedAt());

	                // Map new subassembly fields
	                dto.setJunctionBoxBatchNo(e.getJunctionBoxBatchNo());
	                dto.setJunctionBoxBatchDate(e.getJunctionBoxBatchDate());
	                dto.setSensorAssyBatchNo(e.getSensorAssyBatchNo());
	                dto.setSensorAssyBatchDate(e.getSensorAssyBatchDate());
	                dto.setTmpAssyBatchNo(e.getTmpAssyBatchNo());
	                dto.setTmpAssyBatchDate(e.getTmpAssyBatchDate());
	                dto.setApplicatorAssyBatchNo(e.getApplicatorAssyBatchNo());
	                dto.setApplicatorAssyBatchDate(e.getApplicatorAssyBatchDate());

	                return dto;
	            })
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
