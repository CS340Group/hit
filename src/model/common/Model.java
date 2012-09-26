package model.common;

import java.io.Serializable;

import model.item.ItemVault;
import model.product.ProductVault;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;
import common.Result;

public abstract class Model implements IModel, Serializable {
	private int _id;

	public transient  ItemVault itemVault = ItemVault.getInstance();
	public transient  ProductVault productVault = ProductVault.getInstance();
	public transient  StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	public transient  ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	
	
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