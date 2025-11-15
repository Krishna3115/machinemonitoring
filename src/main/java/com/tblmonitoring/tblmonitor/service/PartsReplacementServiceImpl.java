package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.ConfirmReplacedPartsRequestDTO;
import com.tblmonitoring.tblmonitor.dto.PartInfoDTO;
import com.tblmonitoring.tblmonitor.dto.PartsReplacementRequestDTO;
import com.tblmonitoring.tblmonitor.entity.PartInfo;
import com.tblmonitoring.tblmonitor.entity.PartsReplacementRequest;
import com.tblmonitoring.tblmonitor.repository.PartInfoRepository;
import com.tblmonitoring.tblmonitor.repository.PartsReplacementRequestRepository;

@Service
public class PartsReplacementServiceImpl implements PartsReplacementService {

    private final PartsReplacementRequestRepository requestRepo;
    private final PartInfoRepository partInfoRepo;

    public PartsReplacementServiceImpl(
            PartsReplacementRequestRepository requestRepo,
            PartInfoRepository partInfoRepo) {
        this.requestRepo = requestRepo;
        this.partInfoRepo = partInfoRepo;
    }

    @Override
    public PartsReplacementRequestDTO createRequest(PartsReplacementRequestDTO dto) {
        PartsReplacementRequest entity = mapDtoToEntity(dto);
        entity.setPartReceived(false);
        entity.setCompleted(false);
        PartsReplacementRequest saved = requestRepo.save(entity);
        return mapEntityToDto(saved);
    }

    @Override
    public List<PartsReplacementRequestDTO> getAssignmentsForReplacingTechnician(Long techId) {
        List<PartsReplacementRequest> list = requestRepo.findByReplacingTechnicianIdAndPartReceivedFalse(techId);
        return list.stream()
                   .map(this::mapEntityToDto)
                   .collect(Collectors.toList());
    }

    @Override
    public void markPartReceived(Long requestId) {
        PartsReplacementRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        req.setPartReceived(true);
        req.setPartReceivedAt(LocalDateTime.now());
        requestRepo.save(req);
    }

    @Override
    public PartsReplacementRequestDTO confirmReplacedParts(ConfirmReplacedPartsRequestDTO req) {
        PartsReplacementRequest entity = requestRepo.findById(req.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Mark as completed
        entity.setCompleted(true);
        entity.setCompletedAt(LocalDateTime.now());
        entity.setCompletionRemarks(req.getReplacedParts().stream()
                .map(PartInfoDTO::getReplacedPartNo)
                .collect(Collectors.joining(", ")));

        // Update each PartInfo
        for (PartInfoDTO partDto : req.getReplacedParts()) {
            PartInfo part = entity.getParts().stream()
                    .filter(pi -> pi.getId().equals(partDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Part info not found"));
            part.setReplacedPartNo(partDto.getReplacedPartNo());
            part.setMachineSerialNo(partDto.getMachineSerialNo());
        }

        PartsReplacementRequest saved = requestRepo.save(entity);
        return mapEntityToDto(saved);
    }

    @Override
    public PartsReplacementRequestDTO getRequestById(Long requestId) {
        PartsReplacementRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapEntityToDto(req);
    }

    // -------------------- Mappers --------------------

    private PartsReplacementRequestDTO mapEntityToDto(PartsReplacementRequest e) {
        PartsReplacementRequestDTO dto = new PartsReplacementRequestDTO();
        dto.setId(e.getId());
        dto.setMachineNo(e.getMachineNo());
        dto.setDatetime(e.getDatetime());
        dto.setReason(e.getReason());
        dto.setDispatchMethod(e.getDispatchMethod());
        dto.setCourierName(e.getCourierName());
        dto.setTrackingNumber(e.getTrackingNumber());
        dto.setCourierContact(e.getCourierContact());
        dto.setTechnicianAssignedId(e.getTechnicianAssignedId());
        dto.setReplacingTechnicianId(e.getReplacingTechnicianId());
        dto.setPartReceived(e.isPartReceived());
        dto.setCompleted(e.isCompleted());
        dto.setCompletionRemarks(e.getCompletionRemarks());

        List<PartInfoDTO> partsDto = e.getParts().stream().map(pi -> {
            PartInfoDTO pd = new PartInfoDTO();
            pd.setId(pi.getId());
            pd.setPartName(pi.getPartName());
            pd.setOldPartNo(pi.getOldPartNo());
            pd.setReplacedPartNo(pi.getReplacedPartNo());
            pd.setMachineSerialNo(pi.getMachineSerialNo());
            return pd;
        }).collect(Collectors.toList());

        dto.setParts(partsDto);

        return dto;
    }

    private PartsReplacementRequest mapDtoToEntity(PartsReplacementRequestDTO dto) {
        PartsReplacementRequest e = new PartsReplacementRequest();
        e.setMachineNo(dto.getMachineNo());
        e.setDatetime(dto.getDatetime());
        e.setDispatchMethod(dto.getDispatchMethod());
        e.setReason(dto.getReason());
        e.setCourierName(dto.getCourierName());
        e.setTrackingNumber(dto.getTrackingNumber());
        e.setCourierContact(dto.getCourierContact());
        e.setTechnicianAssignedId(dto.getTechnicianAssignedId());
        e.setReplacingTechnicianId(dto.getReplacingTechnicianId());

        if (dto.getParts() != null) {
            List<PartInfo> parts = dto.getParts().stream().map(pd -> {
                PartInfo pi = new PartInfo();
                pi.setPartName(pd.getPartName());
                pi.setOldPartNo(pd.getOldPartNo());
                pi.setReplacedPartNo(pd.getReplacedPartNo());
                pi.setMachineSerialNo(pd.getMachineSerialNo());
                pi.setRequest(e); // important: set the relationship
                return pi;
            }).collect(Collectors.toList());
            e.setParts(parts);
        }

        return e;
    }
}
