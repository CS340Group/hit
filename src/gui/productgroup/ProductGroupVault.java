package gui.productgroup;

import gui.common.Vault;
import common.Result;


/**
 * The Product class provideds a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductGroupVault extends Vault {
	
	public static ProductGroup find() {
		return null;
	}
	
	protected static Result validateNew(ProductGroup model){
		return null;
	}

	protected static Result validateModified(ProductGroup model){
		return null;
	}

	protected static Result saveNew(ProductGroup model){
		return null;
	}

	protected static Result saveModified(ProductGroup model){
		return null;
	}
}

