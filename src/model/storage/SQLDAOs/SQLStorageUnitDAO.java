/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.productcontainer.StorageUnit;
import model.storage.IStorageDAO;

import common.Result;
import model.storage.SQLDAOFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides the functionality of accessing the stored information for a storage unit.
 */
public class SQLStorageUnitDAO implements IStorageDAO {

    private SQLDAOFactory _factory = new SQLDAOFactory();

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
        PreparedStatement statement;
        StorageUnit su = (StorageUnit) model;
        try {
            String query = "INSERT INTO storageUnit (id,name,rootParentId) VALUES (?,?,?);";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, su.getId());
            statement.setString(2, su.getName());
            statement.setInt(3, su.getRootParentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true);
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#update(model.common.IModel)
	 */
	@Override
	public Result update(IModel model) {
        PreparedStatement statement;
        StorageUnit su = (StorageUnit) model;
        try {
            String query = "UPDATE storageUnit SET name=?,rootParentId=? where id=?";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setString(1, su.getName());
            statement.setInt(2, su.getRootParentId());
            statement.setInt(3, su.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true);
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#delete(model.common.IModel)
	 */
	@Override
	public Result delete(IModel model) {
        PreparedStatement statement;
        StorageUnit su = (StorageUnit) model;
        try {
            String query = "DELETE FROM storageUnit WHERE id=?;";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, su.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true);
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#get(int)
	 */
	@Override
	public IModel get(int id) {
        PreparedStatement statement;
        StorageUnit su = null;
        try {
            String query = "SELECT name,rootParentId WHERE id=?;";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rSet = statement.executeQuery();
            while(rSet.next()){
                su = new StorageUnit();
                su.setId(id);
                su.setName(rSet.getString(1));
                su.setRootParentId(rSet.getInt(2));
                su.setValid(true);
            }
        } catch (SQLException e) {
            return null;
        }
        return su;
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
