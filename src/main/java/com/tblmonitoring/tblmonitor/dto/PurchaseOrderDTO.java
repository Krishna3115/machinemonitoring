package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PurchaseOrderDTO {

	 private String poNumber;
     
	 private LocalDate poDate;
     
	 @Column(name = "final_dispatch_date")
     private LocalDate finalDispatchDate;
     private int quantity;
     private int warrantyMonths;
     private int maintenanceDays;
     private String erpoa; // 
     private double perDayFine;   
     
     public PurchaseOrderDTO() {
		// TODO Auto-generated constructor stub
	}

	 public PurchaseOrderDTO(String poNumber, LocalDate poDate, LocalDate finalDispatchDate, int quantity,
			int warrantyMonths, int maintenanceDays, String erpoa, double perDayFine) {
		super();
		this.poNumber = poNumber;
		this.poDate = poDate;
		this.finalDispatchDate = finalDispatchDate;
		this.quantity = quantity;
		this.warrantyMonths = warrantyMonths;
		this.maintenanceDays = maintenanceDays;
		this.erpoa = erpoa;
		this.perDayFine = perDayFine;
	 }

	 public String getPoNumber() {
		 return poNumber;
	 }

	 public void setPoNumber(String poNumber) {
		 this.poNumber = poNumber;
	 }

	 public LocalDate getPoDate() {
		 return poDate;
	 }

	 public void setPoDate(LocalDate poDate) {
		 this.poDate = poDate;
	 }

	 public LocalDate getFinalDispatchDate() {
		 return finalDispatchDate;
	 }

	 public void setFinalDispatchDate(LocalDate finalDispatchDate) {
		 this.finalDispatchDate = finalDispatchDate;
	 }

	 public int getQuantity() {
		 return quantity;
	 }

	 public void setQuantity(int quantity) {
		 this.quantity = quantity;
	 }

	 public int getWarrantyMonths() {
		 return warrantyMonths;
	 }

	 public void setWarrantyMonths(int warrantyMonths) {
		 this.warrantyMonths = warrantyMonths;
	 }

	 public int getMaintenanceDays() {
		 return maintenanceDays;
	 }

	 public void setMaintenanceDays(int maintenanceDays) {
		 this.maintenanceDays = maintenanceDays;
	 }

	 public String getErpoa() {
		 return erpoa;
	 }

	 public void setErpoa(String erpoa) {
		 this.erpoa = erpoa;
	 }

	 public double getPerDayFine() {
		 return perDayFine;
	 }

	 public void setPerDayFine(double perDayFine) {
		 this.perDayFine = perDayFine;
	 }

	
     
     
}
