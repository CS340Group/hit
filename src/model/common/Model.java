package model.common;

import model.item.ItemVault;
import model.product.ProductVault;
import model.productgroup.ProductGroupVault;
import model.storageunit.StorageUnitVault;
import common.Result;

public abstract class Model implements IModel{
	private int _id;

	protected ItemVault itemVault = ItemVault.getInstance();
	protected ProductVault productVault = ProductVault.getInstance();
	protected StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	protected ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	
	
	private boolean _valid;

	private boolean _saved;	

	public Model(){
		super();
	}

	public boolean isSaved(){
		return this._saved;
	}

	public boolean isValid(){
		return this._valid;
	}

	public Result save(){
		assert false;
		return new Result(false, "Saving is not overridden!");
	}

	public Result validate(){
		assert false;
		return new Result(false, "Validating is not overridden!");
	}
}