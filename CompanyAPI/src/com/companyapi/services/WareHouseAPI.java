package com.companyapi.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.warehouse.WareHouseService;


@Path("/warehouse")
public class WareHouseAPI  extends ServiceAPI {

	private WareHouseService whsSvc;
	
	public WareHouseAPI(){
		whsSvc = new WareHouseService();
	}
	
	@GET
	@Path("/saveMaterial")
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveMaterial(@QueryParam("compName") String compName, 
			@QueryParam("mtrlName") String mtrlName, @QueryParam("quantity") int quantity) {	
		
		int status = whsSvc.saveMaterial(compName, mtrlName, quantity);
		return gson.toJson(status);
	}
	
}
