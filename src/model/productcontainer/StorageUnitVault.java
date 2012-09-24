package model.productcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import model.common.IModel;
import common.Result;
import common.util.QueryParser;
import model.productcontainer.StorageUnit;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class StorageUnitVault{

    protected static SortedMap<Integer, StorageUnit> dataVault =
            new TreeMap<Integer, StorageUnit>();

    public static int size(){
        return dataVault.size();
    }

    public static void clear(){
        dataVault.clear();
    }
	/**
	 * Returns just one StorageUnit based on the query sent in. 
	 * If you need more than one StorageUnit returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public static StorageUnit find(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
            ArrayList<StorageUnit> results = linearSearch(MyQuery,1);
            if(results.size() == 0)
                return null;
            return results.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	/**
	 * Returns a list of StorageUnits which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public static ArrayList<StorageUnit> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<StorageUnit> results = linearSearch(MyQuery,0);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static ArrayList<StorageUnit> linearSearch(QueryParser MyQuery,int count)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<StorageUnit> results = new ArrayList<StorageUnit>();
		String objName = MyQuery.getObjName();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		

		StorageUnit mySU = new StorageUnit();
		
		//Class associated with the product model
		Class suCls = mySU.getClass();
		//Method we will call to get the value
		Method method;
		method = suCls.getMethod("get"+attrName);

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, StorageUnit> entry : dataVault.entrySet()) {
			mySU = (StorageUnit) entry.getValue();
			String mySUValue; 
			mySUValue = (String) method.invoke(mySU, null);

		    if(mySUValue.equals(value) && !mySU.isDeleted()){
		    	results.add(mySU);
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * - Must have a unique name
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(StorageUnit model){
		Result result = new Result();
		result = checkUniqueName(model);
		if(result.getStatus() == false)
			return result;

        model.setValid(true);
		return result;
	}
	
	private static Result checkUniqueName(StorageUnit model){
        //Null check
        if(model.getName() == null)
            return new Result(false, "Name can't be null");

        if(model.getName() == "")
            return new Result(false, "Name can't be empty");

		int size = findAll("Name = "+model.getName()).size();
		if(size!=0)
			return new Result(false,"Duplicate storage container name.");
		return new Result(true);
	}

    public static StorageUnit get(int id){
    	StorageUnit su = dataVault.get(id);
    	if(su == null)
    		return null;

        return new StorageUnit(su);
    }

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(StorageUnit model){
		assert(model!=null);
        assert(!dataVault.isEmpty());
		
		//Delete current model
		StorageUnit currentModel = dataVault.get(model.getId());
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
	 * Adds the StorageUnit to the map if it's new.  Should check before doing so.
	 * 
	 * @param model StorageUnit to add
	 * @return Result of request
	 */
	protected static Result saveNew(StorageUnit model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty())
            id = 0;
        else
            id = dataVault.lastKey()+1;

        model.setId(id);
        model.setSaved(true);
        dataVault.put(id,new StorageUnit(model));
        return new Result(true);
	}

	/**
	 * Adds the StorageUnit to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model StorageUnit to add
	 * @return Result of request
	 */
	protected static Result saveModified(StorageUnit model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = model.getId();
        model.setSaved(true);
        dataVault.put(id, new StorageUnit(model));
        return new Result(true);
	}
}

