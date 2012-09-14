package gui.productgroup;

import gui.common.IModel;
import common.Result;
import gui.inventory.ProductContainerData;

public class ProductGroup extends ProductContainerData implements IModel{

	private int _id;

	private boolean _valid;

	private boolean _saved;	

	public ProductGroup(){
		super();
		_id = -1;
		_valid = false;
		_saved = false;
	}

	public boolean isSaved(){
		return this._saved;
	}

	public boolean isValid(){
		return this._valid;
	}

	public Result save(){
		return new Result(false, "Saving is not yet implemented");
	}

	public Result validate(){
		return new Result(false, "Validating is not yet implemented");
	}
	
}