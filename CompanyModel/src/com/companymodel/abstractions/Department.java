package com.companymodel.abstractions;

import com.companymodel.enumerations.Action;

public abstract class Department {

	private String name;		
	private IMaterial material;	
	private int quantity;
	private Action action;
	private String companyName;

	protected Department(IMaterial material, int quantity, String companyName){
		this.material = material;
		this.quantity = quantity;
		this.companyName = companyName;
	}
	
	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public IMaterial getMaterial() {
		return material;
	}

	public void setMaterial(IMaterial material) {
		this.material = material;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getCompanyName() {
		return companyName.toUpperCase();
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
