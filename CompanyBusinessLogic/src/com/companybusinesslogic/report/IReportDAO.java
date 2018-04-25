package com.companybusinesslogic.report;

import java.util.ArrayList;
import java.util.HashMap;

import com.companymodel.abstractions.IMaterial;

public interface IReportDAO {
	
	int getMaterialRemaining(String companyName, String mtrlName);
	
	 ArrayList<IMaterial> getAllMaterials(String companyName);
	 
	 HashMap<String, Object> getMaterialInfo(String companyName, String mtrlName);
	
	int updateReport(String companyName, String mtrlName, int quantity, String action);
	
}
