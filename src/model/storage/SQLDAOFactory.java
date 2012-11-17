/**
 * 
 */
package model.storage;

import model.common.IModel;

import common.Result;

/**
 * Concrete factory for creating DAOs that use SQL for storage of models.
 */
public class SQLDAOFactory implements IStorageDAO {

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#update(model.common.IModel)
	 */
	@Override
	public Result update(IModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#delete(model.common.IModel)
	 */
	@Override
	public Result delete(IModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#get(int)
	 */
	@Override
	public IModel get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
