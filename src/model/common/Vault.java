package model.common;

import java.util.SortedMap;
import java.util.TreeMap;

import common.Result;

public abstract class Vault{

	
	protected SortedMap<Integer, IModel> dataVault = new TreeMap<Integer, IModel>();
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public Vault(){
		return;
	}
	
	
	public  int size(){
        return dataVault.size();
    }
	
	
	
	/**
	 * adds the item to the map
	 * 
	 * @param newItem
	 */
	protected Result addItem(IModel newItem){
		return null;
	}
	

}