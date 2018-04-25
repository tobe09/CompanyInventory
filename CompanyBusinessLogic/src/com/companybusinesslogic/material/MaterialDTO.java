package com.companybusinesslogic.material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.companybusinesslogic.abstractions.DataTransferObject;
import com.companybusinesslogic.config.CompanyConfig;
import com.companymodel.abstractions.IMaterial;
import com.companymodel.model.Material;

public class MaterialDTO extends DataTransferObject implements IMaterialDAO {

	private String oneMaterialQuery = "SELECT * FROM MATERIALS WHERE COMPANY_NAME = ? AND NAME = ?";
	private String allMaterialsQuery = "SELECT * FROM MATERIALS WHERE COMPANY_NAME = ?";
	private String insertCommand = "{call INSERT_MATERIAL(?, ?, ?, ?, ?, ?)}";
	private String updateCommand = "{call UPDATE_MATERIAL(?, ?, ?, ?, ?, ?)}";

	@Override
	public Material getMaterialOrNull(String companyName, String mtrlName){
		Material material = null;
		
		try{
			Connection con = CompanyConfig.getConnection();
			PreparedStatement stmt = con.prepareStatement(oneMaterialQuery);
			stmt = populate(stmt, companyName.toUpperCase(), mtrlName.toUpperCase());
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				String name = rs.getString("NAME");
				double pricePerUnit = rs.getDouble("PRICE_PER_UNIT");
				String type = rs.getString("TYPE");
				String vendor = rs.getString("VENDOR");
				material = new Material(name, pricePerUnit, type, vendor, companyName);
			}			
			
			closeResources(con, stmt, rs);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	    
	    return material;		
	}

	@Override
	public ArrayList<IMaterial> getAllMaterials(String companyName) {
		ArrayList<IMaterial> materials = new ArrayList<>();
		
		try{
			Connection con = CompanyConfig.getConnection();
			PreparedStatement stmnt = con.prepareStatement(allMaterialsQuery);
			stmnt = populate(stmnt, companyName.toUpperCase());
			
			ResultSet rs = stmnt.executeQuery();
			
			while(rs.next()){
				String name = rs.getString("NAME");
				double pricePerUnit = rs.getDouble("PRICE_PER_UNIT");
				String type = rs.getString("TYPE");
				String vendor = rs.getString("VENDOR");
				String compName = rs.getString("COMPANY_NAME");
				IMaterial material = new Material(name, pricePerUnit, type, vendor, compName);
				materials.add(material);
			}

			closeResources(con, stmnt, rs);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return materials;
	}	

	@Override
	public Material createMaterial(String name, double pricePerUnit, String type, String vendor, String companyName){
		Material material = new Material(name, pricePerUnit, type, vendor, companyName);
		
		int status;
		Material checkMaterial = getMaterialOrNull(material.getCompanyName(), material.getName());		
		if(checkMaterial == null) {
			status = saveMaterial(name, pricePerUnit, type, vendor, companyName);
		}
		else{
			status = updateMaterial(name, pricePerUnit, type, vendor, companyName);
		}
		material = status == 0 ? material : null;
		
		return material;
	}
	
	@Override
	public int saveMaterial(String name, double pricePerUnit, String type, String vendor, String companyName){
		int status = executeProcedure(insertCommand, companyName.toUpperCase(), name.toUpperCase(),
				pricePerUnit, type, vendor);
		return status;
	}	

	@Override
	public int updateMaterial(String name, double pricePerUnit, String type, String vendor, String companyName){
		int status = executeProcedure(updateCommand, companyName.toUpperCase(), name.toUpperCase(),
				pricePerUnit, type, vendor);
		return status;
	}
}
