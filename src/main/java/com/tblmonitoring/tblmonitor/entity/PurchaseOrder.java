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
    private LocalDate finaldispatchDate;
    private int quantity;
    private int warrantyMonths;
    private int maintenanceDays;
    private String erpoa;
    private Double perDayFine;
    
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConsigneeContact> consigneeContacts;
    
    public PurchaseOrder() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrder(Long id, String poNumber, LocalDate poDate, LocalDate finaldispatchDate, int quantity,
			int warrantyMonths, int maintenanceDays, String erpoa, Double perDayFine,
			List<ConsigneeContact> consigneeContacts) {
		super();
		this.id = id;
		this.poNumber = poNumber;
		this.poDate = poDate;
		this.finaldispatchDate = finaldispatchDate;
		this.quantity = quantity;
		this.warrantyMonths = warrantyMonths;
		this.maintenanceDays = maintenanceDays;
		this.erpoa = erpoa;
		this.perDayFine = perDayFine;
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

	public LocalDate getFinaldispatchDate() {
		return finaldispatchDate;
	}

	public void setFinaldispatchDate(LocalDate finaldispatchDate) {
		this.finaldispatchDate = finaldispatchDate;
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
    
}
