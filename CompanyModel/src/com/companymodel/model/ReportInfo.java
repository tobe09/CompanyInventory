package com.companymodel.model;

import java.util.Date;

public class ReportInfo {

	private String materialName;
	private double pricePerUnit;
	private String type;
	private String vendor;
	private Date dateCreated;
	private int amountRecieved;
	private int amountDisbursed;
	private int amountRemaining;
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getAmountRecieved() {
		return amountRecieved;
	}
	public void setAmountRecieved(int amountRecieved) {
		this.amountRecieved = amountRecieved;
	}
	public int getAmountDisbursed() {
		return amountDisbursed;
	}
	public void setAmountDisbursed(int amountDisbursed) {
		this.amountDisbursed = amountDisbursed;
	}
	public int getAmountRemaining() {
		return amountRemaining;
	}
	public void setAmountRemaining(int amountRemaining) {
		this.amountRemaining = amountRemaining;
	}
	
}
