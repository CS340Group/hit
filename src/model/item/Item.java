package model.item;

import model.common.IModel;
import model.common.Barcode;
import model.product.Product;
import common.Result;

import org.joda.time.DateTime;

/**
 * The Item class encapsulates all the funtions and data associated with a "Item".
 * It extends the {@link gui.item.ItemData ItemData} 
 * 	class which contains getters and setters for the various datas.
 */
public class Item implements IModel{

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

    private Product _product;

    private Barcode _barcode;

    private DateTime _entryDate;

    private DateTime _exitDate;

    private DateTime _expirationDate;

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

    }

    public Result setProduct(Product p){
        assert p != null;
        assert p.isValid();
        assert p.isSaved();
        _product = p;
        return new Result(true, "Product set successfully.");
    }

    public Product getProduct(){
        return new Product(_product);
    }

    public Result setBarcode(Barcode b){
        assert b != null;
        _barcode = b;
        return new Result(true, "Barcode set successfully.");
    }

    public Barcode getBarcode(){
        return _barcode;
    }

    public Result setEntryDate(DateTime d){
        _entryDate = d;
        return new Result(true);
    }

    public DateTime getEntryDate(){
        return _entryDate;
    }

    public Result setExitDate(DateTime d){
        _exitDate = d;
        return new Result(true);
    }

    public DateTime getExitDate(){
        return _exitDate;
    }

    public Result setExpirationDate(DateTime d){
        _expirationDate = d;
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

	/**
	 * Is the Item valid?
	 */
	public boolean isValid(){
		return this._valid;
	}

	/**
	 * If the Item is valid it is saved into the vault.
	 */
	public Result save(){
		return new Result(false, "Saving is not yet implemented");
	}

	/**
	 * Validate that the Item is able to be saved into the vault.
	 */
	public Result validate(){
		return new Result(false, "Validating is not yet implemented");
	}
}