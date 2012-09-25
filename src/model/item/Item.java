package model.item;

import model.common.IModel;
import model.common.Barcode;
import model.common.Model;
import model.product.Product;
import model.product.ProductVault;
import common.Result;

import org.joda.time.DateTime;

/**
 * The Item class encapsulates all the funtions and data associated with a "Item".
 * It extends the {@link gui.item.ItemData ItemData} 
 * 	class which contains getters and setters for the various datas.
 */
public class Item extends Model{

		/**
	 * A unique ID is associated with every Item once it is presisted to the vault.
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

    private int _productId;

    private Barcode _barcode;

    private DateTime _entryDate;

    private DateTime _exitDate;

    private DateTime _expirationDate;

    private boolean _deleted;
	/**
	 * Constructor
	 */
	public Item(){
		//super();
		_id = -1;
		_valid = false;
		_saved = false;
	}

    /**
     *  Copy Constructor
     * @param
     * @return
     */
    public Item(Item i){
        _id = i.getId();
        _valid = false;
        _saved = false;
        _productId = i.getProductId();
        _barcode = i.getBarcode();
        _entryDate = i.getEntryDate();
        _exitDate = i.getExitDate();
        _expirationDate = i.getExpirationDate();
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    public int getId(){
        return _id;
    }

    protected Result setId(int id){
        _id = id;
        return new Result(true);
    }

    public Result setProductId(int id){
        _productId = id;
        invalidate();
        return new Result(true);
    }

    public Product getProduct(){
        return productVault.get(_productId);
    }

    public int getProductId(){
        return _productId;
    }

    public Result setBarcode(Barcode b){
        assert b != null;
        _barcode = b;
        invalidate();
        return new Result(true, "Barcode set successfully.");
    }

    public Barcode getBarcode(){
        return _barcode;
    }

    public Result setEntryDate(DateTime d){
        _entryDate = d;
        invalidate();
        return new Result(true);
    }

    public DateTime getEntryDate(){
        return _entryDate;
    }

    public Result setExitDate(DateTime d){
        _exitDate = d;
        invalidate();
        return new Result(true);
    }

    public DateTime getExitDate(){
        return _exitDate;
    }

    public Result setExpirationDate(DateTime d){
        _expirationDate = d;
        invalidate();
        return new Result(true);
    }

    public DateTime getExpirationDate(){
        return _expirationDate;
    }

	/**
	 * Is the Item saved?
	 */
	public boolean isSaved(){
		return this._saved;
	}

    protected Result setSaved(boolean saved){
        _saved = saved;
        return new Result(true);
    }

	/**
	 * Is the Item valid?
	 */
	public boolean isValid(){
		return this._valid;
	}

    protected Result setValid(boolean valid){
        _valid = valid;
        return new Result(true);
    }
    
    

	/**
	 * If the Item is valid it is saved into the vault.
	 */
	public Result save(){
		if(!isValid())
            return new Result(false, "Item must be valid before saving.");
        if(getId() == -1)
            return itemVault.saveNew(this);
        else
            return itemVault.saveModified(this);
	}

	/**
	 * Validate that the Item is able to be saved into the vault.
	 */
	public Result validate(){
        if(getId() == -1)
            return itemVault.validateNew(this);
        else
            return itemVault.validateModified(this);
	}
	
	public Result delete(){
		this._deleted = true;
		this._valid = true;
		this.save();
		return new Result(true);
	}
	public Result unDelete(){
		this._deleted = false;
		this._valid = true;
		this.save();
		return new Result(true);
	}

	@Override
	public boolean isDeleted() {
		return _deleted;
	}

	@Override
	public boolean isDeleted() {
		return _deleted;
	}

    public void invalidate(){
        _saved = false;
        _valid = false;
    }
}