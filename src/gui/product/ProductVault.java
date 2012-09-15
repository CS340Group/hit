package gui.product;

import gui.common.Vault;
import common.Result;

/**
 * The ProductVault class provideds a way to query for Products within the select data backend
 * <PRE>
 * ProductVault.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductVault extends Vault {
	
	/** 
	 * Returns an instance of a {@link gui.product.Product product} located within the product vault. 
	 * @param id unique integer representing automatically assigned index of pre-exiting product.
	 * Returns a copy of the product if found. Otherwise, returns null.
	 */
	public static Product find(int id) {
		return null;
	}
	
	/**
	 * Used by {@link guit.item.Item Item} to make sure that an automatically created product is a
	 * valid new product.
	 * @param model is a reference to the newly created Product instance which wants to be added to the vault.
	 * If the validation fails, a Result instance is returned wiht a message detailing the reason for failure.
	 */
	protected static Result validateNew(Product model){
		return null;
	}

	protected static Result validateModified(Product model){
		return null;
	}

	protected static Result saveNew(Product model){
		return null;
	}

	protected static Result saveModified(Product model){
		return null;
	}
}

