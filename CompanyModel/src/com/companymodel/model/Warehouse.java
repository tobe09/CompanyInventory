package com.companymodel.model;

import com.companymodel.abstractions.Department;
import com.companymodel.abstractions.IMaterial;
import com.companymodel.enumerations.Action;

public class Warehouse extends Department {

	public Warehouse(IMaterial material, int quantity, String companyName) {
		super(material, quantity, companyName);
		setName("Warehousing");
		setAction(Action.ADD);
	}

}
