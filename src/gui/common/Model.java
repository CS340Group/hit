package gui.common;

public abstract class Model implements IModel extends ItemData{
	private int _id;

	private boolean _valid;

	private boolean _saved;	

	public Model(){
		super();
	}

	public boolean isSaved(){
		return this._saved
	}

	public boolean isValid(){
		return this._valid
	}

	public Result save(){
		assert false
		return new Result(false, "Saving is not overridden!")
	}

	public Result validate(){
		assert false
		return new Result(false, "Validating is not overridden!")
	}
}