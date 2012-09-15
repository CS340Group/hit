package gui.item;

import java.util.ArrayList;

import gui.common.Vault;
import common.Result;


/**
 * The Item class provides a way to query for ItemModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Item.find(2) // returns ItemModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ItemVault extends Vault {
	
	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Item
	 * @param value What value does the column have
	 * 
	 */
	public static Item find(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Returns a list of Items which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public static ArrayList<Item> findAll(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(Item model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(Item model){
		return null;
	}

	/**
	 * Adds the Item to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Item to add
	 * @return Result of request
	 */
	protected static Result saveNew(Item model){
		return null;
	}

	/**
	 * Adds the Item to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Item to add
	 * @return Result of request
	 */
	protected static Result saveModified(Item model){
		return null;
	}
}

