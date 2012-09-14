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
	
	public static Product find() {
		return null;
	}
	
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

