package model.product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;

import model.common.IModel;
import model.common.Vault;
import model.item.Item;
import model.productgroup.ProductGroup;
import model.storageunit.StorageUnit;
import common.Result;
import common.util.QueryParser;

/**
 * The ProductVault class provides a way to query for Products within the select data backend
 * <PRE>
 * ProductVault.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductVault extends Vault {
	
	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public Product find(String query)  {
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
	public ArrayList<Product> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<Product> results = linearSearch(MyQuery,0);
			return results;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ArrayList<Product> linearSearch(QueryParser MyQuery,int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<Product> results = new ArrayList<Product>();
		String objName = MyQuery.getObjName();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		ProductGroup myPG = new ProductGroup();
		StorageUnit mySU = new StorageUnit();
		Product myProduct = new Product();
		
		//Class associated with the product model
		Class prdCls = myProduct.getClass();
		Class suCls = mySU.getClass();
		Class pgCls = myPG.getClass();
		//Method we will call to get the value
		Method method;
		
		
		if(objName!= null && objName.equals("productGroup")){
			method = pgCls.getMethod("get"+attrName);
		} else if(objName!= null && objName.equals("storageUnit")){
			method = suCls.getMethod("get"+attrName);
		} else {
			method = prdCls.getMethod("get"+attrName);
		}

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : this.dataVault.entrySet()) {
			myProduct = (Product) entry.getValue();
			String myProductValue; 
			
			if(objName!= null && objName.equals("productGroup")){
				myProductValue = (String) method.invoke(myProduct.getProductGroup(), null);
			} else if(objName!= null && objName.equals("storageUnit")){
				myProductValue = (String) method.invoke(myProduct.getStorageUnit(), null);
			} else {
				myProductValue = (String) method.invoke(myProduct, null);
			}

		    if(myProductValue.equals(value)){
		    	results.add(myProduct);
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
	protected static Result validateNew(Product model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(Product model){
		return null;
	}

	/**
	 * Adds the product to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveNew(Product model){
		return null;
	}

	/**
	 * Adds the product to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveModified(Product model){
		return null;
	}


}
