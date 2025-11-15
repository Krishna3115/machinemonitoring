package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.Map;

import com.tblmonitoring.tblmonitor.dto.CustomerComplaintDTO;
import com.tblmonitoring.tblmonitor.entity.CustomerComplaint;
import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;

public interface CustomerComplaintService {

	CustomerComplaint createComplaint(CustomerComplaintDTO dto);
	Map<String, Long> getComplaintStatusCounts();
    List<CustomerComplaint> getComplaintsByStatus(ComplaintStatus status);
    long getPendingComplaintCount();
}
