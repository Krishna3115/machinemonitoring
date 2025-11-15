package com.tblmonitoring.tblmonitor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consignee_contacts")
public class ConsigneeContact {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String designation;

    @Column(length = 10)
    private String mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;
    
    @Column(name = "consignee_division", length = 255)
    private String division;

    @Column(name = "consignee_section", length = 255)
    private String section;
    
    public ConsigneeContact() {
		// TODO Auto-generated constructor stub
	}

	

	public ConsigneeContact( String name, String designation, String mobile, PurchaseOrder purchaseOrder,
			String division, String section) {
		super();
	//	this.id = id;
		this.name = name;
		this.designation = designation;
		this.mobile = mobile;
		this.purchaseOrder = purchaseOrder;
		this.division = division;
		this.section = section;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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
