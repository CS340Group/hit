/**
 * 
 */
package model.storage;

import com.sun.corba.se.pept.transport.Connection;
import common.Result;


/**
 * Concrete factory for creating DAOs that use SQL for storage of models.
 */
public class SQLDAOFactory implements IDAOFactory {

	@Override
	public IStorageDAO getItemDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStorageDAO getProductDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStorageDAO getProductGroupDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStorageDAO getStorageUnitDAO() {
		// TODO Auto-generated method stub
		return null;
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
