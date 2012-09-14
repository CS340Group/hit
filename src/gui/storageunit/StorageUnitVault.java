package gui.storageunit;

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
public class StorageUnitVault extends Vault {
	
	public static StorageUnit find() {
		return null;
	}
	
	protected static Result validateNew(StorageUnit model){
		return null;
	}

	protected static Result validateModified(StorageUnit model){
		return null;
	}

	protected static Result saveNew(StorageUnit model){
		return null;
	}

	protected static Result saveModified(StorageUnit model){
		return null;
	}
}

