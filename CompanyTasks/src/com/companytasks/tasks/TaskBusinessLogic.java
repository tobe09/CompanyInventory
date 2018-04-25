package com.companytasks.tasks;

import java.util.ArrayList;
import java.util.Scanner;

import com.companybusinesslogic.building.BuildingService;
import com.companybusinesslogic.company.CompanyService;
import com.companybusinesslogic.material.MaterialService;
import com.companybusinesslogic.report.ReportService;
import com.companybusinesslogic.warehouse.WareHouseService;
import com.companymodel.model.Building;
import com.companymodel.model.Company;
import com.companymodel.model.Material;
import com.companymodel.model.ReportInfo;
import com.companymodel.model.Warehouse;

public class TaskBusinessLogic {
	
	private static CompanyService compSvc = new CompanyService();
	private static MaterialService mtrlSvc = new MaterialService();
	private static WareHouseService whsSvc = new WareHouseService();
	private static BuildingService bldSvc = new BuildingService();
	private static ReportService rptSvc = new ReportService(); 

	public static void main(String[] args){			
		System.out.println("Application to Handle Inventory Management for Companies\r\n");

		Scanner sc = new Scanner(System.in);
		
		Company company = initCompany(sc);
		
		String repeat = "Y";
		
		while(repeat.toUpperCase() == "Y" || repeat.toUpperCase() == "YES"){			
			System.out.println("What action do you wish to perform");
			System.out.print("Enter '1' to save materials to inventory,\r\n" +
					"'2' to retrieve materials from inventory and \r\n" +
					"'3' to generate report on inventory: ");
			int code = TaskForAPI.validInt(sc, 1, 3, "Enter a valid choice code: ");
			
			if(code == 1) handleWareHousing(company.getName(), sc);
			else if(code == 2) handleBuilding(company.getName(), sc);
			else handleReporting(company.getName(), sc);
			
			System.out.print("\r\nDo you wish to perform another transaction? \r\n" +
					"Enter 'Y' for Yes and 'N' for No: ");
			repeat = sc.nextLine();
			if(repeat.toUpperCase() == "Y" || repeat.toUpperCase() == "YES"){
				System.out.print("\r\nDo you wish to choose another company? \r\n" +
						"Enter 'Y' for Yes and 'N' for No: ");
				String choice = sc.nextLine();
				if(choice.toUpperCase() == "Y" || choice.toUpperCase() == "YES"){
					company = initCompany(sc);
				}
			}
		}
		
		sc.close();
	}
	
	private static Company initCompany(Scanner sc){
		Company company;
		
		System.out.print("New or Existing Company?  \r\nEnter 1 for new, 2 for existing: ");
		int code = TaskForAPI.validInt(sc, 1, 2, "Enter a valid choice code: ");
		
		String name;
		if(code == 1){
			System.out.print("Enter Company Name: ");
			name = sc.nextLine();
			System.out.print("Enter Company Address: ");
			String address = sc.nextLine();
			System.out.print("Enter Company Type/Category: ");
			String type = sc.nextLine();
			
			company = compSvc.createCompany(name, address, type);
			System.out.println(name + " Company has been created\r\n");
		}
		else{
			System.out.print("Enter Company Name: ");
			name = sc.nextLine();			
			company = compSvc.getCompanyOrNull(name);
			
			if(company == null){
				ArrayList<String> companyNames = compSvc.getAllCompanyNames();
				System.out.println("Companies Available: ");
				TaskForAPI.printList(companyNames);
			}
			while(company == null){
				System.out.print("Enter a valid company Name: ");
				name = sc.nextLine();				
				company = compSvc.getCompanyOrNull(name);			
			}
			System.out.println(name + " Company has been selected\r\n");
		}
		
		return company;
	}
	
	private static void handleWareHousing(String companyName, Scanner sc){	
		Material material = null;
		
		System.out.println("Enter Details of Material to Save Below: ");	
		System.out.print("Enter Material Name: ");
		String name = sc.nextLine();
		
		material = mtrlSvc.getMaterialOrNull(companyName, name);
		if(material == null) {
			System.out.print("Enter Price Per Unit of Material: ");
			double ppu = TaskForAPI.validDouble(sc, 0.01, 100000000, "Enter a Valid Price Per Unit: ");
			System.out.print("Enter Material Type: ");
			String type = sc.nextLine();		
			System.out.print("Enter Material Vendor: ");
			String vendor = sc.nextLine();		
			
			material = mtrlSvc.createMaterial(name, ppu, type, vendor, companyName);	
						
			if(material != null) {
				System.out.println("New material created successfully");
			}
			else{	
				System.out.println("Error occured while creating material");
				return;
			}
		}
		else{
			System.out.println("Price Per Unit of Material: " + material.getPricePerUnit());
			System.out.println("Material Type: " + material.getType());
			System.out.println("Material Vendor: " + material.getVendor());
		}
		
		System.out.print("Enter quantity of material you wish to save: ");
		int quantity = TaskForAPI.validInt(sc, 0, 100000000, "Enter a valid quantity: ");
		
	    Warehouse warehouse = new Warehouse(material, quantity, companyName);
		int status = whsSvc.saveMaterial(companyName, name, quantity);
		
		if(status == 0){
			System.out.println(quantity+" "+warehouse.getMaterial().getName()+
					" Material(s) Successfully Added to the Inventory");
		}
		else{
			System.out.println("Error occured while updating inventory");
		}	
	}

	private static void handleBuilding(String companyName, Scanner sc){	
		Material material = null;
		
		System.out.println("Enter Name of Material to Retrieve Below: ");	
		material = validateMaterial(companyName, sc);

		System.out.print("Enter quantity of material you wish to retrieve: ");
		int quantity = TaskForAPI.validInt(sc, 0, 100000000, "Enter a valid quantity: ");
		
		Building building = new Building(material, quantity, companyName);
		int status = bldSvc.retrieveMaterial(companyName, building.getMaterial().getCompanyName(),
				building.getQuantity());
		
		if(status == 0){
			System.out.println(quantity + " " + building.getMaterial().getName() +
					" Material(s) Successfully Retrieved from the Inventory");
		}
		else{
			System.out.println("Error occured while retrieving materials");
		}	
	}

	private static void handleReporting(String companyName, Scanner sc){
		System.out.print("Enter '1' to get information about material and\r\n" +
				"'2' to generate inventory report");
		int code = TaskForAPI.validInt(sc, 1, 2, "Enter a valid choice code: ");
		
		if(code == 1){
			System.out.println("Enter Material Name: ");			
			Material material = validateMaterial(companyName, sc);
				
			int quantity = rptSvc.getMaterialRemaining(companyName, material.getName());			

			System.out.println("Material Details are as Follows");
			System.out.println("Material Name: " + material.getName());
			System.out.println("Price Per Unit of Material: " + material.getPricePerUnit());
			System.out.println("Material Type: " + material.getType());
			System.out.println("Material Vendor: " + material.getVendor());
			System.out.println("Quantity of Material Remaining: " + quantity);			
		}
		else{
			System.out.println("Inventory Information");
			
			ArrayList<ReportInfo> reports = rptSvc.getAllReports(companyName);
			
			TaskForAPI.printReport(reports);
		}		
	}
	
	private static Material validateMaterial(String companyName, Scanner sc){
		Material material = null;
		System.out.print("Enter Material Name: ");
		String name = sc.nextLine();
		
		material = mtrlSvc.getMaterialOrNull(companyName, name);
		
		if(material == null){
			ArrayList<String> materialNames = mtrlSvc.getAllMaterialNames(companyName);
			System.out.println("Materials Available: ");
			TaskForAPI.printList(materialNames);
		}
		while(material == null){
			System.out.print("Enter a valid Material Name: ");
			name = sc.nextLine();				
			material = mtrlSvc.getMaterialOrNull(companyName, name);
		}
		
		return material;
	}
	
}
