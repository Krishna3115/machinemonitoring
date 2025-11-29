package com.tblmonitoring.tblmonitor.service;

import com.tblmonitoring.tblmonitor.dto.MachineQRDTO;

public interface MachineQrService {

	MachineQRDTO getMachineQRData(String serialNo);
}
