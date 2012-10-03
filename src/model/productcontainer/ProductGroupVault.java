package model.productcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import model.common.IModel;
import model.common.Vault;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;
import model.product.ProductVault;
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
	static ProductGroupVault currentInstance;
	
	/**
	 * Private constructor, for the singleton design pattern.
	 */
	private ProductGroupVault(){
		currentInstance = this;
	}

	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized ProductGroupVault getInstance(){
		if(currentInstance == null) currentInstance = new ProductGroupVault();
		return currentInstance;
	}

	/**
	 * Returns just one ProductGroup based on the query sent in. 
	 * If you need more than one returned use FindAll
	 * 
	 * @param query of form obj.attr = value
	 * 
	 * product.id = 5
	 * name = 'cancer'
	 * id = 2
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	public ProductGroup find(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
            ArrayList<ProductGroup> results = linearSearch(MyQuery,1);
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
	public ArrayList<ProductGroup> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<ProductGroup> results = linearSearch(MyQuery,0);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ArrayList<ProductGroup> linearSearch(QueryParser MyQuery,int count)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<ProductGroup> results = new ArrayList<ProductGroup>();
		String objName = MyQuery.getObjName();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		ProductGroup myPG = new ProductGroup();
		
		//Class associated with the product model
		Class<? extends ProductGroup> pgCls = myPG.getClass();
		//Method we will call to get the value
		Method method;
		
		
		method = pgCls.getMethod("get"+attrName);

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : dataVault.entrySet()) {
			myPG = (ProductGroup) entry.getValue();
			String myProductValue; 
			
			myProductValue = (String) method.invoke(myPG);

		    if(myProductValue.equals(value) && !myPG.isDeleted()){
		    	results.add(new ProductGroup(myPG));
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
	
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * - Must have a unique name in su
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected Result validateNew(ProductGroup model){
		Result result = new Result();
		result = checkUniqueName(model);
		if(result.getStatus() == false)
			return result;

        model.setValid(true);
		return result;
	}
	
	private Result checkUniqueName(ProductGroup model){
        //Null check
        if(model.getName() == null)
            return new Result(false, "Name can't be null");


        if(model.getName() == "")
            return new Result(false, "Name can't be empty");

        //Checks that the product group has a unique name in it's container
        //Return all the ProductGroups in the current level
		ArrayList<ProductGroup> myPGs = findAll("ParentIdString = " + model.getParentIdString());
		//Loop through those results and make sure the name is unique
		for(ProductGroup tempGroup : myPGs){
			if(tempGroup.getName().equals(model.getName()))
				return new Result(false,"Duplicate product in container");
		}

        model.setValid(true);
        return new Result(true);
	}


    public  ProductGroup get(int id){
        ProductGroup pg = (ProductGroup) dataVault.get(id);
    	if(pg == null)
    		return null;

        return new ProductGroup(pg);
    }

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected Result validateModified(ProductGroup model){
		assert(model!=null);
        assert(!dataVault.isEmpty());
		
		//Delete current model
		ProductGroup currentModel = (ProductGroup) dataVault.get(model.getId());
		currentModel.delete();
		//Validate passed in model
		Result result = validateNew(model);
		//Add current model back
		currentModel.unDelete();
		
		if(result.getStatus() == true)
			model.setValid(true);
        return result;
	}

	/**
	 * Adds the ProductGroup to the map if it's new.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected  Result saveNew(ProductGroup model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty())
            id = 0;
        else
            id = dataVault.lastKey()+1;

        model.setId(id);
        model.setSaved(true);
        this.addModel(new ProductGroup(model));
        return new Result(true);
	}

	/**
	 * Adds the ProductGroup to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected  Result saveModified(ProductGroup model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = model.getId();
        model.setSaved(true);
        this.addModel(new ProductGroup(model));
        return new Result(true);
	}
}

