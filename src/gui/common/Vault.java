package gui.common;

import java.util.SortedMap;
import common.Result;

public abstract class Vault implements IVault {

	
	private SortedMap<Integer, IModel> dataVault;
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public Vault(){
		return;
	}
	
	
	/**
	 * Returns the IModel object with the correlating index
	 * 
	 * @param index
	 */
	protected IModel getItem(int index){
		return null;
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