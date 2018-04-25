package com.companybusinesslogic.material;

import java.util.ArrayList;

import com.companymodel.abstractions.IMaterial;
import com.companymodel.model.Material;

public interface IMaterialDAO {
	
	Material getMaterialOrNull(String companyName, String mtrlName);
	
	ArrayList<IMaterial> getAllMaterials(String companyName);
	
	Material createMaterial(String name, double pricePerUnit, String type, String vendor, String companyName);

	int saveMaterial(String name, double pricePerUnit, String type, String vendor, String companyName);
	
	int updateMaterial(String name, double pricePerUnit, String type, String vendor, String companyName);
	
}
