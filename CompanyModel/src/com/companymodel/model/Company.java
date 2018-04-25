package com.companymodel.model;

import com.companymodel.abstractions.ICompany;

public class Company implements ICompany {
	
	private String name;
	private String address;
	private String type;
	
	public Company(String name, String address, String type) {
		this.name = name;
		this.address = address;
		this.type = type;
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
	public String getAddress() {
		return address;
	}
	
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}

}
