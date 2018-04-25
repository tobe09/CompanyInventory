package com.companybusinesslogic.company;

import java.util.ArrayList;

import com.companybusinesslogic.abstractions.Service;
import com.companymodel.abstractions.ICompany;
import com.companymodel.model.Company;

public class CompanyService extends Service {
	
	private ICompanyDAO cDao;
	
	public CompanyService(){
		cDao = new CompanyDTO();
	}
	
	public Company getCompanyOrNull(String compName){
		Company company = cDao.getCompanyOrNull(compName.toUpperCase());
		return company;
	}	
	
	public ArrayList<String> getAllCompanyNames(){
		ArrayList<String> names = new ArrayList<>();
		
		ArrayList<ICompany> companies = cDao.getAllCompanies();
		for(ICompany company: companies){
			String name = company.getName();
			names.add(name);
		}
		
		return names;
	}
	
	public Company createCompany(String name, String address, String type){
		Company company = cDao.createCompany(name.toUpperCase(), address, type);
		return company;
	}
		
}
