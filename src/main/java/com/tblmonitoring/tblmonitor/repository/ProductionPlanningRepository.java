package com.tblmonitoring.tblmonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.dto.LoaDetailDTO;
import com.tblmonitoring.tblmonitor.entity.ProductionPlanning;

public interface ProductionPlanningRepository extends JpaRepository<ProductionPlanning, Long> {
	
	@Query("SELECT new com.tblmonitoring.tblmonitor.dto.LoaDetailDTO(p.poNumber, p.plannedQuantity, p.startDate, p.endDate) FROM ProductionPlanning p")
	List<LoaDetailDTO> findAllLoaDetails();
	

    @Query("SELECT COALESCE(SUM(p.plannedQuantity), 0) FROM ProductionPlanning p WHERE p.poNumber = :poNumber")
    int getTotalPlannedByPoNumber(@Param("poNumber") String poNumber);
    
    @Query("SELECT DISTINCT p.poNumber FROM ProductionPlanning p WHERE p.poNumber IS NOT NULL")
    List<String> findAllDistinctPoNumbers();
    
    List<ProductionPlanning> findByPoNumber(String poNumber);
}