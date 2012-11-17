/**
 * 
 */
package model.storage;

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

}
