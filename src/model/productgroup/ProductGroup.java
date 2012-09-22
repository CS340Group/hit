package model.productgroup;

import model.productcontainer.ProductContainer;
import common.Result;
import model.common.Unit;
/**
 * The ProductGroup class encapsulates all the funtions and data associated with a "ProductGroup".
 * It extends the {@link model.inventory.ProductContainerData ProductContainerData} 
 * 	class which contains getters and setters for the various datas.
 */
public class ProductGroup extends ProductContainer{

	/* A pointer to the ProductContainer that this group belongs to */
	private int _parentId;
	/* A pointer to the StorageUnit that holds this group. Can be the same
	 * as _parentId */
	private int _rootParentId;
	/* A variable representing the three month supply for this group. */
	private Unit _3MonthSupply;

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
	                    Unit threeMonthSupply){
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
	public Unit get3MonthSupply(){
		return _3MonthSupply;
	}

	/**
	 * Allows a non-zero integer to be set for the three month supply.
	 */
	public Result setThreeMosSupply(Unit value){
		// Do checks
		_3MonthSupply = value;
		return new Result(true, "Successfully set.");
	}

    public int getParentId(){
        return _parentId;
    }

    public Result setParentId(int id){
        _parentId = id;
        return new Result(true);
    }

    public int getRootParentId(){
        return _rootParentId;
    }

    public Result setRootParentId(int id){
        _rootParentId = id;
        return new Result(true);
    }
}