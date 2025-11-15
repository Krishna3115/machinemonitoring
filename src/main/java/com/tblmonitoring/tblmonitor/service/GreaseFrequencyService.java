package com.tblmonitoring.tblmonitor.service;

import com.tblmonitoring.tblmonitor.dto.GreaseFrequencyDTO;

public interface GreaseFrequencyService {

	 GreaseFrequencyDTO saveOrUpdate(GreaseFrequencyDTO dto, String updatedBy);
}
