/**
 * 
 */
package model.storage.serialization;

import model.common.IModel;
import model.common.VaultPickler;
import model.storage.IStorageDAO;

import common.Result;

/**
 * Provides the functionality of accessing the stored information for an item..
 * 
 * 
 * This for right now just uses an item model with an entry date to store the one entry date
 * needed for the removed items report.  It's hacked up a little bit but works -Brendon 
 */
public class MiscStorageDAO implements IStorageDAO {

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
		return new Result(true);
	}

	@Override
	public Result saveAllData() {
		return null;
	}

}
