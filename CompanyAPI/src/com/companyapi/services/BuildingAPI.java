package com.companyapi.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.building.BuildingService;


@Path("/building")
@Consumes(MediaType.APPLICATION_JSON)
public class BuildingAPI extends ServiceAPI{

	private BuildingService bldSvc;
	
	public BuildingAPI() {
		bldSvc = new BuildingService();
	}

	@GET
	@Path("/retrieveMaterial")
	public String retrieveMaterial(@QueryParam("compName") String compName, 
			@QueryParam("mtrlName") String mtrlName, @QueryParam("quantity") int quantity){
		
		int status = bldSvc.retrieveMaterial(compName, mtrlName, quantity);			
		return gson.toJson(status);
	}

	//test java web services for databinding  //(Servlet.class error)
	@POST
	@Path("/getMaterial")
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //use with @FormParam
	public String getMaterial(String body, @QueryParam("list") List<Integer> list){	
		Material material = gson.fromJson(body, Material.class);
		return gson.toJson(material);
	}
	
}
