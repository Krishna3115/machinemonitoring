package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.InsuranceClaimEntity;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaimEntity, Long> {
	
    //Optional<InsuranceClaimEntity> findByVandalismReportId(Long vandalismReportId);
    
    List<InsuranceClaimEntity> findByVandalismReportId(Long vandalismReportId);
    List<InsuranceClaimEntity> findByStatus(String status);

}
