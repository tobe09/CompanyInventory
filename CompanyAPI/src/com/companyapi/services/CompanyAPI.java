package com.companyapi.services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.company.CompanyService;
import com.companymodel.model.Company;


@Path("/company")
public class CompanyAPI extends ServiceAPI {

	private CompanyService compSvc;
	
	public CompanyAPI(){
		compSvc = new CompanyService();
	}

	@GET
	@Path("/getTest")
	@Consumes(MediaType.APPLICATION_XML)
	public String getTest(@QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("type") String type){
		
		Company company = new Company(name, address, type);
		return gson.toJson(company);
	}

	@GET
	@Path("/getCompanyOrNull")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getCompanyOrNull(@QueryParam("compName") String compName){
		
		Company company = compSvc.getCompanyOrNull(compName);
		return gson.toJson(company);
	}

	@GET
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createCompany(@QueryParam("compName") String compName,
			@QueryParam("address") String address, @QueryParam("type") String type){
		
		Company company = compSvc.createCompany(compName, address, type);
		return gson.toJson(company);
	}

	@GET
	@Path("/getAllCompanyNames")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllCompanyNames(){
		ArrayList<String> company = compSvc.getAllCompanyNames(); 
		return gson.toJson(company);
	}
}
