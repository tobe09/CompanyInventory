package com.companybusinesslogic.abstractions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import com.companybusinesslogic.config.CompanyConfig;

public abstract class DataTransferObject {

	//method to execute a procedure given necessary values
	protected int executeProcedure(String command, Object ...values){
		Connection con = null;
		CallableStatement callableStatement = null;
		int output;		
		
		try {
			con = CompanyConfig.getConnection();
			callableStatement = con.prepareCall(command);
			 
			callableStatement = (CallableStatement)populateHelper(callableStatement, values);
			callableStatement.registerOutParameter(values.length + 1, Types.INTEGER); 
			
			callableStatement.executeUpdate();  
			
			output = callableStatement.getInt(values.length + 1);  
			
			con.close();
			callableStatement.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			output = -1;
		}  
		  
		return output;
	}
	
	//method to populate a prepared statement with values
	protected PreparedStatement populate(PreparedStatement stmnt, Object... values)throws SQLException{		
		stmnt = populateHelper(stmnt, values);		
		return stmnt;
	}
	
	private PreparedStatement populateHelper(PreparedStatement stmnt, Object[] values) throws SQLException{
		for(int i = 0; i < values.length; i++){
			Object value = values[i];
			
			if(value instanceof String)
				stmnt.setString(i + 1, (String)value);
			else if(value instanceof Integer)
				stmnt.setInt(i + 1, ((Integer)value).intValue());
			else if(value instanceof Double)
				stmnt.setDouble(i + 1, ((Double)value).doubleValue());
		}
		
		return stmnt;
	}
	
	//method to dispose of unnecessary resources
	protected void closeResources(Connection con, Statement stmt, ResultSet rs) throws SQLException{
		con.close();
		stmt.close();
		rs.close();
	}
	
	//format an sql date to a standard java date (with time)
	public static Date convertSqlToJavaDate(java.sql.Date sqlDate) {
        Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        
        return javaDate;
    }
	
}
