package model.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.item.Item;
import model.product.Product;

import common.Result;
import common.util.QueryParser;


public abstract class Vault extends Observable implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SortedMap<Integer, IModel> dataVault = new TreeMap<Integer, IModel>();
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public Vault(){
		return;
	}
	
	/**
	 * Returns the size of the vault.
	 */
	public int size(){
        return dataVault.size();
    }

    public void clear(){
        dataVault.clear();
    }
	
	
	/**
	 * adds the item to the map and notifies all observers
	 * 
	 * @param newItem
	 */
	protected Result addModel(IModel newItem){
		int id = newItem.getId();
		this.dataVault.put(id, newItem);
        this.setChanged();
		this.notifyObservers();
		return null;
	}
	
	/**
	 * Configures this vault to use the data from another vault instance.
	 * @param v
	 */
	public void useDataFromOtherVault(Vault v){
		this.dataVault = v.dataVault;
	}
	
	
	/**
	 * Returns a list of Models which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public abstract IModel find(String query);
	public IModel findPrivateCall(String query)  {
		QueryParser MyQuery = new QueryParser(query);

		
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
            ArrayList<Item> results = (ArrayList)linearSearch(MyQuery,1);
            if(results.size() == 0)
                return null;
            return results.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	public abstract  ArrayList findAll(String query);
	protected ArrayList<IModel> findAllPrivateCall(String query){
		QueryParser MyQuery = new QueryParser(query);
		//Do a linear Search first
		//TODO: Add ability to search by index
		try {
			ArrayList<IModel> results = linearSearch(MyQuery,0);
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	protected abstract IModel getNewObject();
	protected abstract IModel getCopiedObject(IModel model);
	
	protected ArrayList<IModel> linearSearch(QueryParser MyQuery,int count)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<IModel> results = new ArrayList<IModel>();
		String attrName = MyQuery.getAttrName();
		String value = MyQuery.getValue();
		
		//PushDown
		IModel myModel = getNewObject();
		//Class associated with the item model
		Class<? extends IModel> cls =  myModel.getClass();
		
		//Method we will call to get the value
		Method method;
		method = cls.getMethod("get"+attrName);

		
		//Loop through entire hashmap and check values one at a time
		for (Entry<Integer, IModel> entry : dataVault.entrySet()) {
			myModel = entry.getValue();
			String myItemValue; 

			myItemValue = method.invoke(myModel).toString();
		    
		    if(myItemValue.equals(value) && !myModel.isDeleted()){
		    	//PushDown
		    	results.add(getCopiedObject(myModel));
		    }
		    if(count != 0 && results.size() == count )
		    	return results;
		}
		return results;
	}
	
}