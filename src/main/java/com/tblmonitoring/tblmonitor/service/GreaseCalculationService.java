package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.GreaseCalculationResult;

public interface GreaseCalculationService {

	GreaseCalculationResult calculateGreaseEstimate(String modelNo, double greaseLeftKg);
	List<GreaseCalculationResult> getMachinesWithLowGrease();
	
	
}
