/**
 *
 */
package model.storage.serialization;

import common.Result;
import model.common.IModel;
import model.storage.IStorageDAO;

/**
 * Provides the functionality of accessing the stored information for a storage unit.
 */
public class StorageUnitDAO implements IStorageDAO {

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result saveAllData() {
        // TODO Auto-generated method stub
        return null;
    }

}
