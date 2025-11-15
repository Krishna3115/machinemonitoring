package com.tblmonitoring.tblmonitor.service;

import java.util.List;

import com.tblmonitoring.tblmonitor.dto.InProgressJobCardDTO;
import com.tblmonitoring.tblmonitor.dto.JobCardDTO;
import com.tblmonitoring.tblmonitor.entity.JobCard;

public interface JobCardService {

	JobCard createJobCard(JobCardDTO jobCardDTO);

    List<JobCard> getAllJobCards();

    JobCard getJobCardById(Long id);
    
    List<JobCard> getInProgressJobCards();

    int countInProgressJobCards();

	List<InProgressJobCardDTO> getInProgressJobCardDetails();

	JobCard getJobCardByNumber(String jobCardNumber);

}
