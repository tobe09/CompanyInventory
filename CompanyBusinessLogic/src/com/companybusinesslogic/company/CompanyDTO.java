package com.companybusinesslogic.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.companybusinesslogic.abstractions.DataTransferObject;
import com.companymodel.abstractions.ICompany;
import com.companymodel.model.Company;
import com.companybusinesslogic.config.CompanyConfig;

class CompanyDTO extends DataTransferObject implements ICompanyDAO{
	
	private String oneCompanyQuery = "SELECT * FROM COMPANIES WHERE NAME = ?";
	private String allCompaniesQuery = "SELECT * FROM COMPANIES";	
	private String insertCommand = "{call INSERT_COMPANY(?, ?, ?, ?)}";
	private String updateCommand = "{call UPDATE_COMPANY(?, ?, ?, ?)}";

	@Override
	public Company getCompanyOrNull(String compName) {
		Company company = null;

		try {
			Connection con = CompanyConfig.getConnection();
			PreparedStatement stmt = con.prepareStatement(oneCompanyQuery);
			stmt = populate(stmt, compName.toUpperCase());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String name = rs.getString("NAME");
				String address = rs.getString("ADDRESS");
				String type = rs.getString("TYPE");
				company = new Company(name, address, type);
			}
			
			closeResources(con, stmt, rs);	    
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return company;
	}

	@Override
	public ArrayList<ICompany> getAllCompanies() {
		ArrayList<ICompany> companies = new ArrayList<>();
		
		try{
			Connection con = CompanyConfig.getConnection();
			PreparedStatement stmt = con.prepareStatement(allCompaniesQuery);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				String name = rs.getString("NAME");
				String address = rs.getString("ADDRESS");
				String type = rs.getString("TYPE");
				ICompany company = new Company(name, address, type);
				companies.add(company);
			}

			closeResources(con, stmt, rs);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return companies;
	}	

	@Override
	public Company createCompany(String name, String address, String type){
		Company company = new Company(name, address, type);
		
		int status;
		Company checkCompany = getCompanyOrNull(company.getName());		
		if(checkCompany == null) {
			status = saveCompany(name.toUpperCase(), address, type);
		}
		else{
			status = updateCompany(name.toUpperCase(), address, type);			
		}
		company = status == 0 ? company : null;
		
		return company;
	}
	
	@Override
	public int saveCompany(String name, String address, String type){
		int status = executeProcedure(insertCommand, name, address, type);
		return status;
	}	

	@Override
	public int updateCompany(String name, String address, String type){
		int status = executeProcedure(updateCommand, name, address, type);
		return status;
	}
	
}
