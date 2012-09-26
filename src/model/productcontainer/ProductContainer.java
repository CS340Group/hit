package model.productcontainer;

import model.common.IModel;
import model.common.Model;
import common.Result;

/**
 * The ProductContainer is the base class for 
 * {@link ProductGroup ProductGroup} and for
 * {@link StorageUnit StorageUnit}. It defines the shared
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
        _deleted = false;
    }

    /**
     * Copy Constructor
     */
    public ProductContainer(ProductContainer p){
        assert p != null : "I wanted a ProductContainer, and I found a null!";
        _id = p.getId();
        _valid = p.isValid();
        _saved = p.isSaved();
        _deleted = p.isDeleted();
        _name = p.getName();
    }

    /**
     * Returns the deleted status of this instance.
     */
    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    /**
     * Returns the ID associated with the class as an int.
     */
    public int getId(){
        return _id;
    }

    protected Result setId(int id){
        assert id > 0 : "An ID shouldn't be nagative, if you think about it.";
        _id = id;
        return new Result(true);
    }

    /**
     * This method should be called whenever an attribute is changed so that 
     * validation must happen again.
     */
    public void invalidate(){
        _valid = false;
        _saved = false;
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
    public Result setName(String _name) {
        assert _name != null;
        this._name = _name;
        invalidate();
        return new Result(_name==this._name);
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
        assert true;
        return new Result(false, "THIS METHOD SHOULD BE OVERRIDDEN");
    }

    /**
     * Validate that the ProductContainer is able to be saved into the vault.
     */
    public Result validate(){
        assert true;
        return new Result(false, "THIS METHOD SHOULD BE OVERRIDDEN");
    }

    /**
     * Sets the internal state of this instance to deleted.
     */
    public Result delete(){
        assert true;
		this._deleted = true;
		return new Result(true);
	}

    /**
     * Resets the internal state of this instance to not deleted.
     */
    public Result unDelete(){
        assert true;
		this._deleted = false;
		return new Result(true);
	}

    /**
     * Indicates that this instance has been validated.
     */
    protected Result setValid(boolean v){
        assert true;
        _valid = v;
        return new Result(true);
    }

    /**
     * Indicates that this instance has been saved.
     */
    protected Result setSaved(boolean b){
        assert true;
        _saved = b;
        return new Result(true);
    }
}