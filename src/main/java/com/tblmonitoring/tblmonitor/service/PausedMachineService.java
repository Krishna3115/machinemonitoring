package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.MachineDTO;

public interface PausedMachineService {

	long countPausedMachines();
    List<MachineDTO> getPausedMachines();
}
