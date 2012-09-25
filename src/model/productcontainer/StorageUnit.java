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

    public StorageUnit(StorageUnit su){
        super(su);
    }

    public Result validate(){
        if(getId() == -1)
            return storageUnitVault.validateNew(this);
        else
            return storageUnitVault.validateModified(this);
    }

    public Result save(){
        if(!isValid())
            return new Result(false, "Item must be valid before saving.");
        if(getId() == -1)
            return storageUnitVault.saveNew(this);
        else
            return storageUnitVault.saveModified(this);
    }
}