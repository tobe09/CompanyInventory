package com.companybusinesslogic.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.TimeZone;

public class CompanyConfig {

	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	private static final String connString = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String username = "TOBEDEMO";
	private static final String password = "tobedemo";
	
	public static String getDbUserName() {
		return username;
	}
	
    public static Connection getConnection() {
    	Connection connection =  null;
        try {
            Class.forName(driverName);
        	TimeZone timeZone = TimeZone.getTimeZone("GMT+0");
            TimeZone.setDefault(timeZone);
            connection = DriverManager.getConnection(
                    connString, username, password);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }

}
