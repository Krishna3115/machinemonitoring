package com.tblmonitoring.tblmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.PartInfo;

public interface PartInfoRepository extends JpaRepository<PartInfo, Long> {
    // maybe custom queries if needed

}
