package com.companyapi.services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.companybusinesslogic.material.MaterialService;
import com.companymodel.model.Material;


@Path("/material")
public class MaterialAPI extends ServiceAPI {
	
	private MaterialService materialSvc;
	
	public MaterialAPI(){
		materialSvc = new MaterialService();
	}

	@GET
	@Path("/getMaterialOrNull")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMaterialOrNull(@QueryParam("compName") String compName, 
			@QueryParam("mtrlName") String mtrlName) {		
		
		Material material = materialSvc.getMaterialOrNull(compName, mtrlName);
		return gson.toJson(material);
	}

	@GET
	@Path("/createMaterial")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createMaterial(@QueryParam("compName") String compName, @QueryParam("mtrlName") String mtrlName, 
			@QueryParam("pricePerUnit") double pricePerUnit, @QueryParam("type") String type, 
			@QueryParam("vendor")String vendor) {	
		
		Material material = materialSvc.createMaterial(mtrlName, pricePerUnit, type, vendor, compName);			
		return gson.toJson(material);
	}

	@GET
	@Path("/getAllMaterialNames")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllMaterialNames(@QueryParam("compName") String compName) {
		
		ArrayList<String> names = materialSvc.getAllMaterialNames(compName.toUpperCase());
		return gson.toJson(names);
	}
	
}
