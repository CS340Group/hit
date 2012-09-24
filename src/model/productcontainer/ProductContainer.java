package model.productcontainer;

import model.common.IModel;
import model.common.Model;
import common.Result;

/**
 * The ProductContainer is the base class for 
 * {@link model.productgroup.ProductGroup ProductGroup} and for 
 * {@link model.storageunit.StorageUnit StorageUnit}. It defines the shared 
 * interface for these objects.
 */
public class ProductContainer extends Model{

    /**
     * A string that is non-empty and must be unique among its vault context.
     */
    private String _name;

    /**
     * A unique ID is associated with every ProductContainer once it is presisted to the vault.
     * _id is not set by the user, but by the vault when it is saved.
     * _id can be -1 if it is new and has not been saved
     */
    private int _id;

    /**
     * When a change is made to the data it becomes invalid and 
     * must be validated before it can be saved.
     * _valid maintains this state
     */
    private boolean _valid;

    /**
     * _saved maintaines the state of if the instance of the model is the same as the 
     * persisted model in the vault.
     */
    private boolean _saved;
    private boolean _deleted;
    /**
     * Constructor
     */
    public ProductContainer(){
        _name = "";
        _id = -1;
        _valid = false;
        _saved = false;
    }

    /**
     * Copy Constructor
     */
    public ProductContainer(ProductContainer p){
        _id = p.getId();
        _valid = false;
        _saved = false;
    }

    public int getId(){
        return _id;
    }

    /**
     * This method should be called whenever an attribute is changed so that 
     * validation must happen again.
     */
    private void invalidate(){
        _valid = false;
    }

    /**
     * Returns the String name of the ProductCointainer.
     */
    public String getName() {
        return _name;
    }

    /**
     * Sets the name of the ProductContainer, invalidating it as well so that a 
     * subsequent save must be validated first.
     */
    public void setName(String _name) {
        this._name = _name;
        invalidate();
    }

    /**
     * Is the ProductContainer saved?
     */
    public boolean isSaved(){
        return this._saved;
    }

    /**
     * Is the ProductContainer valid?
     */
    public boolean isValid(){
        return this._valid;
    }

    /**
     * If the ProductContainer is valid it is saved into the vault.
     */
    public Result save(){
        if(!isValid()){
            return new Result(false, "Not yet validated.");
        }
                else {
                    return new Result(true, "Will soon be implemented.");
                }
    }

    /**
     * Validate that the ProductContainer is able to be saved into the vault.
     */
    public Result validate(){
        if(_name == ""){
            return new Result(false, "Name must be non-empty.");
        }
        else {
            _valid = true;
            return new Result(true, "I said it was successful, but validate isn't finished.");
        }
    }
    public Result delete(){
		this._deleted = true;
		return new Result(true);
	}
    public Result unDelete(){
		this._deleted = false;
		return new Result(true);
	}

	public boolean isDeleted() {
		return this._deleted;
	}
}