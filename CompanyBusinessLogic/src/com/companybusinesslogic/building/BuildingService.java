package com.companybusinesslogic.building;

import com.companybusinesslogic.abstractions.Service;
import com.companybusinesslogic.report.ReportService;
import com.companymodel.model.Building;
import com.companymodel.model.Material;

public class BuildingService extends Service{

	private ReportService rSvc;
	
	public BuildingService(){
		rSvc = new ReportService();
	}

	public int retrieveMaterial(String companyName, String mtrlName, int quantity){
		Material material = new Material(mtrlName, 0, null, null, companyName);
		Building building = new Building(material, quantity, companyName);
		int status = rSvc.updateReport(building);
		
		return status;
	}
	
}
