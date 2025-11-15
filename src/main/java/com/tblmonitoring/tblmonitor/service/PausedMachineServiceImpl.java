package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.MachineDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.enums.ComplaintStatus;
import com.tblmonitoring.tblmonitor.repository.CustomerComplaintRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;

@Service
public class PausedMachineServiceImpl implements PausedMachineService {

	private final CustomerComplaintRepository complaintRepo;
    private final InstallationRecordRepository installRepo;

    @Autowired
    public PausedMachineServiceImpl(CustomerComplaintRepository complaintRepo,
                                    InstallationRecordRepository installRepo) {
        this.complaintRepo = complaintRepo;
        this.installRepo = installRepo;
    }

    @Override
    public long countPausedMachines() {
        List<Machine> complained = complaintRepo.findMachinesByStatus(ComplaintStatus.OPEN);
        return installRepo.findMachinesByInstallationStatus("COMPLETE")
                          .stream().filter(complained::contains).count();
    }

    @Override
    public List<MachineDTO> getPausedMachines() {
        List<Machine> complained = complaintRepo.findMachinesByStatus(ComplaintStatus.OPEN);
        return installRepo.findMachinesByInstallationStatus("COMPLETE").stream()
            .filter(complained::contains)
            .map(m -> new MachineDTO(m.getModelNo(), m.getSection(), m.getStatus()))
            .collect(Collectors.toList());
    }
}

