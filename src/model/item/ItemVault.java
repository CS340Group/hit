package model.item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import model.common.IModel;
import model.common.Vault;
import model.product.Product;
import common.Result;
import common.util.QueryParser;


/**
 * The Item class provides a way to query for ItemModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Item.find(2) // returns ItemModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ItemVault extends Vault {
	
	public ItemVault(){
		
	}
	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
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
	public Item find(String query)  {
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
	 * Returns a list of Items which match the criteria
	 * 
	 * @param query of form obj.attr = value 
	 * 
	 */
	public ArrayList<Item> findAll(String query) {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<Item> results = linearSearch(MyQuery,0);
			return results;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Search an ordered hashmap one at a time
	private ArrayList<Item> linearSearch(QueryParser MyQuery,int count) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<Item> results = new ArrayList<Item>();
		String objName = MyQuery.getObjName();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		Item myItem = new Item();
		Product myProduct = new Product();
		//Class associated with the product model
		Class prdCls = myProduct.getClass();
		//Class associated with the item model
		Class cls = myItem.getClass();
		//Method we will call to get the value
		Method method;
		
		
		if(objName!= null && objName.equals("product")){
			method = prdCls.getMethod("get"+attrName);
		} else {
			method = cls.getMethod("get"+attrName);
		}

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : this.dataVault.entrySet()) {
			myItem = (Item) entry.getValue();
			String myItemValue; 
			
			if(objName!= null && objName.equals("product")){
				//Get the item, call get product, run dynamic method on that
				myItemValue = (String) method.invoke(myItem.getProduct(), null);
			} else {
				myItemValue = (String) method.invoke(myItem, null);
			}
		    
		    if(myItemValue.equals(value)){
		    	results.add(myItem);
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
		
	//TODO: for testing only
	public void add(Item item){
		
		dataVault.put(1, item);
	}
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	public Result validateNew(Item model){
		assert(model!=null);
		
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	public static Result validateModified(Item model){
		assert(model!=null);
		
		return null;
	}

	/**
	 * Adds the Item to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Item to add
	 * @return Result of request
	 */
	public static Result saveNew(Item model){
		return null;
	}

	/**
	 * Adds the Item to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Item to add
	 * @return Result of request
	 */
	public static Result saveModified(Item model){
		return null;
	}
}

