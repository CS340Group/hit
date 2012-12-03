/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.item.Item;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;
import model.storage.IStorageDAO;

import common.Result;
import model.storage.SQLDAOFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.DateTime;

/**
 * Provides the functionality of accessing the stored information for a storage unit.
 */
public class SQLMiscStorageDAO implements IStorageDAO {

    private SQLDAOFactory _factory = new SQLDAOFactory();
    private StorageUnitVault _vault = StorageUnitVault.getInstance();

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
        PreparedStatement statement;
        Item item = (Item) model;
        try {
            String query = "INSERT INTO miscStorage (id,dateTime) VALUES (?,?);";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, 1);
            statement.setLong(2, new Long(item.getEntryDate().getMillis()));
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
        Item item = (Item) model;
        try {
            String query = "UPDATE miscStorage SET dateTime=?, where id=?";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setLong(1, new Long(item.getEntryDate().getMillis()));
            statement.setInt(2, item.getId());
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
        
        return new Result(true);
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#get(int)
	 */
	@Override
	public IModel get(int id) {
        PreparedStatement statement;
        Item item = null;
        try {
            String query = "SELECT datetime FROM miscStorage WHERE id=?;";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rSet = statement.executeQuery();
            while(rSet.next()){
                item = new Item();
                item.setId(id);
                item.setEntryDate(new DateTime(rSet.getLong(1)));
                item.setValid(true);
            }
        } catch (SQLException e) {
            return null;
        }
        return item;
	}

	@Override
	public Result loadAllData() {
        return new Result(true);
	}

	@Override
	public Result saveAllData() {
		return new Result(true);
	}

}
