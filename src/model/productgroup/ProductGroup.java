package model.productgroup;

import model.common.IModel;
import model.productcontainer.ProductContainer;
import common.Result;

/**
 * The ProductGroup class encapsulates all the funtions and data associated with a "ProductGroup".
 * It extends the {@link model.inventory.ProductContainerData ProductContainerData} 
 * 	class which contains getters and setters for the various datas.
 */
public class ProductGroup extends ProductContainer{

	/* A pointer to the ProductContainer that this group belongs to */
	private ProductContainer _parent;
	/* A pointer to the StorageUnit that holds this group. Can be the same
	 * as _parent */
	private ProductContainer _rootParent;
	/* A variable representing the three month supply for this group. */
	private int _threeMosSupply;

	/**
	 * No-args constructor.
	 */
	public ProductGroup(){
		super();
		_parent = NULL;
		_rootParent = NULL;
	}

	/**
	 * Expects the parent group, and the storage unit that this group belongs
	 * to. They can be the same value. Also expects some non-negative integer
	 * for the three-moth supply for this group.
	 */
	public ProductGroup(ProductGroup parent, ProductGroup rootParent, 
	                    int threeMosSupply){
		super();
		_parent = parent;
		_rootParent = rootParent;
		_threeMosSupply = threeMosSupply;
	}

	/**
	 * Returns the three-month supply for this group as an int. */	
	public int getThreeMosSupply(){
		return _threeMosSupply;
	}

	/**
	 * Allows a non-zero integer to be set for the three month supply.
	 */
	public result setThreeMosSupply(int value){
		// Do checks
		_threeMosSupply = value;
		return Result(true, "Successfully set.")
	}
	
}