/**
 * 
 */
package model.storage;

import model.common.IModel;

import common.Result;

/**
 * Concrete factory for producing DAOs that use serialization for storage.
 */
public class SerializationDAOFactory implements IDAOFactory {

	@Override
	public IStorageDAO getItemDAO() {
		return new ItemDAO();
	}

	@Override
	public IStorageDAO getProductDAO() {
		return new ProductDAO();
	}

	@Override
	public IStorageDAO getProductGroupDAO() {
		return new ProductGroupDAO();
	}

	@Override
	public IStorageDAO getStorageUnitDAO() {
		return new StorageUnitDAO();
	}

	

}
