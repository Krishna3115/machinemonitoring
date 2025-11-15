package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.ClaimPartDTO;
import com.tblmonitoring.tblmonitor.dto.InsuranceClaimDTO;
import com.tblmonitoring.tblmonitor.entity.ClaimPartEntity;
import com.tblmonitoring.tblmonitor.entity.InsuranceClaimEntity;
import com.tblmonitoring.tblmonitor.entity.Machine;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;
import com.tblmonitoring.tblmonitor.repository.ClaimPartRepository;
import com.tblmonitoring.tblmonitor.repository.InsuranceClaimRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.VandalismReportRepository;

@Service
public class InsuranceClaimServiceImpl implements InsuranceClaimService {

    @Autowired
    private InsuranceClaimRepository claimRepo;

    @Autowired
    private VandalismReportRepository vandalismRepo;
    
    @Autowired
    private ClaimPartRepository claimPartRepository;

    @Autowired
    private MachineRepository machineRepository;
    

    @Override
    public InsuranceClaimEntity startClaim(Long reportId, String complaintNo, LocalDate complaintDate, String machineSerial) {
        VandalismReport report = vandalismRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        List<InsuranceClaimEntity> existingClaims = claimRepo.findByVandalismReportId(reportId);
        if (!existingClaims.isEmpty()) {
            return existingClaims.get(0);
        }

        InsuranceClaimEntity claim = new InsuranceClaimEntity();
        claim.setVandalismReport(report);
        claim.setStatus("started");
        claim.setCreatedAt(LocalDateTime.now());
        claim.setUpdatedAt(LocalDateTime.now());
        
        // Set new fields
        claim.setComplaintNo(complaintNo);
        claim.setComplaintDate(complaintDate);
        claim.setMachineSerial(machineSerial);

        return claimRepo.save(claim);
    }


    @Override
    public InsuranceClaimEntity uploadJointReport(Long reportId, String pdfUrl, LocalDate date) {
        InsuranceClaimEntity claim = getByReport(reportId);
        claim.setJointReportPdf(pdfUrl);
        claim.setJointReportDate(date);
        claim.setStatus("joint_report_uploaded");
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepo.save(claim);
    }
    

    @Override
    public InsuranceClaimEntity markSubmittedToInsurance(Long reportId) {
        InsuranceClaimEntity claim = getByReport(reportId);
        claim.setClaimedToInsuranceAt(LocalDateTime.now());
        claim.setStatus("submitted_to_insurance");
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepo.save(claim);
    }

    @Override
    public InsuranceClaimEntity markServerVisitDone(Long reportId) {
        InsuranceClaimEntity claim = getByReport(reportId);
        claim.setServerVisitedAt(LocalDateTime.now());
        claim.setStatus("awaiting_claim_result");
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepo.save(claim);
    }

    @Override
    public InsuranceClaimEntity updateClaimResult(Long reportId, boolean passed, String remark) {
        InsuranceClaimEntity claim = getByReport(reportId);
        claim.setClaimPassed(passed);
        claim.setRemark(passed ? null : remark);
        claim.setStatus(passed ? "passed" : "failed");
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepo.save(claim);
    }

    @Override
    public InsuranceClaimEntity closeClaim(Long reportId) {
        InsuranceClaimEntity claim = getByReport(reportId);

        if (claim.getClaimPassed() == null) {
            throw new IllegalStateException("Cannot close claim without claim result.");
        }

        claim.setClosedAt(LocalDateTime.now());
        claim.setStatus("closed");
        claim.setUpdatedAt(LocalDateTime.now());
        return claimRepo.save(claim);
    }

    @Override
    public InsuranceClaimEntity getClaimByReportId(Long reportId) {
        return getByReport(reportId);
    }

    // ✅ Updated helper to work with List
    private InsuranceClaimEntity getByReport(Long reportId) {
        List<InsuranceClaimEntity> claims = claimRepo.findByVandalismReportId(reportId);
        return claims.stream()
                .max((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()))  // Or updatedAt if preferred
                .orElseThrow(() -> new RuntimeException("No claim found for report ID: " + reportId));
    }
    
    
    @Override
    public void saveClaimParts(Long claimId, List<ClaimPartDTO> parts) {
        InsuranceClaimEntity claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        List<ClaimPartEntity> entities = parts.stream().map(dto -> {
            ClaimPartEntity part = new ClaimPartEntity();
            part.setPartName(dto.getPartName());
            part.setActualValue(dto.getActualValue());
            part.setClaimedAmount(dto.getClaimedAmount());
            part.setInsuranceClaim(claim);
            return part;
        }).collect(Collectors.toList());

        claimPartRepository.saveAll(entities);
    }
    
    private ClaimPartDTO convertToDTO(ClaimPartEntity entity) {
        ClaimPartDTO dto = new ClaimPartDTO();
        dto.setPartName(entity.getPartName());
        dto.setActualValue(entity.getActualValue());
        dto.setClaimedAmount(entity.getClaimedAmount());
        return dto;
    }
    
    @Override
    public List<ClaimPartDTO> getPartsByClaimId(Long claimId) {
        return claimPartRepository.findByInsuranceClaimId(claimId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private ClaimPartDTO convertToDto(ClaimPartEntity entity) {
    	ClaimPartDTO dto = new ClaimPartDTO();
        dto.setId(entity.getId());
        dto.setPartName(entity.getPartName());
        dto.setActualValue(entity.getActualValue());
        dto.setClaimedAmount(entity.getClaimedAmount());
        return dto;
    }

    @Override
    public List<ClaimPartDTO> getClaimParts(Long claimId) {
        List<ClaimPartEntity> partEntities = claimPartRepository.findByInsuranceClaimId(claimId);

        return partEntities.stream().map(entity -> {
            ClaimPartDTO dto = new ClaimPartDTO();
            dto.setPartName(entity.getPartName());
            dto.setActualValue(entity.getActualValue());
            dto.setClaimedAmount(entity.getClaimedAmount());

            double profitOrLoss = entity.getClaimedAmount() - entity.getActualValue();
            dto.setProfitOrLossAmount(profitOrLoss);
            dto.setProfitOrLossPercent(entity.getActualValue() == 0 ? 0 : (profitOrLoss / entity.getActualValue()) * 100);

            return dto;
        }).collect(Collectors.toList());
    }

    
    public Map<String, Object> getClaimSummary(Long claimId) {
        List<ClaimPartDTO> parts = getClaimParts(claimId);

        double totalActual = 0;
        double totalClaimed = 0;

        for (ClaimPartDTO part : parts) {
            totalActual += part.getActualValue();
            totalClaimed += part.getClaimedAmount();
        }

        double profitOrLoss = totalClaimed - totalActual;
        double profitOrLossPercent = totalActual == 0 ? 0 : (profitOrLoss / totalActual) * 100;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalActualValue", totalActual);
        summary.put("totalClaimedAmount", totalClaimed);
        summary.put("profitOrLossAmount", profitOrLoss);
        summary.put("profitOrLossPercent", profitOrLossPercent);

        return summary;
    }

    @Override
    public List<InsuranceClaimDTO> getAllClaims(String status) {
        List<InsuranceClaimEntity> claims;

        if (status != null && !status.isEmpty()) {
            claims = claimRepo.findByStatus(status);
        } else {
            claims = claimRepo.findAll();
        }

        return claims.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    
    @Override
    public InsuranceClaimDTO convertToDto(InsuranceClaimEntity claim) {
        Optional<Machine> machineOpt = machineRepository.findByModelNo(claim.getMachineSerial());

        Machine machine = machineOpt.orElse(null);

        // ✅ Now pass both claim and machine
        return InsuranceClaimDTO.fromEntity(claim, machine);
    }


}

