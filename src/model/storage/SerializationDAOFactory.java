/**
 * 
 */
package model.storage;

import com.sun.corba.se.pept.transport.Connection;

import model.common.IModel;
import model.storage.SQLDAOs.ItemDAO;
import model.storage.SQLDAOs.ProductDAO;
import model.storage.SQLDAOs.ProductGroupDAO;
import model.storage.SQLDAOs.StorageUnitDAO;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result endTransaction(boolean commit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
