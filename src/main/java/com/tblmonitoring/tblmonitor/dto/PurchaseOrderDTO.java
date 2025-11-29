package com.tblmonitoring.tblmonitor.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class PurchaseOrderDTO {

	 private String poNumber;
     
	 private LocalDate poDate;
     
	 @Column(name = "final_dispatch_date")
     private String dispatchDate;
     private int quantity;
     private int warrantyMonths;
     private int maintenanceDays;
     private String erpoa; // 
     private double perDayFine;   
     private String division;
     private String section;
 
     
     public PurchaseOrderDTO() {
		// TODO Auto-generated constructor stub
	}

	 public PurchaseOrderDTO(String poNumber, LocalDate poDate, String dispatchDate, int quantity,
			int warrantyMonths, int maintenanceDays, String erpoa, double perDayFine, String division, String section) {
		super();
		this.poNumber = poNumber;
		this.poDate = poDate;
		this.dispatchDate = dispatchDate;
		this.quantity = quantity;
		this.warrantyMonths = warrantyMonths;
		this.maintenanceDays = maintenanceDays;
		this.erpoa = erpoa;
		this.perDayFine = perDayFine;
		this.division = division;
		this.section = section;
		
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

	 public String getDispatchDate() {
		 return dispatchDate;
	 }

	 public void setDispatchDate(String dispatchDate) {
		 this.dispatchDate = dispatchDate;
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

	 public String getDivision() {
		 return division;
	 }

	 public void setDivision(String division) {
		 this.division = division;
	 }

	 public String getSection() {
		 return section;
	 }

	 public void setSection(String section) {
		 this.section = section;
	 }
     
}
