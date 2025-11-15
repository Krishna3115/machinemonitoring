package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "machine_frequency_config")

public class GreaseFrequency {

	 	@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(name = "model_no", nullable = false, unique = true)
		private String modelNo;
		
		@Column(name = "wheels_per_day", nullable = false)
		private int wheelsPerDay;
		
		@Column(name = "grease_release_rate_gm_per_sec", nullable = false)
		private double greaseReleaseRateGmPerSec;
		
		@Column(name = "updated_by")
		private String updatedBy;
		
		@Column(name = "updated_at")
		private LocalDateTime updatedAt;
		
		public GreaseFrequency() {
			// TODO Auto-generated constructor stub
		}

		public GreaseFrequency(Long id, String modelNo, int wheelsPerDay, double greaseReleaseRateGmPerSec,
				String updatedBy, LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.modelNo = modelNo;
			this.wheelsPerDay = wheelsPerDay;
			this.greaseReleaseRateGmPerSec = greaseReleaseRateGmPerSec;
			this.updatedBy = updatedBy;
			this.updatedAt = updatedAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getModelNo() {
			return modelNo;
		}

		public void setModelNo(String modelNo) {
			this.modelNo = modelNo;
		}

		public int getWheelsPerDay() {
			return wheelsPerDay;
		}

		public void setWheelsPerDay(int wheelsPerDay) {
			this.wheelsPerDay = wheelsPerDay;
		}

		public double getGreaseReleaseRateGmPerSec() {
			return greaseReleaseRateGmPerSec;
		}

		public void setGreaseReleaseRateGmPerSec(double greaseReleaseRateGmPerSec) {
			this.greaseReleaseRateGmPerSec = greaseReleaseRateGmPerSec;
		}

		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		
		
		
}
