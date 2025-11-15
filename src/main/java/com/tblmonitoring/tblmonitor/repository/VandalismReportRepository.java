package com.tblmonitoring.tblmonitor.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.entity.VandalismReport;

public interface VandalismReportRepository extends JpaRepository<VandalismReport, Long> {

	@Query("SELECT MAX(v.reportedAtDateTime) FROM VandalismReport v WHERE v.modelNo = :modelNo")
	Date findLatestVandalismDateByModelNo(@Param("modelNo") String modelNo);

}
