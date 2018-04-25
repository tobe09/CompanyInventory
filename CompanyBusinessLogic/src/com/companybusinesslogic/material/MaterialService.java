package com.companybusinesslogic.material;

import java.util.ArrayList;

import com.companybusinesslogic.abstractions.Service;
import com.companymodel.abstractions.IMaterial;
import com.companymodel.model.Material;

public class MaterialService extends Service{
	
	private IMaterialDAO mDao;
	
	public MaterialService(){
		mDao = new MaterialDTO();
	}
	
	public Material getMaterialOrNull(String companyName, String mtrlName) {
		Material material = mDao.getMaterialOrNull(companyName, mtrlName);
		return material;
	}	

	public ArrayList<String> getAllMaterialNames(String companyName){
		ArrayList<String> names = new ArrayList<>();
		
		ArrayList<IMaterial> materials = mDao.getAllMaterials(companyName);
		for(IMaterial material: materials){
			String name = material.getName();
			names.add(name);
		}
		
		return names;
	}

	public Material createMaterial(String name, double pricePerUnit, String type, String vendor, String companyName) {
		Material material = mDao.createMaterial(name, pricePerUnit, type, vendor, companyName);
		return material;		
	}	
	
}
