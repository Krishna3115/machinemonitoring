package com.tblmonitoring.tblmonitor.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.VandalismReportDTO;
import com.tblmonitoring.tblmonitor.dto.VandalismReportwithUserDTO;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.entity.VandalismReport;
import com.tblmonitoring.tblmonitor.repository.InsuranceClaimRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;
import com.tblmonitoring.tblmonitor.repository.VandalismReportRepository;

@Service
public class VandalismServiceImpl implements VandalismService{

	private final VandalismReportRepository reportRepository;

    public VandalismServiceImpl(VandalismReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    

    @Override
    public VandalismReport saveReport(VandalismReportDTO dto) {
        VandalismReport report = new VandalismReport();
        report.setInspectionId(dto.getInspectionId());
        report.setModelNo(dto.getModelNo());
        report.setComponentName(dto.getComponentName());
        report.setIssueDescription(dto.getIssueDescription());
    //    report.setPhotoUrl(dto.getPhotoUrl());  // Already uploaded URL
        report.setIsDamaged(dto.getIsDamaged());
        report.setPhotoUrls(dto.getPhotoUrls());
        

        report.setReportedByUserId(dto.getReportedByUserId());
        report.setReportedAtDateTime(new Date());

        return reportRepository.save(report);
    }
	
    @Override
    public List<VandalismReport> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<VandalismReport> saveMultipleReports(List<VandalismReportDTO> reportDTOs) {
        List<VandalismReport> savedReports = reportDTOs.stream().map(dto -> {
            VandalismReport report = new VandalismReport();
            report.setInspectionId(dto.getInspectionId());
            report.setModelNo(dto.getModelNo());
            report.setComponentName(dto.getComponentName());
            report.setIssueDescription(dto.getIssueDescription());
           // report.setPhotoUrl(dto.getPhotoUrl());
            report.setIsDamaged(dto.getIsDamaged());

            report.setPhotoUrls(dto.getPhotoUrls()); // ✅ Use actual list

            report.setReportedByUserId(dto.getReportedByUserId());
            report.setReportedAtDateTime(new Date());
            return report;
        }).toList();

        return reportRepository.saveAll(savedReports);
    }
    
    

    @Override
    public List<VandalismReportwithUserDTO> getAllReportsWithUserNames() {
        List<VandalismReport> reports = reportRepository.findAll();

        return reports.stream()
            .map(report -> {
                Long userId = report.getReportedByUserId();
                String reporterName;

                if (userId != null) {
                    reporterName = userRepository.findById(userId)
                        .map(Users::getName)
                        .orElse("Unknown");
                } else {
                    reporterName = "Unknown";
                }

                // ✅ Updated logic for multiple claims
                String claimStatus = null;
                Long claimId = null;

                var claims = insuranceClaimRepository.findByVandalismReportId(report.getId());
                if (!claims.isEmpty()) {
                    var latestClaim = claims.stream()
                        .max((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()))
                        .orElse(null);

                    if (latestClaim != null) {
                        claimStatus = latestClaim.getStatus();
                        claimId = latestClaim.getId();
                    }
                }

                return new VandalismReportwithUserDTO(report, reporterName, claimStatus, claimId);
            })
            .collect(Collectors.toList());
    }



}
