package com.companybusinesslogic.warehouse;

import com.companybusinesslogic.abstractions.Service;
import com.companybusinesslogic.report.ReportService;
import com.companymodel.model.Material;
import com.companymodel.model.Warehouse;

public class WareHouseService extends Service {
	
	private ReportService rSvc;
	
	public WareHouseService(){
		rSvc = new ReportService();
	}

	public int saveMaterial(String companyName, String mtrlName, int quantity){
		Material material = new Material(mtrlName, 0, null, null, companyName);
		Warehouse warehouse = new Warehouse(material, quantity, companyName);
		int status = rSvc.updateReport(warehouse);
		
		return status;
	}
	
}
