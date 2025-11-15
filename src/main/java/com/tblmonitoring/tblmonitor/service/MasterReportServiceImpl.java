package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tblmonitoring.tblmonitor.dto.MasterReportDTO;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.ProductionPlanning;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.ProductionPlanningRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MasterReportServiceImpl implements MasterReportService {

	@Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ProductionPlanningRepository productionPlanningRepository;
    
    @Autowired
    private InstallationRecordRepository installationRecordRepository;

    @Override
    public List<MasterReportDTO> getMasterReport() {
        List<Machine> machines = machineRepository.findAll();

        return machines.stream().map(machine -> {
            MasterReportDTO dto = new MasterReportDTO();

            // Machine Info
            dto.setModelNo(machine.getModelNo());
            dto.setMachineName(machine.getMachineName());
            dto.setLocation(machine.getLocation());
            dto.setStatus(machine.getStatus());
            dto.setDispatchDate(machine.getDispatchDate());
            dto.setDeliveredDate(machine.getDeliveredDate());
            dto.setTechnicianAssigned(machine.getTechnicianAssigned());

            // Purchase Order Info
            PurchaseOrder po = machine.getPurchaseOrder();
            if (po != null) {
                dto.setPoNumber(po.getPoNumber());
                dto.setPoDate(po.getPoDate());
                dto.setFinalDispatchDate(po.getFinaldispatchDate());
                dto.setPoQuantity(po.getQuantity());
                dto.setPoWarrantyMonths(po.getWarrantyMonths());
                dto.setMaintenanceDays(po.getMaintenanceDays());
                dto.setErpoa(po.getErpoa());
                dto.setPerDayFine(po.getPerDayFine());
            }

            // Production Planning Info
            if (po != null && po.getPoNumber() != null) {
                List<ProductionPlanning> plannings = productionPlanningRepository.findByPoNumber(po.getPoNumber());
                if (!plannings.isEmpty()) {
                    int totalPlanned = plannings.stream().mapToInt(ProductionPlanning::getPlannedQuantity).sum();
                    dto.setPlannedQuantity(totalPlanned);

                    LocalDateTime startDate = plannings.stream()
                            .map(ProductionPlanning::getStartDate)
                            .min(LocalDateTime::compareTo)
                            .orElse(null);
                    LocalDateTime endDate = plannings.stream()
                            .map(ProductionPlanning::getEndDate)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);

                    dto.setProductionStartDate(startDate);
                    dto.setProductionEndDate(endDate);
                }
            }


            // Installation Record Info
            InstallationRecord inst = installationRecordRepository.findByMachine(machine);
            if (inst != null) {
                dto.setInstallationStarted(inst.getInstallationStarted());
                dto.setInstallationEnded(inst.getInstallationEnded());
                dto.setSection(inst.getSection());
                dto.setCurveNo(inst.getCurveNo());
                dto.setPoleNo(inst.getPoleNo());
                dto.setFromKm(inst.getFromKm());
                dto.setToKm(inst.getToKm());
                dto.setRhLhRadius(inst.getRhLhRadius());
                dto.setSrDen(inst.getSrDen());
                dto.setLineSection(inst.getLineSection());
                dto.setPwi(inst.getPwi());
                dto.setMachineStatus(inst.getMachineStatus());
                dto.setGreaseLevel(inst.getGreaseLevel());
                dto.setGreaseLevelPhotoUrl(inst.getGreaseLevelPhotoUrl());
                dto.setWheelCount(inst.getWheelCount());
                dto.setTimeCount(inst.getTimeCount());
                dto.setRemarks(inst.getRemarks());
                dto.setGreaseLevelKg(inst.getGreaseLevelKg());
                dto.setInstallationTechnicianId(inst.getInstallationTechnicianId());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
