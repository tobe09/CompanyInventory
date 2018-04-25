package com.companytasks.tasks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.Type;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.companymodel.model.Building;
import com.companymodel.model.Company;
import com.companymodel.model.Material;
import com.companymodel.model.ReportInfo;
import com.companymodel.model.Warehouse;
import com.companytasks.helper.Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TaskForAPI {

	private static Client client;
	private static Gson gson;
	
	private static String cSvcUrl = getBaseAddress()+ "company/";
	private static String mSvcUrl = getBaseAddress()+ "material/";
	private static String wSvcUrl = getBaseAddress()+ "warehouse/";
	private static String bSvcUrl = getBaseAddress()+ "building/";
	private static String rSvcUrl = getBaseAddress()+ "report/";
	
	private static String getBaseAddress(){
		String address = "http://localhost:2018/CompanyAPI/rest/";		
		return address;
	}

	public static void main(String[] args){			
		System.out.println("Application to Handle Inventory Management for Companies\r\n");
		
		client = ClientBuilder.newClient(); 
		gson = new GsonBuilder().create();
		
		Scanner sc = new Scanner(System.in);
		
		Company company = initCompany(sc);
		
		String repeat = "Y";
		
		while(repeat.toUpperCase().equals("Y") || repeat.toUpperCase().equals("YES")){	
			System.out.println("\r\n" + company.getName() + " Company has been selected");
			
			System.out.println("\r\nWhat action do you wish to perform");
			System.out.print("'1' to save materials to inventory, \r\n" +
					"'2' to retrieve materials from inventory or \r\n" +
					"'3' to generate report on inventory: ");
			int code = validInt(sc, 1, 3, "Enter a valid choice code: ");
			
			if(code == 1) handleWareHousing(company.getName(), sc);
			else if(code == 2) handleBuilding(company.getName(), sc);
			else handleReporting(company.getName(), sc);
			
			System.out.print("\r\nDo you wish to perform another transaction? \r\n" +
					"Enter 'Y' for Yes or 'N' for No: ");
			repeat = sc.nextLine();
			if(repeat.toUpperCase().equals("Y") || repeat.toUpperCase().equals("YES")){
				System.out.print("\r\nDo you wish to choose another company? \r\n" +
						"Enter 'Y' for Yes or 'N' for No: ");
				String choice = sc.nextLine();
				if(choice.toUpperCase().equals("Y") || choice.toUpperCase().equals("YES")){
					company = initCompany(sc);
				}
			}
		}
		System.out.println("\r\nInventory Session Ended");
		
		sc.close();
	}
	
	private static Company initCompany(Scanner sc){
		Company company = null;

		System.out.print("\r\nNew or Existing Company?  \r\nEnter 1 for new or\r\n" +
				"2 for existing: ");
		int code = validInt(sc, 1, 2, "Enter a valid choice code: ");
		
		String name, json;
		if(code == 1){
			System.out.print("\r\nEnter Company Name: ");
			name = sc.nextLine();
			System.out.print("Enter Company Address: ");
			String address = sc.nextLine();
			System.out.print("Enter Company Type/Category: ");
			String type = sc.nextLine();
			
			json = getJsonString(cSvcUrl + "createCompany", "compName", name, 
					"address", address, "type", type);
			company = gson.fromJson(json, Company.class);
		}
		else{
			System.out.print("\r\nEnter Company Name: ");
			name = sc.nextLine();			
			json = getJsonString(cSvcUrl + "getCompanyOrNull", "compName", name);
			company = (Company)gson.fromJson(json, Company.class);
			
			if(company == null){
				json = getJsonString(cSvcUrl + "getAllCompanyNames");
				@SuppressWarnings("unchecked")
				ArrayList<String> companyNames = (ArrayList<String>)gson.fromJson(json, ArrayList.class);
				System.out.println("Companies Available: ");
				boolean status = printList(companyNames);
				
				if(!status) company = initCompany(sc);
			}
			while(company == null){
				System.out.print("Enter a valid company Name: ");
				name = sc.nextLine();				
				json = getJsonString(cSvcUrl + "getCompanyOrNull", "compName", name);
				company = (Company)gson.fromJson(json, Company.class);				
			}
		}
		
		return company;
	}
	
	private static void handleWareHousing(String companyName, Scanner sc){	
		Material material = null;
		
		System.out.println("\r\nEnter Details of Material to Save Below");	
		System.out.print("Enter Material Name: ");
		String name = sc.nextLine();
		
		String json = getJsonString(mSvcUrl + "getMaterialOrNull", "compName", companyName,
				"mtrlName", name);
		material = (Material)gson.fromJson(json, Material.class);
		if(material == null) {
			System.out.print("Enter Price Per Unit of Material: ");
			double ppu = validDouble(sc, 0.01, 100000000, "Enter a Valid Price Per Unit: ");
			System.out.print("Enter Material Type: ");
			String type = sc.nextLine();		
			System.out.print("Enter Material Vendor: ");
			String vendor = sc.nextLine();		
			
			json = getJsonString(mSvcUrl + "createMaterial", "compName", companyName, 
					"mtrlName", name, "pricePerUnit", ppu + "", "type", type, "vendor", vendor);		
			material = (Material)gson.fromJson(json, Material.class);	
						
			if(material == null) {
				System.out.println("Error occured while creating material");
				return;
			}
			
			System.out.println("New material created successfully");
		}
		else{
			System.out.println("Price Per Unit of Material: " + material.getPricePerUnit());
			System.out.println("Material Type: " + material.getType());
			System.out.println("Material Vendor: " + material.getVendor());
		}
		
		System.out.print("Enter quantity of material you wish to save: ");
		int quantity = validInt(sc, 0, 10000, "Enter a valid quantity: ");
		
	    Warehouse warehouse = new Warehouse(material, quantity, companyName);
		json = getJsonString(wSvcUrl + "saveMaterial", "compName", companyName,
				"mtrlName", warehouse.getMaterial().getName(),
				"quantity", warehouse.getQuantity() + "");		
		int status = (Integer)gson.fromJson(json, Integer.class);
		
		if(status == 0){
			System.out.println("\r\n" + quantity + " " + warehouse.getMaterial().getName() +
					" Material(s) Successfully Added to the Inventory");
		}
		else{
			System.out.println("\r\nError occured while updating inventory");
		}	
	}

	private static void handleBuilding(String companyName, Scanner sc){	
		Material material = null;
		
		System.out.println("\r\nEnter Name of Material to Retrieve Below: ");	
		material = validateMaterial(companyName, sc);
		if(material == null) return;

		System.out.print("Enter quantity of material you wish to retrieve: ");
		int quantity = validInt(sc, 0, 100000000, "Enter a valid quantity: ");
		
		Building building = new Building(material, quantity, companyName);
		String json = getJsonString(bSvcUrl + "retrieveMaterial", "compName", companyName,
				"mtrlName", building.getMaterial().getName(), "quantity", building.getQuantity() + "");		
		int status = (Integer)gson.fromJson(json, Integer.class);
		if(status == 0){
			System.out.println("\r\n" + quantity + " " + building.getMaterial().getName() +
					" Material(s) Successfully Retrieved from the Inventory");
		}
		else{
			System.out.println("\r\nError occured while retrieving materials");
		}	
	}

	private static void handleReporting(String companyName, Scanner sc){
		System.out.print("\r\nEnter '1' to get information about a material or \r\n" +
				"'2' to generate inventory report: ");
		int code = validInt(sc, 1, 2, "Enter a valid choice code: ");
		
		if(code == 1){			
			Material material = validateMaterial(companyName, sc);
			if(material == null) return;
			
			String json = getJsonString(rSvcUrl + "getMaterialRemaining", "compName",
					companyName, "mtrlName", material.getName());		
			int quantity = gson.fromJson(json, Integer.class);
						
			System.out.println("\r\nMaterial Details are as Follows: ");
			System.out.println("Material Name: " + material.getName());
			System.out.println("Price Per Unit of Material: " + material.getPricePerUnit());
			System.out.println("Material Type: " + material.getType());
			System.out.println("Material Vendor: " + material.getVendor());
			System.out.println("Quantity of Material Remaining: " + quantity);			
		}
		else{
			System.out.println("\r\nInventory Information");
			
			String json = getJsonString(rSvcUrl + "getAllReports", "compName", companyName);

			Type listType = new TypeToken<ArrayList<ReportInfo>>(){}.getType();
			ArrayList<ReportInfo> reports = gson.fromJson(json, listType);
						
			printReport(reports);
		}		
	}
	
	private static Material validateMaterial(String companyName, Scanner sc){
		Material material = null;
		System.out.print("\r\nEnter Material Name: ");
		String name = sc.nextLine();
		
		String json = getJsonString(mSvcUrl + "getMaterialOrNull", "compName", companyName,
				"mtrlName", name);
		material = gson.fromJson(json, Material.class);
		
		if(material == null){
			json = getJsonString(mSvcUrl + "getAllMaterialNames", "compName", companyName);
			@SuppressWarnings("unchecked")
			ArrayList<String> materialNames = (ArrayList<String>)gson.fromJson(json, ArrayList.class);
			System.out.println("Material(s) Available: ");
			boolean status = printList(materialNames);
			if(!status) return null;
		}
		while(material == null){
			System.out.print("Enter a valid Material Name: ");
			name = sc.nextLine();				
			json = getJsonString(mSvcUrl + "getMaterialOrNull", "compName", companyName,
				"mtrlName", name);
			material = (Material)gson.fromJson(json, Material.class);				
		}
		
		return material;
	}
	
	private static String getJsonString(String url, String... queryParamsKeyValue){
		WebTarget target = client.target(url);
		for(int i = 0; i < queryParamsKeyValue.length; i += 2){
			target = target.queryParam(queryParamsKeyValue[i], queryParamsKeyValue[i + 1]);
		}
		
		String jsonString = target.request(MediaType.APPLICATION_JSON).get(String.class);

		return jsonString;
	}
	
	static boolean printList(ArrayList<String> values){
		if(values.size() == 0){
			System.out.println("No data available");
			return false;
		}
		for(int i = 0; i < values.size(); i++){
			System.out.println((i + 1) + ". " + values.get(i));			
		}
		
		return true;
	}
	
	static void printReport(ArrayList<ReportInfo> reports){
		if(reports.size() == 0){
			System.out.println("No Material in the Inventory. \r\n");
			return;
		}
		int sn = 1;
		for(ReportInfo report: reports){
			String formattedPrice = FormatToNoOfDP(report.getPricePerUnit(), 2);
			System.out.println("\r\n" + sn + ". Material Name: "+report.getMaterialName()+", \r\n" +
					"Price Per Unit: "+formattedPrice+", \r\n" +
					"Type: "+report.getType()+", \r\n" +
					"Vendor: "+report.getVendor()+"\r\n" +
					"Date Created: "+report.getDateCreated()+", \r\n"+
					"Amount Recieved: "+report.getAmountRecieved()+", \r\n"+
					"Amount Disbursed: "+report.getAmountDisbursed()+", \r\n"+
					"Amount Remaining: "+report.getAmountRemaining()+"\r\n");
			sn++;
		}
	}
	
	static int validInt(Scanner sc, int min, int max, String prompt){
		int code = Integer.MIN_VALUE;
		
		String codeStr = sc.nextLine();
		while (code < min || code > max){
			code = Helper.validateInt(codeStr, prompt, sc);
			codeStr = "arbitrary value";
		}
		
		return code;
	}
	
	static double validDouble(Scanner sc, double min, double max, String prompt){
		double code = Integer.MIN_VALUE;
		
		String valueStr = sc.nextLine();
		while (code < min || code > max){
			code = Helper.validateDouble(valueStr, prompt, sc);
			valueStr = "arbitrary value";
		}		
		
		return code;		
	}

	static String FormatToNoOfDP(double value, int dp){
		String zeros = "0";
		for(int i = 1; i < dp ; i++) zeros += "0";
		DecimalFormat df = new DecimalFormat("#0." + zeros);
		
		return df.format(value);
	}
}
