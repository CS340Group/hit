package model.product;

import common.Result;
import model.common.Size;
import model.item.Item;
import model.productcontainer.StorageUnit;
import model.productcontainer.ProductGroup;
import model.common.Model;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * The Product class encapsulates all the funtions and data associated with a "Product".
 * It extends the {@link model.common.Model Model}
 * 	class which contains getters and setters for the various datas.
 */

public class Product extends Model{
	/**
	 * A unique ID is associated with every Product once it is presisted to the vault.
	 * _id is not set by the user, but by the vault when it is saved.
	 * _id can be -1 if it is new and has not been saved
	 */
    private int _storageUnitId;

    private int _containerId;

    private DateTime _creationDate;

    private String _barcode;

    private String _description;

    private Size _size;

    private int _shelfLife;

    private int _3MonthSupply;

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
        assert p != null;
        _id = p.getId();
        _valid = true;
        _saved = true;
        _deleted = p.isDeleted();
        _storageUnitId = p.getStorageUnitId();
        _containerId = p.getContainerId();
        _creationDate = p.getCreationDate();
        _barcode = p.getBarcode();
        _description = p.getDescription();
        _size = p.getSize();
        _shelfLife = p.getShelfLife();
        _3MonthSupply = p.get3MonthSupply();
        
    }

    public int getItemCount(){
    	ArrayList<Item> items = this._itemVault.findAll("ProductId = "+this.getId());
    	return items.size();
    }

    /**
     * Return a copy of the {@link model.productcontainer.StorageUnit
     * StorageUnit} that holds the product.
     */
    public StorageUnit getStorageUnit(){
        if(_storageUnitId < 0)
            return null;
        return _storageUnitVault.get(_storageUnitId);
    }

    /**
     * Return the ID of the {@link model.productcontainer.StorageUnit
     * StorageUnit} that holds the product. 
     */
    public int getStorageUnitId(){
        return _storageUnitId;
    }

    /**
     * Set the ID of the StorageUnit that contains this product.
     */
    public Result setStorageUnitId(int id){
        assert true;
        _storageUnitId = id;
        invalidate();
        return new Result(true);
    }

    /**
     * Get a copy of the ProductGroup that holds this Product. If the Product 
     * has no ProductGroup, a null pointer is returned.
     */
    public ProductGroup getContainer(){
        return _productGroupVault.get(_containerId);
    }

    /**
     * Get the id of the ProductGroup that holds this product. If the Product 
     * is not in a ProductGroup, -1 is returned.
     */
    public int getContainerId(){
        return _containerId;
    }

    /**
     * Set the ID for the ProductGroup that holds this Product.
     */
    public Result setContainerId(int id){
        assert true;
        _containerId = id;
        invalidate();
        return new Result(true);
    }

    /**
     * Get the creation date of the Product.
     */
    public DateTime getCreationDate(){
        return _creationDate;
    }

    /**
     * Set the creation date of the Product.
     */
    public Result setCreationDate(DateTime d){
        assert d != null;
        _creationDate = d;
        invalidate();
        return new Result(true);
    }

    /**
     * Get a reference to the Barcode object that belongs to this Product.
     */
    public String getBarcode(){
        return _barcode;
    }

    /**
     * Get the string that represents the Barcode number belonging to this 
     * Product.
     */
    public String getBarcodeString(){
    	return _barcode.toString();
    }

    /**
     * Assign a Barcode object as this Product's Barcode.
     */
    public Result setBarcode(String b){
        assert b != null;
        _barcode = b;
        invalidate();
        return new Result(true);
    }

    /**
     * Get the description of the Product.
     * @Pre the description cannot be empty.
     */
    public String getDescription(){
        return _description;
    }
    public String getDescriptionSort(){
    	return _description.toLowerCase();
    }

    /**
     * Set the description of the product. 
     */
    public Result setDescription(String d){
        assert d != null;
        _description = d;
        invalidate();
        return new Result(true);
    }

    /**
     * Get a reference to the Size of the Product.
     */
    public Size getSize(){
        return _size;
    }

    /**
     * Set the size of the product.
     * @Pre The size cannot be 0.
     * @Pre The size must be a valid Size object.
     */
    public Result setSize(Size u){
        assert u != null;
        _size = u;
        invalidate();
        return new Result(true);
    }

    /**
     * Return the shelf life of the Product.
     */
    public int getShelfLife(){
        return _shelfLife;
    }

    /**
     * Set the shelf life of the Product.
     * @Pre The shelf life must be a non-negative number.
     */
    public Result setShelfLife(int i){
        _shelfLife = i;
        invalidate();
        return new Result(true);
    }

    /**
     * Get the 3 month supply of the Product.
     */
    public int get3MonthSupply(){
        return _3MonthSupply;
    }

    /**
     * Set the 3 month supply of the product. 
     * @Pre Must be non-negative.
     */
    public Result set3MonthSupply(int i){
        _3MonthSupply = i;
        invalidate();
        return new Result(true);
    }

	/**
	 * Save the product to its Vault.
     * @Pre Product must be validated in order to be saved.
	 */
	public Result save(){
        if(!this._valid && !this.validate().getStatus())
            return new Result(false, "Product must be valid before saving.");
        if(getId() == -1)
            return _productVault.saveNew(this);
        else
            return _productVault.saveModified(this);
	}

	/**
	 * Make sure all parts of the product are valid. Put the Product into a 
     * validated state.
	 */
	public Result validate(){
        if (_barcode == null || _barcode.isEmpty()) {
            return new Result(false, "The barcode must be set and valid.");
        }
        if (_description == null || _description.equals("")){
            return new Result(false, "The description cannot be empty.");
        }
        if (_size==null || !_size.validate().getStatus()){
            return new Result(false, "The size is invalid.");
        }
        if (_shelfLife<0){
            return new Result(false, "The shelf life must be non-negative.");
        }
        if (_3MonthSupply<0){
            return new Result(false, "The 3 mo. supply must be non-negative.");
        }
        
        if(getId() == -1)
            return _productVault.validateNew(this);
        else
            return _productVault.validateModified(this);
	}
	
	/*
	 * Sets all the product attributes to defaults which
	 * pass validation.
	 */
	public void setToBlankProduct(){
		_barcode = "1";
		_description = "A Description";
		this._size = new Size(1,"count");
	}

    /**
     * Check if the product is void of items, and thus, deletable.
     */
    public Result isDeleteable(){
        ArrayList<Item> items = _itemVault.findAll("ProductId = " + _id);
        for(Item item : items){
            if(!item.isDeleted())
                return new Result(false, "All items must be deleted first");
        }

        return new Result(true);
    }

	public String getCount() {
		return this._size.toString();
	}

	public String getProductContainerName() {
		return _productGroupVault.getName(_containerId);
	}

	public Product generateTestData() {
		this.setBarcode("1");
		this.setDescription("Spam and eggs");
		this.setSize(new Size(3, "oz"));
		this.setShelfLife(4);
		this.set3MonthSupply(3);
		this.validate();
		this.save();
		return this;
	}

	/**
	 * Completely removes this item from the vault it sits in.
	 * Leaves the item in an unsaved state.
	 */
	public void obliterate() {
		this._productVault.obliterate(this);
		this._saved = false;
	}
}