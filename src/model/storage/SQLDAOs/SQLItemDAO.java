/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.item.Item;

import java.sql.*;
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
			statement.setTimestamp(4, new Timestamp(item.getEntryDate().getMillis()));
			statement.setTimestamp(5, new Timestamp(item.getExitDate().getMillis()));
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
			statement.setTimestamp(3, new Timestamp(item.getEntryDate().getMillis()));
			statement.setTimestamp(4, new Timestamp(item.getExitDate().getMillis()));
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
		// TODO Auto-generated method stub
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
