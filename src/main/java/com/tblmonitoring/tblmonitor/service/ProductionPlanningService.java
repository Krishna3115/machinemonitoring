package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.AvailableLOADTO;
import com.tblmonitoring.tblmonitor.dto.ProductionPlanningDTO;

public interface ProductionPlanningService {

	 void createPlan(ProductionPlanningDTO dto);
	    List<AvailableLOADTO> getAvailableLOAs();
	}

