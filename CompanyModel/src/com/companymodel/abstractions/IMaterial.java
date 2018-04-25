package com.companymodel.abstractions;

public interface IMaterial {
	
	String getName();
	
	void setName(String name);
	
	double getPricePerUnit();
	
	void setPricePerUnit(double pricePerUnit);

	String getType();
	
	void setType(String type);

	String getVendor();
	
	void setVendor(String vendor);
	
	String getCompanyName();
	
	void setCompanyName(String companyName);

}
