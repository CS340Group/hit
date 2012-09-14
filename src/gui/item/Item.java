package gui.item;

import gui.common.IModel;
import common.Result;

public class ItemModel implements IModel extends ItemData {

	public ItemModel(){
		_id = -1;
		_valid = false;
		_saved = false;
		super();
	}

	public boolean isSaved(){
		return this._saved
	}

	public boolean isValid(){
		return this._valid
	}

	public Result save(){
		return new Result(false, "Saving is not yet implemented")
	}

	public Result validate(){
		return new Result(false, "Validating is not yet implemented")
	}
	
}