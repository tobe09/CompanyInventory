package com.companyapi.services;

import com.google.gson.Gson;


public abstract class ServiceAPI {	
	
	protected Gson gson;
	
	protected ServiceAPI(){
		gson = new Gson();
	}
	
}
