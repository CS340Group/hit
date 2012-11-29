/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;
import model.storage.IStorageDAO;

import common.Result;
import model.storage.SQLDAOFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides the functionality of accessing the stored information for a storage unit.
 */
public class SQLStorageUnitDAO implements IStorageDAO {

    private SQLDAOFactory _factory = new SQLDAOFactory();
    private StorageUnitVault _vault = StorageUnitVault.getInstance();

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
        _vault.clear();
        PreparedStatement statement;
        try {
            String query = "SELECT id,name,rootParentId FROM storageUnit;";
            statement = _factory.getConnection().prepareStatement(query);
            ResultSet rSet = statement.executeQuery();
            while(rSet.next()){
                StorageUnit su = new StorageUnit();
                su.setId(rSet.getInt(1));
                su.setName(rSet.getString(2));
                su.setRootParentId(rSet.getInt(3));
                su.setValid(true);
                Result result = su.save();
                assert(result.getStatus());
            }
        } catch (SQLException e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true);
	}

	@Override
	public Result saveAllData() {
        ArrayList<StorageUnit> sus = _vault.findAll("Id > %o", 0);
        Result ultimateResult = new Result(true);
        for(StorageUnit su : sus) {
            Result result = this.insert(su);
            if (result.getStatus() == false) {
                result = this.update(su);
                if (result.getStatus() == false) ultimateResult = new Result(false, "Not all items were saved.");
            }
        }
        return ultimateResult;
	}

}
