package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tblmonitoring.tblmonitor.entity.MachineProduction;

@Repository
public interface MachineProductionRepository extends JpaRepository<MachineProduction, Long> {
    List<MachineProduction> findByStatus(MachineProduction.MachineStatus status);
    
 // MachineProductionRepository.java
    @Query("SELECT COUNT(m) FROM MachineProduction m WHERE m.status = :status")
    Long countByStatus(@Param("status") MachineProduction.MachineStatus status);

    
    List<MachineProduction> findByJobCardNo(String jobCardNo);

    List<MachineProduction> findByIdIn(List<Long> ids);
    
    List<MachineProduction> findByMachineSerialNo(String machineSerialNo);
    
    int countByJobCardNoAndStatus(String jobCardNo, MachineProduction.MachineStatus status);
    
    
    @Query("SELECT COUNT(m) FROM MachineProduction m WHERE m.jobCardNo = :jobCardNo AND m.status IN :statuses")
    int countByJobCardNoAndStatusIn(@Param("jobCardNo") String jobCardNo, @Param("statuses") List<MachineProduction.MachineStatus> statuses);

}
