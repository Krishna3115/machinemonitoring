package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.ClaimPartEntity;

public interface ClaimPartRepository extends JpaRepository<ClaimPartEntity, Long> {
	
    List<ClaimPartEntity> findByInsuranceClaimId(Long insuranceClaimId);

}