package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.InProgressJobCardDTO;
import com.tblmonitoring.tblmonitor.dto.JobCardDTO;
import com.tblmonitoring.tblmonitor.entity.JobCard;
import com.tblmonitoring.tblmonitor.repository.JobCardRepository;
import com.tblmonitoring.tblmonitor.repository.MachineProductionRepository;

import com.tblmonitoring.tblmonitor.entity.MachineProduction;

@Service
public class JobCardServiceImpl implements JobCardService{

	//Job Card No. Generate is From now IS Batch No. for the Front end
	//IF the New File Generated Batch card then IT was a Job Card No. 
	//It Whole Process is only For the Backend.
	@Autowired
    private JobCardRepository jobCardRepository;
	
	
	@Autowired
	private MachineProductionRepository machineProductionRepository;


	@Override
	public JobCard createJobCard(JobCardDTO dto) {
	    String jobCardNumber = generateJobCardNumber();
	    List<String> serials = generateMachineSerialNumbers(dto.getMachineType(), dto.getQuantity());

	    JobCard jobCard = new JobCard();
	    jobCard.setJobCardNumber(jobCardNumber);
	    jobCard.setMachineType(dto.getMachineType());
	    jobCard.setQuantity(dto.getQuantity());
	    jobCard.setStartDate(dto.getStartDate());
	    jobCard.setEndDate(dto.getEndDate());
	    jobCard.setProcessLayout(dto.getProcessLayout());
	    jobCard.setMachineSerialNumbers(serials);
	    jobCard.setProducedCount(0);
	    jobCard.setPoNumber(dto.getPoNumber());  // save the selected PO_number

	    return jobCardRepository.save(jobCard);
	}

    private String generateJobCardNumber() {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear()).substring(2); // e.g. "25"
        String month = String.format("%02d", now.getMonthValue()); // e.g. "07"
        String prefix = "B" + year + month;

        JobCard last = jobCardRepository.findLastByJobCardNumberPrefix(prefix + "%");
        int nextSeq = 1;
        if (last != null) {
            String[] parts = last.getJobCardNumber().split("-");
            nextSeq = Integer.parseInt(parts[1]) + 1;
        }

        return prefix + "-" + String.format("%04d", nextSeq);
    }

    private List<String> generateMachineSerialNumbers(String type, int quantity) {
        List<String> serials = new ArrayList<>();
        String prefix = type.equalsIgnoreCase("TBL Electronic") ? "TBL-ELE-" : "TBL-HYD-";

        // Get the max existing number for the type
        Integer lastSerial = jobCardRepository.findMaxSerialNumberByType(prefix);
        int start = (lastSerial != null) ? lastSerial + 1 : 1;

        for (int i = start; i < start + quantity; i++) {
            serials.add(prefix + String.format("%03d", i));
        }

        return serials;
    }


    @Override
    public List<JobCard> getAllJobCards() { 
        return jobCardRepository.findAll();
    }

    @Override
    public JobCard getJobCardById(Long id) {
        return jobCardRepository.findById(id).orElseThrow(() -> new RuntimeException("JobCard not found"));
    }
    
    
//    @Override
//    public List<JobCard> getInProgressJobCards() {
//        return jobCardRepository.findByQuantityGreaterThanProduced();
//    }
    
    @Override
    public List<JobCard> getInProgressJobCards() {
        return jobCardRepository.findInProgressJobCards();
    }


    @Override
    public int countInProgressJobCards() {
        return jobCardRepository.countInProgressJobCards();
    }

    @Override
    public List<InProgressJobCardDTO> getInProgressJobCardDetails() {
        List<JobCard> jobCards = jobCardRepository.findInProgressJobCards();
        
        List<InProgressJobCardDTO> dtos = new ArrayList<>();
        for (JobCard job : jobCards) {
            List<String> serials = job.getMachineSerialNumbers();
            String first = serials != null && !serials.isEmpty() ? serials.get(0) : null;
            String last = serials != null && !serials.isEmpty() ? serials.get(serials.size() - 1) : null;

            // ✅ Count machines with QC done (READY_TO_DISPATCH)
            int qcDoneCount = machineProductionRepository.countByJobCardNoAndStatusIn(
            	    job.getJobCardNumber(),
            	    List.of(
            	        MachineProduction.MachineStatus.READY_TO_DISPATCH,
            	        MachineProduction.MachineStatus.DISPATCHED
            	    )
            	);


            // ✅ Count dispatched machines
            int dispatchedCount = machineProductionRepository.countByJobCardNoAndStatus(
                job.getJobCardNumber(),
                MachineProduction.MachineStatus.DISPATCHED
            );

            InProgressJobCardDTO dto = new InProgressJobCardDTO(
                job.getJobCardNumber(),
                job.getQuantity(),
                job.getProducedCount() == null ? 0 : job.getProducedCount(),
                job.getStartDate(),
                job.getEndDate(),
                first,
                last,
                qcDoneCount,
                dispatchedCount
            );

            // ✅ Set new fields
           

            dtos.add(dto);
        }
        return dtos;
    }
    
    @Override
    public JobCard getJobCardByNumber(String jobCardNumber) {
        return jobCardRepository.findByJobCardNumber(jobCardNumber);
    }

    
}
