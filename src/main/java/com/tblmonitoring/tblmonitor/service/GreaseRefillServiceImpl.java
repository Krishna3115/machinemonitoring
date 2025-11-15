package com.tblmonitoring.tblmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.GreaseRefillDTO;
import com.tblmonitoring.tblmonitor.entity.GreaseFillRecord;
import com.tblmonitoring.tblmonitor.repository.GreaseRefillRepository;

import jakarta.transaction.Transactional;

@Service
public class GreaseRefillServiceImpl implements GreaseRefillService {

    private final GreaseRefillRepository repo;

    @Autowired
    public GreaseRefillServiceImpl(GreaseRefillRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public GreaseFillRecord submitGreaseRefill(GreaseRefillDTO dto) {
        GreaseFillRecord rec = new GreaseFillRecord();
        rec.setModelNo(dto.getModelNo());
        rec.setFillDate(dto.getFillDate());
        rec.setRemainingGreaseKg(dto.getRemainingGreaseKg());
        rec.setRemainingPhotoUrl(dto.getRemainingGreasePhoto());
        rec.setFilledGreaseKg(dto.getFilledGreaseKg());
        rec.setFilledPhotoUrl(dto.getFilledGreasePhoto());
        rec.setIsFullTank(dto.getIsFullTank());
        rec.setTechnicianId(dto.getSubmittedBy());
        rec.setRemarks(dto.getRemarks());
        return repo.save(rec);
    }
}
