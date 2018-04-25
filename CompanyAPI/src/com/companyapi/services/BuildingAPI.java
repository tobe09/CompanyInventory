package com.companyapi.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.building.BuildingService;


@Path("/building")
public class BuildingAPI extends ServiceAPI{

	private BuildingService bldSvc;
	
	public BuildingAPI() {
		bldSvc = new BuildingService();
	}

	@GET
	@Path("/retrieveMaterial")
	@Consumes(MediaType.APPLICATION_JSON)
	public String retrieveMaterial(@QueryParam("compName") String compName, 
			@QueryParam("mtrlName") String mtrlName, @QueryParam("quantity") int quantity){
		
		int status = bldSvc.retrieveMaterial(compName, mtrlName, quantity);			
		return gson.toJson(status);
	}
	
}
