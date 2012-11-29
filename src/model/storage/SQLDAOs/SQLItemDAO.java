/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.item.Item;

import java.sql.*;

import org.joda.time.DateTime;

import model.storage.IStorageDAO;
import model.storage.SQLDAOFactory;

import common.Result;

/**
 * Provides the functionality of accessing the stored information for an item.
 */
public class SQLItemDAO implements IStorageDAO {

	private SQLDAOFactory _factory = new SQLDAOFactory();
	private Connection _connection;
	
	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
		PreparedStatement statement;
		Item item = (Item) model;
		try {
			String query = "INSERT INTO main.item (id,productId,barcode,entryTime,exitTime,deleted) VALUES (?,?,?,?,?,?);";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, item.getId());
			statement.setInt(2, item.getProductId());
			statement.setString(3, item.getBarcodeString());
			statement.setLong(4, new Long(item.getEntryDate().getMillis()));
			statement.setLong(5, new Long(item.getExitDate().getMillis()));
			statement.setBoolean(6, item.getDeleted());
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
			String query = "UPDATE main.item SET productId=?,barcode=?,entryTime=?,exitTime=?,deleted=? where id=?";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, item.getProductId());
			statement.setString(2, item.getBarcodeString());
			statement.setLong(3, new Long(item.getEntryDate().getMillis()));
			statement.setLong(4, new Long(item.getExitDate().getMillis()));
			statement.setBoolean(5, item.getDeleted());
			statement.setInt(6, item.getId());
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
		Item item = (Item) model;
		try {
			String query = "DELETE FROM main.item WHERE id=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, item.getId());
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
		Item item = null;
		try {
			String query = "SELECT productId,entryTime,exitTime,deleted FROM main.item WHERE id=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
				item = new Item();
				item.setId(id);
				item.setProductId(rSet.getInt(1));
				Long t = rSet.getLong(2);
				item.setEntryDate(new DateTime(rSet.getLong(2)));
				item.setExitDate(new DateTime(rSet.getLong(3)));
				item.setDeleted(rSet.getBoolean(4));
			}
		} catch (SQLException e) {
			return null;
		}
		return item;
	}

	@Override
	public Result loadAllData() {
		ItemVault vault = ItemVault.getInstance();
		PreparedStatement statement;
		try {
			String query = "SELECT id,productId,entryTime,exitTime,deleted FROM item;";
			statement = _factory.getConnection().prepareStatement(query);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
				Item item = new Item();
				item.setId(rSet.getInt(1));
				item.setProductId(rSet.getInt(2));
				item.setEntryDate(new DateTime(rSet.getLong(3)));
				item.setExitDate(new DateTime(rSet.getLong(4)));
				item.setDeleted(rSet.getBoolean(5));
				item.setValid(true);
				Result result = item.save();
				assert(result.getStatus());
			}
		} catch (SQLException e) {
			return new Result(false, e.getMessage());
		}
		return new Result(true);
	}

	@Override
	public Result saveAllData() {
		// TODO Auto-generated method stub
		return null;
	}

}
