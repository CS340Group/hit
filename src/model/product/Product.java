package model.product;

import common.Result;
import model.common.Size;
import model.productcontainer.StorageUnit;
import model.productcontainer.ProductGroup;
import model.common.Barcode;
import model.common.Model;
import model.common.Size.Unit;

import org.joda.time.DateTime;

/**
 * The Product class encapsulates all the funtions and data associated with a "Product".
 * It extends the {@link model.product.ProductData ProductData} 
 * 	class which contains getters and setters for the various datas.
 */

public class Product extends Model{
	/**
	 * A unique ID is associated with every Product once it is presisted to the vault.
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

    private int _storageUnitId;

    private int _containerId;

    private DateTime _creationDate;

    private Barcode _barcode;

    private String _description;

    private Size _size;

    private int _shelfLife;

    private int _3MonthSupply;

    private boolean _deleted;
	/**
	 * Constructor
	 */
	public Product(){
		_id = -1;
		_valid = false;
		_saved = false;
        _creationDate = new DateTime();
	}

    /**
     * Copy constructor
     */
    public Product(Product p){
        _id = p.getId();
        _valid = true;
        _saved = true;
        _storageUnitId = p.getStorageUnitId();
        _containerId = p.getContainerId();
        _creationDate = p.getCreationDate();
        _barcode = p.getBarcode();
        _description = p.getDescription();
        _size = p.getSize();
        _shelfLife = p.getSelfLife();
        _3MonthSupply = p.get3MonthSupply();
    }

	/**
	 * Is the Product saved?
	 */
	public boolean isSaved(){
		return _saved;
	}

	public boolean isDeleted(){
		return _deleted;
	}
	
    protected Result setSaved(boolean saved){
        _saved = saved;
        return new Result(true);
    }

	/**
	 * Is the Product valid?
	 */
	public boolean isValid(){
		return _valid;
	}

    protected Result setValid(boolean valid){
        _valid = valid;
        return new Result(true);
    }

    public int getId(){
        return _id;
    }

    protected Result setId(int id){
        _id = id;
        return new Result(true);
    }

    public StorageUnit getStorageUnit(){
        return storageUnitVault.get(_storageUnitId);
    }

    public int getStorageUnitId(){
        return _storageUnitId;
    }

    public Result setStorageUnitId(int id){
        _storageUnitId = id;
        invalidate();
        return new Result(true);
    }

    public ProductGroup getContainer(){
        return productGroupVault.get(_containerId);
    }

    public int getContainerId(){
        return _containerId;
    }

    public Result setContainerId(int id){
        _containerId = id;
        invalidate();
        return new Result(true);
    }

    public DateTime getCreationDate(){
        return _creationDate;
    }

    public Result setCreationDate(DateTime d){
        _creationDate = d;
        invalidate();
        return new Result(true);
    }

    public Barcode getBarcode(){
        return _barcode;
    }
    public String getBarcodeString(){
    	return _barcode.toString();
    }

    public Result setBarcode(Barcode b){
        _barcode = b;
        invalidate();
        return new Result(true);
    }

    public String getDescription(){
        return _description;
    }

    public Result setDescription(String d){
        _description = d;
        invalidate();
        return new Result(true);
    }

    public Size getSize(){
        return _size;
    }

    public Result setSize(Size u){
        if(u.getAmount() == 0){
            return new Result(false, "The size of a product cannot be 0.");
        }
        if(!u.validate().getStatus()){
            return new Result(false, "That's an invlaid size.");
        }
        _size = u;
        invalidate();
        return new Result(true);
    }

    public int getSelfLife(){
        return _shelfLife;
    }

    public Result setShelfLife(int i){
        if (i<0){
            return new Result(false, "The shelf life must be non-negative.");
        }
        _shelfLife = i;
        invalidate();
        return new Result(true);
    }

    public int get3MonthSupply(){
        return _3MonthSupply;
    }

    public Result set3MonthSupply(int i){
        if (i<0){
            return new Result(false, "The 3 mo. supply must be non-negative.");
        }
        _3MonthSupply = i;
        invalidate();
        return new Result(true);
    }
	/**
	 * If the Product is valid it is saved into the vault.
	 */
	public Result save(){
        if(!isValid())
            return new Result(false, "Product must be valid before saving.");
        if(getId() == -1)
            return productVault.saveNew(this);
        else
            return productVault.saveModified(this);
	}

	/**
	 * Validate that the product is able to be saved into the vault.
	 */
	public Result validate(){
        if (_barcode == null || !_barcode.validate().getStatus()) {
            return new Result(false, "The barcode must be set and valid.");
        }
        if (_description == null || _description.equals("")){
            return new Result(false, "The description cannot be empty.");
        }
        if (!_size.validate().getStatus()){
            return new Result(false, "The size is invalid.");
        }
        if (_shelfLife<0){
            return new Result(false, "The shelf life must be non-negative.");
        }
        if (_3MonthSupply<0){
            return new Result(false, "The 3 mo. supply must be non-negative.");
        }
        
        if(getId() == -1)
            return productVault.validateNew(this);
        else
            return productVault.validateModified(this);
	}
	
	/*
	 * Sets all the product attributes to defaults which
	 * pass validation
	 */
	public void setToBlankProduct(){
		_barcode = new Barcode("1");
		_description = "A Description";
		this._size = new Size(1,Unit.count);
		
		
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
    public void invalidate(){
        _valid = false;
        _saved = false;
    }
}