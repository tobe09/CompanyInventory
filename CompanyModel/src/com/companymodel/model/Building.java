package com.companymodel.model;

import com.companymodel.abstractions.Department;
import com.companymodel.abstractions.IMaterial;
import com.companymodel.enumerations.Action;

public class Building extends Department{

	public Building(IMaterial material, int quantity, String companyName) {
		super(material, quantity, companyName);
		setName("Building");
		setAction(Action.REMOVE);
	}

}
