package model.productgroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;

import model.common.IModel;
import model.common.Vault;
import model.item.Item;
import model.product.Product;
import model.storageunit.StorageUnit;
import common.Result;
import common.util.QueryParser;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductGroupVault extends Vault {
	
	public ProductGroup find(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			return linearSearch(MyQuery,1).get(0);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	/**
	 * Returns a list of Products which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public ArrayList<ProductGroup> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<ProductGroup> results = linearSearch(MyQuery,0);
			return results;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ArrayList<ProductGroup> linearSearch(QueryParser MyQuery,int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<ProductGroup> results = new ArrayList<ProductGroup>();
		String objName = MyQuery.getObjName();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		ProductGroup myPG = new ProductGroup();
		StorageUnit mySU = new StorageUnit();
		
		//Class associated with the product model
		Class suCls = mySU.getClass();
		Class pgCls = myPG.getClass();
		//Method we will call to get the value
		Method method;
		
		
		if(objName!= null && objName.equals("storageUnit")){
			method = suCls.getMethod("get"+attrName);
		} else {
			method = pgCls.getMethod("get"+attrName);
		}

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : this.dataVault.entrySet()) {
			myPG = (ProductGroup) entry.getValue();
			String myProductValue; 
			
			if(objName!= null && objName.equals("storageUnit")){
				myProductValue = (String) method.invoke(myPG.getStorageUnit(), null);
			} else {
				myProductValue = (String) method.invoke(myPG, null);
			}

		    if(myProductValue.equals(value)){
		    	results.add(myPG);
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
	
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(ProductGroup model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(ProductGroup model){
		return null;
	}

	/**
	 * Adds the ProductGroup to the map if it's new.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected static Result saveNew(ProductGroup model){
		return null;
	}

	/**
	 * Adds the ProductGroup to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected static Result saveModified(ProductGroup model){
		return null;
	}
}

