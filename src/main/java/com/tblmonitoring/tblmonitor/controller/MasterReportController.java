package com.tblmonitoring.tblmonitor.controller;

import com.tblmonitoring.tblmonitor.dto.MasterReportDTO;
import com.tblmonitoring.tblmonitor.service.MasterReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master-report")
 // ✅ Allow frontend testing (React, etc.)
public class MasterReportController {

    private final MasterReportService masterReportService;

    // ✅ Constructor injection (best practice)
    public MasterReportController(MasterReportService masterReportService) {
        this.masterReportService = masterReportService;
    }

    // ✅ Endpoint to fetch the full Master Report
    @GetMapping
    public List<MasterReportDTO> getMasterReport() {
        return masterReportService.getMasterReport();
    }
}
