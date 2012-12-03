/**
 * 
 */
package model.storage;

import common.Result;
import java.sql.*;

/**
 * @author murphyra
 * An abstract factory for creating data access objects.
 */
public interface IDAOFactory {
	
	/**
	 * @return an item data access object.
	 * The implementation of the DAO is defined by the implementation of this interface.
	 */
	public IStorageDAO getItemDAO();
	
	/**
	 * @return a product data access object.
	 * The implementation of the DAO is defined by the implementation of this interface.
	 */
	public IStorageDAO getProductDAO();
	
	/**
	 * @return a product group data access object.
	 * The implementation of the DAO is defined by the implementation of this interface.
	 */
	public IStorageDAO getProductGroupDAO();
	
	/**
	 * @return a storage unit data access object.
	 * The implementation of the DAO is defined by the implementation of this interface.
	 */
	public IStorageDAO getStorageUnitDAO();
	
	public IStorageDAO getMiscStorageDAO();
	
	/**
	 * Starts a transaction with the DB (if appropriate).
	 * If the program is not running in SQL mode, no action is taken.
	 * @return False if there was some DB error, else true.
	 */
	public Result startTransaction();
	
	/**
	 * Ends the transaction with the DB (if appropriate).
	 * If the program is not running in SQL mode, no action is taken.
	 * @param commit Whether the transaction should be committed, or rolled back.
	 * @return False if there was some DB error, else true.
	 */
	public Result endTransaction(boolean commit);

	/**
	 * Returns a connection to the DB.
	 * If the program is running in SQL mode, NULL is returned.
	 * @return a pointer to a Connection, or NULL.
	 */
	public Connection getConnection();
	
	/**
	 * Initialize the connection to the DB.
	 * @return A Result indicating the success of the initialization.
	 */
	public Result initializeConnection();

}
