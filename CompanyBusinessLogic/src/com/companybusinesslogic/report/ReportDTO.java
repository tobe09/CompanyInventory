package com.companybusinesslogic.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.companybusinesslogic.abstractions.DataTransferObject;
import com.companybusinesslogic.config.CompanyConfig;
import com.companybusinesslogic.material.IMaterialDAO;
import com.companybusinesslogic.material.MaterialDTO;
import com.companymodel.abstractions.IMaterial;

public class ReportDTO extends DataTransferObject implements IReportDAO {
	
	private String remainingMtrlQuery = "SELECT QUANTITY FROM INVENTORY WHERE " +
			"COMPANY_NAME = ? AND MATERIAL_NAME = ?";
	private String updateCommand = "{call UPDATE_INVENTORY(?, ?, ?, ?, ?)}";
	private IMaterialDAO mdao = new MaterialDTO();
	
	private String getMaterialInfoQuery(String companyName, String mtrlName){
		String query = "SELECT (SELECT QUANTITY FROM INVENTORY "+
           "WHERE COMPANY_NAME = '" + companyName + "' AND MATERIAL_NAME = '" + mtrlName + "') "+
           "AS REMAINING," +
           "(SELECT CREATED_DATE FROM MATERIALS WHERE COMPANY_NAME = '" + companyName + 
           "' AND NAME = '" + mtrlName + "') AS DATE_CREATED, " +
           "(SELECT SUM (QUANTITY) FROM TRACK_ORDERS WHERE COMPANY_NAME = '" + companyName + 
           "' AND MATERIAL_NAME = '" + mtrlName + "' AND ACTION = 'ADD') AS RECIEVED, " +
           "(SELECT   SUM (QUANTITY) FROM TRACK_ORDERS WHERE COMPANY_NAME = '" + companyName + 
           "' AND MATERIAL_NAME = '" + mtrlName + "' AND ACTION = 'REMOVE') AS DISBURSED " +
           "FROM DUAL";
		
		return query;
	}
	
	@Override
	public int getMaterialRemaining(String companyName, String mtrlName) {
		int quantity = 0;
		
		try{
			Connection con = CompanyConfig.getConnection();
			PreparedStatement stmt = con.prepareStatement(remainingMtrlQuery);
			stmt = populate(stmt, companyName, mtrlName);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				quantity = rs.getInt("QUANTITY");
			}
			
			closeResources(con, stmt, rs);
		}
		catch(SQLException ex){
			ex.printStackTrace();
			quantity = -1;
		}
		
		return quantity;
	}

	@Override
	public ArrayList<IMaterial> getAllMaterials(String companyName){
		return mdao.getAllMaterials(companyName);
	}
		
	@Override
	public HashMap<String, Object> getMaterialInfo(String companyName, String mtrlName){
		HashMap<String, Object> values = new HashMap<>();
		
		try{
			Connection con = CompanyConfig.getConnection();
			String query = getMaterialInfoQuery(companyName.toUpperCase(), mtrlName.toUpperCase());
			PreparedStatement stmt = con.prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				Date crtdDate = convertSqlToJavaDate(rs.getDate("DATE_CREATED"));
				values.put("DATE_CREATED", crtdDate);
				values.put("RECIEVED", rs.getInt("RECIEVED"));
				values.put("DISBURSED", rs.getInt("DISBURSED"));
				values.put("REMAINING", rs.getInt("REMAINING"));
			}
			
			closeResources(con, stmt, rs);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return values;
	}
	
	@Override
	public int updateReport(String companyName, String mtrlName, int quantity, String action){
		int status = executeProcedure(updateCommand, companyName.toUpperCase(), 
				mtrlName.toUpperCase(), quantity, action);
		return status;
	}
	
}
