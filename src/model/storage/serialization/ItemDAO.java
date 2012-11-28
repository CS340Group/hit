/**
 * 
 */
package model.storage.serialization;

import model.common.IModel;
import model.common.VaultPickler;
import model.storage.IStorageDAO;

import common.Result;

/**
 * Provides the functionality of accessing the stored information for an item.
 */
public class ItemDAO implements IStorageDAO {

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#update(model.common.IModel)
	 */
	@Override
	public Result update(IModel model) {
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#delete(model.common.IModel)
	 */
	@Override
	public Result delete(IModel model) {
		return null;
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#get(int)
	 */
	@Override
	public IModel get(int id) {
		return null;
	}

	@Override
	public Result loadAllData() {
		VaultPickler pickler = new VaultPickler();	
		pickler.DeSerializeMe();
		return new Result(true);
	}

	@Override
	public Result saveAllData() {
		// TODO Auto-generated method stub
		return null;
	}

}
