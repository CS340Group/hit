/**
 * 
 */
package model.storage;

import model.storage.serialization.*;
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

	@Override
	public Result startTransaction() {
		return new Result(true);
	}

	@Override
	public Result endTransaction(boolean commit) {
		return new Result(true);
	}

	@Override
	public java.sql.Connection getConnection() {
		return null;
	}

	@Override
	public Result initializeConnection() {
		return new Result(true);
	}

	

}
