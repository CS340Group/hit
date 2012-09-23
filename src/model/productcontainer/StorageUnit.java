package model.productcontainer;

import common.Result;
/**
 * The StorageUnit class encapsulates all the funtions and data associated with a "StorageUnit".
 * It extends the {@link model.productcontainer.ProductContainer ProductContainer} 
 * implements the general functions for StorageUnit and for 
 * {@link ProductGroup ProductGroup}.
 */
public class StorageUnit extends ProductContainer{


    public StorageUnit(){
        super();
    }

    public Result validate(){
        if(getId() == -1)
            return StorageUnitVault.validateNew(this);
        else
            return StorageUnitVault.validateModified(this);
    }

    public Result save(){
        if(!isValid())
            return new Result(false, "Item must be valid before saving.");
        if(getId() == -1)
            return StorageUnitVault.saveNew(this);
        else
            return StorageUnitVault.saveModified(this);
    }
}