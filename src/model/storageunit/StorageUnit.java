package model.storageunit;

import model.productcontainer.ProductContainer;
import common.Result;

/**
 * The StorageUnit class encapsulates all the funtions and data associated with a "StorageUnit".
 * It extends the {@link model.productcontainer.ProductContainer ProductContainer} 
 * implements the general functions for StorageUnit and for 
 * {@link model.productgroup.ProductGroup ProductGroup}.
 */
public class StorageUnit extends ProductContainer{

    private String _name;

    public StorageUnit(){
        super();
        this._name = "";
    }

    public StorageUnit(StorageUnit storage) {
        super(storage);
        this._name = storage.getName();
    }

	public void setValid(boolean b) {
		// TODO Auto-generated method stub
		
	}

}