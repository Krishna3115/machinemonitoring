package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "production_planning")
public class ProductionPlanning {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String poNumber;
	    private int plannedQuantity;
	    private LocalDateTime startDate;
	    private LocalDateTime endDate;

	    private LocalDateTime createdAt = LocalDateTime.now();
	    
	    
	    
	    public ProductionPlanning() {
			// TODO Auto-generated constructor stub
		}

		public ProductionPlanning(Long id, String poNumber, int plannedQuantity, LocalDateTime startDate, LocalDateTime endDate,
				LocalDateTime createdAt) {
			super();
			this.id = id;
			this.poNumber = poNumber;
			this.plannedQuantity = plannedQuantity;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPoNumber() {
			return poNumber;
		}

		public void setPoNumber(String poNumber) {
			this.poNumber = poNumber;
		}

		public int getPlannedQuantity() {
			return plannedQuantity;
		}

		public void setPlannedQuantity(int plannedQuantity) {
			this.plannedQuantity = plannedQuantity;
		}

		public LocalDateTime getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDateTime startDate) {
			this.startDate = startDate;
		}

		public LocalDateTime getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDateTime endDate) {
			this.endDate = endDate;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
	    
	    
}
