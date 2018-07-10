package com.companyapi.services;

import com.google.gson.Gson;

public class Material {
	private String compName;
	private String mtrlName;
	private int quantity;

	/*public String compName;
	public String mtrlName;
	public int quantity;*/
	
	public static Material valueOf(String str){
		Gson gson = new Gson();
		Material mtrl = gson.fromJson(str, Material.class);
		
		return mtrl;
	}
	
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getMtrlName() {
		return mtrlName;
	}
	public void setMtrlName(String mtrlName) {
		this.mtrlName = mtrlName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
