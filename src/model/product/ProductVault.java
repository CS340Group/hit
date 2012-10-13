package model.product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;
import org.joda.time.DateTime;

import model.common.IModel;
import model.common.Vault;
import common.Result;
import common.util.QueryParser;

/**
 * The ProductVault class provides a way to query for Products within the select data backend
 * <PRE>
 * ProductVault.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductVault extends Vault{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ProductVault currentInstance;
	
	/**
	 * Private constructor, for the singleton design pattern.
	 */	
	private ProductVault(){
		currentInstance = this;
	}
	
	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized ProductVault getInstance(){
		if(currentInstance == null) currentInstance = new ProductVault();
		return currentInstance;
	}

	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public  Product find(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
            ArrayList<Product> results = linearSearch(MyQuery,1);
            if(results.size() == 0)
                return null;
            return results.get(0);
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
	public  ArrayList<Product> findAll(String query) {
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
	
	private  ArrayList<Product> linearSearch(QueryParser MyQuery,int count)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<Product> results = new ArrayList<Product>();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		Product myProduct = new Product();
		
		//Class associated with the product model
		Class<? extends Product> prdCls = myProduct.getClass();
		//Method we will call to get the value
		Method method;
		
		
		method = prdCls.getMethod("get"+attrName);

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : dataVault.entrySet()) {
			myProduct = (Product) entry.getValue();
			String myProductValue; 
			
			myProductValue = method.invoke(myProduct).toString();

		    if(myProductValue.equals(value) && !myProduct.isDeleted()){
		    	results.add(new Product(myProduct));
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
	protected  Result validateNew(Product model){
		Result result = new Result();
		
		//Check that the new product is not a duplicate
		//in the storage container
		result = validateUniqueBarcode(model);
		if(result.getStatus() != true)
			return result;
		result = validateCreationDate(model);
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
	protected  Result validateModified(Product model){ 
		assert(model!=null);
        assert(!dataVault.isEmpty());
		
		//Delete current model
		Product currentModel = this.get(model.getId());
		currentModel.delete();
		currentModel.save();
		//Validate passed in model
		Result result = this.validateNew(model);
		//Add current model back
		currentModel.unDelete();
		if(result.getStatus() == true)
			model.setValid(true);
        return result;
	}

	private  Result validateUniqueBarcode(Product model){
		ArrayList<Product> allProducts = findAll("StorageUnitId = "+model.getStorageUnitId());
		String barcode = model.getBarcode().toString();
		for(Product testProd : allProducts){
			if(testProd.getBarcode().toString().equals(barcode))
				return new Result(false,"Duplicate product in container");
		}
		return new Result(true);
	}
	private Result validateCreationDate(Product model){
		Product existingProduct = find("BarcodeString = "+model.getBarcodeString());
		if(existingProduct != null && existingProduct.getCreationDate() != model.getCreationDate())
			return new Result(false,"The creation date does now match the existing creation date");
		return new Result(true);
	}
	private Product setCreationDate(Product model){
		Product existingProduct = find("BarcodeString = "+model.getBarcodeString());
		if(existingProduct != null && existingProduct.getCreationDate() != model.getCreationDate())
			model.setCreationDate(existingProduct.getCreationDate());
		else
			model.setCreationDate(new DateTime());
		return model;
	}
	
    public  Product get(int id){
    	Product p = (Product)dataVault.get(id);
    	if(p == null)
    		return null;
        return new Product(p);
    }

    
	
	/**
	 * Adds the product to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected  Result saveNew(Product model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty())
            id = 0;
        else
            id = (int)dataVault.lastKey()+1;

        model.setId(id);
        
        //If the creation date hasn't been set
        if(model.getCreationDate() == null)
        	model = this.setCreationDate(model);
        model.setSaved(true);
        this.addModel(new Product(model));
        return new Result(true);
	}

	/**
	 * Adds the product to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected  Result saveModified(Product model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");
        model.setSaved(true);
        this.addModel(new Product(model));
        return new Result(true);
	}


}
