package com.companybusinesslogic.report;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import com.companybusinesslogic.abstractions.Service;
import com.companymodel.abstractions.Department;
import com.companymodel.abstractions.IMaterial;
import com.companymodel.model.ReportInfo;

public class ReportService extends Service{
	
	private IReportDAO rDao;
	
	public ReportService() {
		rDao = new ReportDTO();
	}
	
	public int getMaterialRemaining(String companyName, String mtrlName) {
		int count = rDao.getMaterialRemaining(companyName, mtrlName);
		return count;		
	}

	public ArrayList<ReportInfo> getAllReports(String compName){
		ArrayList<ReportInfo> reports = new ArrayList<>();
		
		ArrayList<IMaterial> materials = rDao.getAllMaterials(compName);
		
		for(IMaterial material: materials){
			ReportInfo report = new ReportInfo();
			report.setMaterialName(material.getName());
			report.setPricePerUnit(material.getPricePerUnit());
			report.setType(material.getType());
			report.setVendor(material.getVendor());
			
			HashMap<String, Object> values = rDao.getMaterialInfo(compName, material.getName());
			report.setDateCreated((Date)values.get("DATE_CREATED"));
			report.setAmountRecieved((int)values.get("RECIEVED"));
			report.setAmountDisbursed((int)values.get("DISBURSED"));
			report.setAmountRemaining((int)values.get("REMAINING"));
			
			reports.add(report);
		}
		
		return reports;
	}

	public int updateReport(Department department){
		int status = rDao.updateReport(department.getCompanyName(), department.getMaterial().getName(),
				department.getQuantity(), department.getAction().toString());
		return status;
	}
	
}
