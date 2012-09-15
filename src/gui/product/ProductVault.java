package gui.product;

import java.util.ArrayList;

import gui.common.IModel;
import gui.common.Vault;
import common.Result;

/**
 * The ProductVault class provides a way to query for Products within the select data backend
 * <PRE>
 * ProductVault.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductVault extends Vault {
	
	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public static Product find(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Returns a list of Products which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public static ArrayList<Product> findAll(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(Product model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(Product model){
		return null;
	}

	/**
	 * Adds the product to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveNew(Product model){
		return null;
	}

	/**
	 * Adds the product to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected static Result saveModified(Product model){
		return null;
	}


}

