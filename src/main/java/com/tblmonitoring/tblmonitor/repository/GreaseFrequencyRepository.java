package com.tblmonitoring.tblmonitor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tblmonitoring.tblmonitor.entity.GreaseFrequency;

@Repository
public interface GreaseFrequencyRepository extends JpaRepository<GreaseFrequency, Long> {

	Optional<GreaseFrequency> findByModelNo(String modelNo);
}
