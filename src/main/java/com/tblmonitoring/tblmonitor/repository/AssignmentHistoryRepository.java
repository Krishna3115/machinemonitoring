package com.tblmonitoring.tblmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tblmonitoring.tblmonitor.entity.AssignmentHistory;

public interface AssignmentHistoryRepository extends JpaRepository<AssignmentHistory, Long> {
    // You can add custom queries if needed later
}
