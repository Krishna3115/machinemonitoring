package com.tblmonitoring.tblmonitor.service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.CustomerComplaintDTO;
import com.tblmonitoring.tblmonitor.entity.CustomerComplaint;
import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;
import com.tblmonitoring.tblmonitor.repository.CustomerComplaintRepository;

@Service
public class CustomerComplaintServiceImpl implements CustomerComplaintService{

	private final CustomerComplaintRepository repository;
	
	@Autowired
	private MachineService machineService;


    public CustomerComplaintServiceImpl(CustomerComplaintRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerComplaint createComplaint(CustomerComplaintDTO dto) {
        CustomerComplaint complaint = new CustomerComplaint();
        complaint.setDivision(dto.getDivision());
        complaint.setSection(dto.getSection());
        complaint.setFromKm(dto.getFromKm());
        complaint.setToKm(dto.getToKm());
        complaint.setModelNo(dto.getModelNo());
        complaint.setMachineIssue(dto.getMachineIssue());
        complaint.setPhotoUrl(dto.getPhotoUrl());
        return repository.save(complaint);
    
	}
    
    @Override
    public Map<String, Long> getComplaintStatusCounts() {
        Map<String, Long> result = new HashMap<>();
        for (ComplaintStatus status : ComplaintStatus.values()) {
            result.put(status.name(), repository.countByStatus(status));
        }
        return result;
    }

    @Override
    public List<CustomerComplaint> getComplaintsByStatus(ComplaintStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public long getPendingComplaintCount() {
        return repository.countByStatus(ComplaintStatus.PENDING);
    }

}
