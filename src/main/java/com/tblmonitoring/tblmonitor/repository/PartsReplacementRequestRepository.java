package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.PartsReplacementRequest;

public interface PartsReplacementRequestRepository extends JpaRepository<PartsReplacementRequest, Long> {
	
    List<PartsReplacementRequest> findByReplacingTechnicianIdAndPartReceivedFalse(Long technicianId);
}