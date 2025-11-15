package com.tblmonitoring.tblmonitor.service;

import com.tblmonitoring.tblmonitor.dto.GreaseRefillDTO;
import com.tblmonitoring.tblmonitor.entity.GreaseFillRecord;

public interface GreaseRefillService {

	GreaseFillRecord submitGreaseRefill(GreaseRefillDTO dto);
}
