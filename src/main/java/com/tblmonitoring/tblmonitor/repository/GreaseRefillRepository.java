package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.GreaseFillRecord;

public interface GreaseRefillRepository extends JpaRepository<GreaseFillRecord, Long> {
    List<GreaseFillRecord> findByModelNoOrderByFillDateAsc(String modelNo);
}
