package model.common;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

import common.Result;
import static ch.lambdaj.Lambda.*;

public abstract class Vault implements Serializable{

	
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
	 * adds the item to the map
	 * 
	 * @param newItem
	 */
	protected Result addItem(IModel newItem){
		return null;
	}
	

}