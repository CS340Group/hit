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
	public static Product find(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			return linearSearch(MyQuery,1).get(0);
		} catch (Exception e) {
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
	public static ArrayList<Product> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<Product> results = linearSearch(MyQuery,0);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static ArrayList<Product> linearSearch(QueryParser MyQuery,int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
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
		for (Entry<Integer, IModel> entry : dataVault.entrySet()) {
			myProduct = (Product) entry.getValue();
			String myProductValue; 
			
			if(objName!= null && objName.equals("productGroup")){
				myProductValue = method.invoke(myProduct.getContainer(), null).toString();
			} else if(objName!= null && objName.equals("storageUnit")){
				myProductValue = method.invoke(myProduct.getStorageUnit(), null).toString();
			} else {
				myProductValue = method.invoke(myProduct, null).toString();
			}

		    if(myProductValue.equals(value) && !myProduct.isDeleted()){
		    	results.add(myProduct);
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
		
	
	/**
	 * Checks if a new model will fit in the vault
	 * - Product must have unique barcode within a su
	 * 
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(Product model){
		Result result = new Result();
		
		//Check that the new product is not a duplicate
		//in the storage container
		result = validateUniqueBarcode(model);
		if(result.getStatus() != true)
			return result;

        model.setValid(true);
		return new Result(true);
	}
	

	/**
	 * Checks if the updated model will fit into the vault
	 * -- Does so by deleting the old model, 
	 * -- doing a validate with the new model
	 * -- then undelete the model
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected Result validateModified(Product model){
		assert(model!=null);
        assert(!dataVault.isEmpty());
		
		//Delete current model
		Product currentModel = this.get(model.getId());
		currentModel.delete();
		//Validate passed in model
		Result result = this.validateNew(model);
		//Add current model back
		currentModel.unDelete();
		
        //TODO: This method should call a list of other validate methods for each integrity constraint
		if(result.getStatus() == true)
			model.setValid(true);
        return result;
	}

	private static Result validateUniqueBarcode(Product model){
		ArrayList<Product> allProducts = findAll("storageUnit.Id = "+model.getStorageUnitId());
		String barcode = model.getBarcode().toString();
		for(Product testProd : allProducts){
			if(testProd.getBarcode().toString().equals(barcode))
				return new Result(false,"Duplicate product in container");
		}
		return new Result(true);
	}

    public static Product get(int id){
        return new Product((Product) dataVault.get(id));
    }

    public static int size(){
        return dataVault.size();
    }
	
	/**
	 * Adds the product to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveNew(Product model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty())
            id = 0;
        else
            id = dataVault.lastKey()+1;

        model.setId(id);
        model.setSaved(true);
        dataVault.put(id,model);
        return new Result(true);
	}

	/**
	 * Adds the product to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveModified(Product model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = model.getId();
        model.setSaved(true);
        dataVault.put(id,model);
        return new Result(true);
	}


}
