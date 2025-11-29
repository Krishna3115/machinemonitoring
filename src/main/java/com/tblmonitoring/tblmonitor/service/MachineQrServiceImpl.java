package com.tblmonitoring.tblmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.MachineQRDTO;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.MachineInspection;
import com.tblmonitoring.tblmonitor.entity.MachineProduction;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MachineQrServiceImpl implements MachineQrService {

    @Autowired
    private MachineProductionRepository productionRepo;

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private InstallationRecordRepository installationRepo;

    @Autowired
    private InspectionRepository inspectionRepo;

    @Override
    public MachineQRDTO getMachineQRData(String serialNo) {

        // 1️⃣ Production Data
        MachineProduction prod = productionRepo.findTopByMachineSerialNo(serialNo);
        if (prod == null) throw new RuntimeException("No production data found: " + serialNo);

        // 2️⃣ Machine Details
        Optional<Machine> machineOpt = machineRepo.findByModelNo(serialNo);
        Machine machine = machineOpt.orElse(null);

        String division = machine != null ? machine.getDivision() : null;
        String section = machine != null ? machine.getSection() : null;
        String dispatchDate = machine != null && machine.getDispatchDate() != null ? machine.getDispatchDate().toString() : null;
        String deliveredDate = machine != null && machine.getDeliveredDate() != null ? machine.getDeliveredDate().toString() : null;

        // 3️⃣ Installation Date
        Optional<InstallationRecord> installOpt = installationRepo.findByMachine_ModelNo(serialNo);
        LocalDateTime installationDate = installOpt.map(InstallationRecord::getInstallationEnded).orElse(null);

        // 4️⃣ Latest Maintenance
        LocalDateTime maintenanceEnded = inspectionRepo.findLatestMaintenanceDateByModelNo(serialNo);

        // 5️⃣ Build DTO
        return new MachineQRDTO(
            prod.getMachineSerialNo(),
            prod.getJobCardNo(),
            prod.getMotorNo(),
            prod.getSensorNo(),
            prod.getApplicatorNo(),
            prod.getBatteryNo(),
            prod.getSolarChargeControllerNo(),
            prod.getSolarPanelNo1(),
            prod.getSolarPanelNo2(),
            prod.getCabinetNo(),
            prod.getBatchCounterNo(),
            prod.getMcbNo(),
            prod.getGearPumpNo(),
            prod.getQcInspectionDate(), // final QC
            division,
            section,
            dispatchDate,
            deliveredDate,
            installationDate,
            maintenanceEnded
        );
    }

}
