package com.companymodel.model;

import com.companymodel.abstractions.IMaterial;

public class Material implements IMaterial{
	
	private String name;
	private double pricePerUnit;
	private String type;
	private String vendor;
	private String companyName;

	public Material(String name, double pricePerUnit, String type, String vendor, String companyName){
		this.name = name;
		this.pricePerUnit = pricePerUnit;
		this.type = type;
		this.vendor = vendor;
		this.companyName = companyName;
	}
	
	@Override
	public String getName() {
		return name.toUpperCase();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPricePerUnit() {
		return pricePerUnit;
	}

	@Override
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getVendor() {
		return vendor;
	}

	@Override
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public String getCompanyName() {
		return companyName.toUpperCase();
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
