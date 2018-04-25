package com.companytasks.helper;

import java.util.Scanner;

public class Helper {
	
	public static int validateInt(String valueStr, String prompt, Scanner sc) {
		while (!tryParseInt(valueStr)){
			System.out.print(prompt);
			valueStr = sc.nextLine();			
		}
		
		return getInt(valueStr);
	}
	
	static boolean tryParseInt(String valueStr){
		boolean status = ((Boolean)checkStringInt(valueStr)[0]).booleanValue();
		return status;
	}
	
	static int getInt(String valueStr){
		int valueInt = ((Integer)checkStringInt(valueStr)[1]).intValue();
		return valueInt;
	}
	
	static Object[] checkStringInt(String valueStr){
		try{
			int valueInt = Integer.parseInt(valueStr);
			return new Object[]{new Boolean(true), new Integer(valueInt)};
		}
		catch(NumberFormatException ex){
			return new Object[]{new Boolean(false), new Integer(0)};
		}
	}
	
	public static double validateDouble(String valueStr, String prompt, Scanner sc) {
		while (!tryParseDouble(valueStr)){
			System.out.print(prompt);
			valueStr = sc.nextLine();			
		}
		
		return getDouble(valueStr);
	}
	
	static boolean tryParseDouble(String valueStr){
		boolean status = ((Boolean)checkStringDouble(valueStr)[0]).booleanValue();
		return status;
	}
	
	static double getDouble(String valueStr){
		double valueDouble = ((Double)checkStringDouble(valueStr)[1]).doubleValue();
		return valueDouble;
	}
	
	static Object[] checkStringDouble(String valueStr){
		try{
			double valueDouble = Integer.parseInt(valueStr);
			return new Object[]{new Boolean(true), new Double(valueDouble)};
		}
		catch(NumberFormatException ex){
			return new Object[]{new Boolean(false), new Double(0)};
		}
	}
	
}
