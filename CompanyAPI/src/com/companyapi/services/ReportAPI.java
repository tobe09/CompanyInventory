package com.companyapi.services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.report.ReportService;
import com.companymodel.model.ReportInfo;


@Path("/report")
public class ReportAPI extends ServiceAPI {

	private ReportService rptSvc;
	
	public ReportAPI(){
		rptSvc = new ReportService();
	}

	@GET
	@Path("/getMaterialRemaining")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMaterialRemaining(@QueryParam("compName") String compName, 
			@QueryParam("mtrlName") String mtrlName) {		
		
		int quantity = rptSvc.getMaterialRemaining(compName, mtrlName);
		return gson.toJson(quantity);
	}

	@GET
	@Path("/getAllReports")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllReports(@QueryParam("compName") String compName) {		
		
		ArrayList<ReportInfo> reports = rptSvc.getAllReports(compName);
		return gson.toJson(reports);
	}

}
