package com.companybusinesslogic.company;

import java.util.ArrayList;

import com.companymodel.abstractions.ICompany;
import com.companymodel.model.Company;

public interface ICompanyDAO {
	
	Company getCompanyOrNull(String compName);
	
	ArrayList<ICompany> getAllCompanies();
	
	Company createCompany(String name, String address, String type);
	
	int saveCompany(String name, String address, String type);
	
	int updateCompany(String name, String address, String type);
	
}
