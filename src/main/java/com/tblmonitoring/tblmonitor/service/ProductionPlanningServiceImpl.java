package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tblmonitoring.tblmonitor.dto.AvailableLOADTO;
import com.tblmonitoring.tblmonitor.dto.ProductionPlanningDTO;
import com.tblmonitoring.tblmonitor.entity.ProductionPlanning;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;
import com.tblmonitoring.tblmonitor.repository.ProductionPlanningRepository;
import com.tblmonitoring.tblmonitor.repository.PurchaseOrderRepository;

@Service
public class ProductionPlanningServiceImpl implements ProductionPlanningService {

    @Autowired
    private ProductionPlanningRepository planningRepo;

    @Autowired
    private PurchaseOrderRepository purchaseRepo;

    @Override
    public void createPlan(ProductionPlanningDTO dto) {
        String poNumber = dto.getPoNumber().trim();

        PurchaseOrder po = purchaseRepo.findByPoNumber(poNumber)
            .orElseThrow(() -> new RuntimeException("Purchase Order not found for PO Number: " + poNumber));

        int totalPlanned = planningRepo.getTotalPlannedByPoNumber(poNumber);
        int available = po.getQuantity() - totalPlanned;

        if (dto.getPlannedQuantity() > available) {
            throw new IllegalArgumentException("Planned quantity exceeds available quantity");
        }

        ProductionPlanning plan = new ProductionPlanning();
        plan.setPoNumber(poNumber);
        plan.setPlannedQuantity(dto.getPlannedQuantity());
        plan.setStartDate(dto.getStartDate().atStartOfDay());
        plan.setEndDate(dto.getEndDate().atStartOfDay());
        plan.setCreatedAt(LocalDateTime.now());

        planningRepo.save(plan);
    }

    @Override
    public List<AvailableLOADTO> getAvailableLOAs() {
        return purchaseRepo.findAll().stream()
            .map(po -> {
                String poNumber = po.getPoNumber().trim();

                int totalPlanned = planningRepo.getTotalPlannedByPoNumber(poNumber);
                int remaining = po.getQuantity() - totalPlanned;

                // Add division, section, final dispatch date from PO
                String division = po.getDivision(); // assuming field exists
                String section = po.getSection();   // assuming field exists
                String finalDispatchDate = po.getDispatchDate(); // assuming LocalDate

                return new AvailableLOADTO(poNumber, po.getQuantity(), remaining,
                                            division, section, finalDispatchDate);
            })
            .filter(loa -> loa.getRemainingQuantity() > 0) // only those with available quantity
            .collect(Collectors.toList());
    }


}
