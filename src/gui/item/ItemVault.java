package gui.item;

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
public class ItemVault extends Vault {
	
	public static Item find() {
		return null;
	}
	
	protected static Result validateNew(Item model){
		return null;
	}

	protected static Result validateModified(Item model){
		return null;
	}

	protected static Result saveNew(Item model){
		return null;
	}

	protected static Result saveModified(Item model){
		return null;
	}
}

