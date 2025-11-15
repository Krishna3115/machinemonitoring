package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.GreaseFrequencyDTO;
import com.tblmonitoring.tblmonitor.entity.GreaseFrequency;
import com.tblmonitoring.tblmonitor.repository.GreaseFrequencyRepository;

@Service
public class GreaseFrequencyServiceImpl implements GreaseFrequencyService {

	@Autowired
    private GreaseFrequencyRepository repository;

    @Override
    public GreaseFrequencyDTO saveOrUpdate(GreaseFrequencyDTO dto, String updatedBy) {
        GreaseFrequency frequency = repository.findByModelNo(dto.getModelNo())
                .orElse(new GreaseFrequency());

        frequency.setModelNo(dto.getModelNo());
        frequency.setWheelsPerDay(dto.getWheelsPerDay());
        frequency.setGreaseReleaseRateGmPerSec(dto.getGreaseReleaseRate());
        frequency.setUpdatedBy(updatedBy);
        frequency.setUpdatedAt(LocalDateTime.now());

        repository.save(frequency);

        return dto;
    }
}
