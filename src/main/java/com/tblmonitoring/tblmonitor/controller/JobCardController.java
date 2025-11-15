package com.tblmonitoring.tblmonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tblmonitoring.tblmonitor.dto.InProgressJobCardDTO;
import com.tblmonitoring.tblmonitor.dto.JobCardDTO;
import com.tblmonitoring.tblmonitor.dto.PendingMachineSerielDTO;
import com.tblmonitoring.tblmonitor.entity.JobCard;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;
import com.tblmonitoring.tblmonitor.service.JobCardService;

@RestController
@RequestMapping("/api/job-cards")
public class JobCardController {

	@Autowired
    private JobCardService jobCardService;
	
	@Autowired
	private MachineProductionRepository machineProductionRepository;

    @PostMapping
    public ResponseEntity<JobCard> createJobCard(@RequestBody JobCardDTO dto) {
        JobCard saved = jobCardService.createJobCard(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobCard>> getAll() {
        return ResponseEntity.ok(jobCardService.getAllJobCards());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<JobCard> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobCardService.getJobCardById(id));
    }

	
//    @GetMapping("/in-progress")
//    public ResponseEntity<List<JobCard>> getInProgressJobCards() {
//        List<JobCard> inProgress = jobCardService.getInProgressJobCards();
//        return ResponseEntity.ok(inProgress);
//    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<JobCard>> getInProgressJobCards() {
        return ResponseEntity.ok(jobCardService.getInProgressJobCards());
    }

    @GetMapping("/in-progress/count")
    public ResponseEntity<Integer> countInProgressJobCards() {
        return ResponseEntity.ok(jobCardService.countInProgressJobCards());
    }

    @GetMapping("/in-progress/details")
    public ResponseEntity<List<InProgressJobCardDTO>> getInProgressDetails() {
        return ResponseEntity.ok(jobCardService.getInProgressJobCardDetails());
    }

    @GetMapping("/by-job-card-number/{jobCardNumber}")
    public ResponseEntity<JobCard> getByJobCardNumber(@PathVariable String jobCardNumber) {
        JobCard jobCard = jobCardService.getJobCardByNumber(jobCardNumber);
        if (jobCard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobCard);
    }


    @GetMapping("/{jobCardNo}/pending-serials")
    public ResponseEntity<PendingMachineSerielDTO> getPendingSerials(@PathVariable("jobCardNo") String jobCardNo) {
        JobCard jobCard = jobCardService.getJobCardByNumber(jobCardNo);
        if (jobCard == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> allSerials = jobCard.getMachineSerialNumbers();

        List<String> produced = machineProductionRepository.findByJobCardNo(jobCardNo)
            .stream()
            .map(MachineProduction::getMachineSerialNo)
            .toList();

        List<String> pending = allSerials.stream()
            .filter(sn -> !produced.contains(sn))
            .toList();

        PendingMachineSerielDTO dto = new PendingMachineSerielDTO(jobCardNo, pending);
        return ResponseEntity.ok(dto);
    }

    
}
