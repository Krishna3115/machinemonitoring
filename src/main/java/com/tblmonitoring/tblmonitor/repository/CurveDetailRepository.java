package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.CurveDetail;

public interface CurveDetailRepository extends JpaRepository<CurveDetail, Long>{

	Optional<CurveDetail> findByPoNumberAndCurveNo(String poNumber, String curveNo);

	List<CurveDetail> findByPoNumber(String poNumber);
	
	void deleteByPoNumber(String poNumber);


}
