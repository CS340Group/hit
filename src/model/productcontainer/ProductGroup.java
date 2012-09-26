package model.productcontainer;

import common.Result;
import model.common.Size;
import model.item.Item;
import model.product.Product;

import java.util.ArrayList;

/**
 * The ProductGroup class encapsulates all the funtions and data associated with a "ProductGroup".
 * It extends the {@link model.productcontainer.ProductContainer ProductContainer}
 * 	class which contains getters and setters for the various datas.
 */
public class ProductGroup extends ProductContainer{

	/* A pointer to the ProductContainer that this group belongs to */
	private int _parentId;
	/* A pointer to the StorageUnit that holds this group. Can be the same
	 * as _parentId */
	private int _rootParentId;
	/* A variable representing the three month supply for this group. */
	private Size _3MonthSupply;
	private boolean _deleted;
	/**
	 * No-args constructor.
	 */
	public ProductGroup(){
		super();
		_parentId = -1;
		_rootParentId = -1;
	}

	/**
	 * Expects the parent group, and the storage unit that this group belongs
	 * to. They can be the same value. Also expects some non-negative integer
	 * for the three-moth supply for this group.
	 */
	public ProductGroup(ProductGroup parent, ProductGroup rootParent, 
	                    Size threeMonthSupply){
		super();
		_parentId = parent.getId();
		_rootParentId = rootParent.getId();
		_3MonthSupply = threeMonthSupply;
    }


    public ProductGroup(ProductGroup container) {
        super(container);
        _parentId = container.getParentId();
        _rootParentId = container.getRootParentId();
        _3MonthSupply = container.get3MonthSupply();
    }

    /**
	 * Returns the three-month supply for this group as an int. */	
	public Size get3MonthSupply(){
		return _3MonthSupply;
	}

	/**
	 * Allows a non-zero integer to be set for the three month supply.
	 */
	public Result set3MonthSupply(Size value){
        if (!value.validate().getStatus()){
            return new Result(false, "The 3 mo. is invalid.");
        }
		_3MonthSupply = value;
        invalidate();
        return new Result(true, "Successfully set.");
	}

    public int getParentId(){
        return _parentId;
    }
    public String getParentIdString(){
    	return Integer.toString(_parentId);
    }
    public ProductContainer getParent(){
        return this.productGroupVault.get(_parentId);
    }

    public Result setParentId(int id){
        _parentId = id;
        invalidate();
        return new Result(true);
    }

    public int getRootParentId(){
        return _rootParentId;
    }

    public ProductContainer getRootParent(){
        return storageUnitVault.get(_rootParentId);
    }

    public Result setRootParentId(int id){
        _rootParentId = id;
        invalidate();
        return new Result(true);
    }

    public Result validate(){
        if(getId() == -1)
            return productGroupVault.validateNew(this);
        else
            return productGroupVault.validateModified(this);
    }

    public Result save(){
        if(!isValid())
            return new Result(false, "Item must be valid before saving.");
        if(getId() == -1)
            return productGroupVault.saveNew(this);
        else
            return productGroupVault.saveModified(this);
    }

    public Result isDeleteable(){
        ArrayList<Product> products = productVault.findAll("ContainerId = "+getId());
        for(Product product : products){
            if(!product.isDeleteable().getStatus())
                return product.isDeleteable();
        }

        return new Result(true);
    }
}