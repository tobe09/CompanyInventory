package com.companymodel.model;

import com.companymodel.abstractions.Department;

public class Report extends Department {
	
	public Report(Department department) {
		super(department.getMaterial(), department.getQuantity(), department.getCompanyName());
		setName("Reports");
		setAction(department.getAction());
	}
	
}
