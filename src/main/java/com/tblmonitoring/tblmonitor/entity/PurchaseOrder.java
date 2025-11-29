package com.tblmonitoring.tblmonitor.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	
    private String poNumber;
    private LocalDate poDate;
    @Column(name = "final_dispatch_date")
    private String dispatchDate;
    private int quantity;
    private int warrantyMonths;
    private int maintenanceDays;
    private String erpoa;
    private Double perDayFine;
    @Column(name = "division")
    private String division;
    @Column(name = "section")
    private String section;
    
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConsigneeContact> consigneeContacts;
    
    public PurchaseOrder() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrder(Long id, String poNumber, LocalDate poDate, String dispatchDate, int quantity,
			int warrantyMonths, int maintenanceDays, String erpoa, Double perDayFine, String division, String section,
			List<ConsigneeContact> consigneeContacts) {
		super();
		this.id = id;
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
		this.consigneeContacts = consigneeContacts;
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

	public Double getPerDayFine() {
		return perDayFine;
	}

	public void setPerDayFine(Double perDayFine) {
		this.perDayFine = perDayFine;
	}

	public List<ConsigneeContact> getConsigneeContacts() {
		return consigneeContacts;
	}

	public void setConsigneeContacts(List<ConsigneeContact> consigneeContacts) {
		this.consigneeContacts = consigneeContacts;
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
