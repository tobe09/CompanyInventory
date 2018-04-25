package com.companybusinesslogic.tests;

import java.util.ArrayList;

import com.companybusinesslogic.building.BuildingService;
import com.companybusinesslogic.company.CompanyService;
import com.companybusinesslogic.material.MaterialService;
import com.companybusinesslogic.report.ReportService;
import com.companybusinesslogic.warehouse.WareHouseService;
import com.companymodel.model.Company;
import com.companymodel.model.Material;
import com.companymodel.model.ReportInfo;

public class TestServices {

	public static void main(String[] args){
		CompanyService compSvc = new CompanyService();
		Company comp = compSvc.createCompany("Neptune Software Group", "24 Park Lane, Apapa, Lagos", "Information Technology");
		ArrayList<String> compNames = compSvc.getAllCompanyNames();
		printList(compNames);
		
		MaterialService mtrlSvc = new MaterialService();
		Material mtrl = mtrlSvc.createMaterial("Keyboard", 500, "Input Device", "HP", comp.getName());
		ArrayList<String> mtrlNames = mtrlSvc.getAllMaterialNames(comp.getName());
		printList(mtrlNames);
		
		WareHouseService whs = new WareHouseService();
		int status = whs.saveMaterial(mtrl.getCompanyName(), mtrl.getName(), 50);
		System.out.println("Status: " + status);
		
		BuildingService bldSvc = new BuildingService();
		status = bldSvc.retrieveMaterial(mtrl.getCompanyName(), mtrl.getName(), 23);
		System.out.println("Status: " + status);
		
		ReportService rptSvc = new ReportService();
		status = rptSvc.getMaterialRemaining(mtrl.getCompanyName(), mtrl.getName());
		System.out.println("Quantity remaining: " + status);
		
		ArrayList<ReportInfo> reports = rptSvc.getAllReports(mtrl.getCompanyName());
		printReport(reports);
	}
	
	private static void printList(ArrayList<String> values){
		for(String value: values)
			System.out.print(value+", ");
		System.out.println("");
	}
	
	private static void printReport(ArrayList<ReportInfo> reports){
		for(ReportInfo report: reports){
			System.out.println("\r\nMaterial Name: "+report.getMaterialName()+", " +
					"Price Per Unit: "+report.getPricePerUnit()+", " +
					"Type: "+report.getType()+", Vendor: "+report.getVendor()+"\r\n" +
					"Date Created: "+report.getDateCreated()+", "+
					"Amount Recieved: "+report.getAmountRecieved()+", "+
					"Amount Disbursed: "+report.getAmountDisbursed()+", "+
					"Amount Remaining: "+report.getAmountRemaining()+"\r\n");
		}
	}

}
