package model.productcontainer;

import common.Result;
import model.product.Product;
import model.reports.Ivisitor;

import java.util.ArrayList;

/**
 * The StorageUnit class encapsulates all the funtions and data associated with a "StorageUnit".
 * It extends the {@link model.productcontainer.ProductContainer ProductContainer} 
 * implements the general functions for StorageUnit and for 
 * {@link ProductGroup ProductGroup}.
 */
public class StorageUnit extends ProductContainer{


    /**
     * Constructor.
     */
    public StorageUnit(){
        super();

    }

    /**
     * Copy Constructor.
     */
    public StorageUnit(StorageUnit su){
        super(su);
        assert su != null;
    }

    /**
     * Uses the {@link model.productcontainer.StorageVault StorageVault} to make
     * sure that the proper data constraints for a StorageUnit instance are met.
     * This function must be called and complete successfully before this
     * instance can be saved into the vault. 
     */
    public Result validate(){
        if(getName().isEmpty())
            return new Result(false,"Name cannot be empty");

        if(getId() == -1)
            return _storageUnitVault.validateNew(this);
        else
            return _storageUnitVault.validateModified(this);
    }

    /**
     * Puts a copy of this instance into the {@link
     *  model.productcontainer.StorageVault StorageVault} and links it to all of
     *  its relatives. Before saving, an instance must be validated using the
     *  validate() call. */
    public Result save(){
        if (!this._valid) {
            Result result = this.validate();
            if (!result.getStatus())
                return result;
        }
        if(getId() == -1)
            return _storageUnitVault.saveNew(this);
        else
            return _storageUnitVault.saveModified(this);
    }

    /**
     * Returns a copy of the storage unit that this ProductGroup is contained
     * in.
     */
    public ProductContainer getRootParent(){
        return this;
    }

    /**
     * Set this to the ID of the StorageUnit that this ProductGroup belongs to.
     */
    public Result setRootParentId(int id){
        return new Result(false, "Root Parent of a Storage Unit is immutable");
    }

	public StorageUnit generateTestData() {
		// TODO Auto-generated method stub
		this.setName("Test Storage Unit Name");
		return this;
	}
	
	 public ArrayList<Product> getProducts(){
			return this._productVault.findAll("ContainerId = %o", this.getId());
	}
    public ArrayList<ProductGroup> getProductGroups(){
    	return this._productGroupVault.findAll("ParentId = %o", this.getId());
    }
    
	public void accept(Ivisitor visitor){
		for(ProductGroup productGroup : this.getProductGroups() ){
			productGroup.accept(visitor);
		}
		for(Product product : this.getProducts() ){
			product.accept(visitor);
		}
		visitor.visit(this);
	}
	
	
}