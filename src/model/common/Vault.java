package model.common;

import java.io.Serializable;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;

import common.Result;


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
	
	
}