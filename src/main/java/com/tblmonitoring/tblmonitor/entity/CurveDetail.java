package com.tblmonitoring.tblmonitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curve_details")
public class CurveDetail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String poNumber;
    private String curveNo;
    private String blockSection;
    private String railSection;
    private String lhRh;
    private String poleNo;
    private Integer kmFrom;
    private Integer metFrom;
    private Integer kmTo;
    private Integer metTo;
    private Double length;
    private Double degree;
    private String pwiSection;

    
    public CurveDetail() {
		// TODO Auto-generated constructor stub
	}


	public CurveDetail(Long id, String poNumber, String curveNo, String blockSection, String railSection, String lhRh,
			String poleNo, Integer kmFrom, Integer metFrom, Integer kmTo, Integer metTo, Double length, Double degree,
			String pwiSection) {
		super();
		this.id = id;
		this.poNumber = poNumber;
		this.curveNo = curveNo;
		this.blockSection = blockSection;
		this.railSection = railSection;
		this.lhRh = lhRh;
		this.poleNo = poleNo;
		this.kmFrom = kmFrom;
		this.metFrom = metFrom;
		this.kmTo = kmTo;
		this.metTo = metTo;
		this.length = length;
		this.degree = degree;
		this.pwiSection = pwiSection;
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


	public String getCurveNo() {
		return curveNo;
	}


	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}


	public String getBlockSection() {
		return blockSection;
	}


	public void setBlockSection(String blockSection) {
		this.blockSection = blockSection;
	}


	public String getRailSection() {
		return railSection;
	}


	public void setRailSection(String railSection) {
		this.railSection = railSection;
	}


	public String getLhRh() {
		return lhRh;
	}


	public void setLhRh(String lhRh) {
		this.lhRh = lhRh;
	}


	public String getPoleNo() {
		return poleNo;
	}


	public void setPoleNo(String poleNo) {
		this.poleNo = poleNo;
	}


	public Integer getKmFrom() {
		return kmFrom;
	}


	public void setKmFrom(Integer kmFrom) {
		this.kmFrom = kmFrom;
	}


	public Integer getMetFrom() {
		return metFrom;
	}


	public void setMetFrom(Integer metFrom) {
		this.metFrom = metFrom;
	}


	public Integer getKmTo() {
		return kmTo;
	}


	public void setKmTo(Integer kmTo) {
		this.kmTo = kmTo;
	}


	public Integer getMetTo() {
		return metTo;
	}


	public void setMetTo(Integer metTo) {
		this.metTo = metTo;
	}


	public Double getLength() {
		return length;
	}


	public void setLength(Double length) {
		this.length = length;
	}


	public Double getDegree() {
		return degree;
	}


	public void setDegree(Double degree) {
		this.degree = degree;
	}


	public String getPwiSection() {
		return pwiSection;
	}


	public void setPwiSection(String pwiSection) {
		this.pwiSection = pwiSection;
	}
    
    
}
