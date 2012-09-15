package gui.productgroup;

import java.util.ArrayList;

import gui.common.Vault;
import common.Result;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductGroupVault extends Vault {
	
	/**
	 * Returns just one ProductGroup based on the query sent in. 
	 * If you need more than one ProductGroup returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each ProductGroup
	 * @param value What value does the column have
	 * 
	 */
	public static ProductGroup find(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Returns a list of ProductGroups which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public static ArrayList<ProductGroup> findAll(String attribute, String value) {
		return null;
	}
	
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateNew(ProductGroup model){
		return null;
	}

	/**
	 * Checks if the model already exists in the map
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected static Result validateModified(ProductGroup model){
		return null;
	}

	/**
	 * Adds the ProductGroup to the map if it's new.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected static Result saveNew(ProductGroup model){
		return null;
	}

	/**
	 * Adds the ProductGroup to the map if it already exists.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected static Result saveModified(ProductGroup model){
		return null;
	}
}

