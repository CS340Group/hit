package model.storageunit;

import java.util.ArrayList;

import model.common.Vault;
import common.Result;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class StorageUnitVault extends Vault {
	
	/**
	 * Returns just one StorageUnit based on the query sent in. 
	 * If you need more than one StorageUnit returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each StorageUnit
	 * @param value What value does the column have
	 * 
	 */
	public static StorageUnit find(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Returns a list of StorageUnits which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public static ArrayList<StorageUnit> findAll(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(StorageUnit model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(StorageUnit model){
		return null;
	}

	/**
	 * Adds the StorageUnit to the map if it's new.  Should check before doing so.
	 * 
	 * @param model StorageUnit to add
	 * @return Result of request
	 */
	protected static Result saveNew(StorageUnit model){
		return null;
	}

	/**
	 * Adds the StorageUnit to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model StorageUnit to add
	 * @return Result of request
	 */
	protected static Result saveModified(StorageUnit model){
		return null;
	}
}

