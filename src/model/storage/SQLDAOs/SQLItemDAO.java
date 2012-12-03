package model.storage.SQLDAOs;

import common.Result;
import model.common.IModel;
import model.item.Item;
import model.item.ItemVault;
import model.storage.IStorageDAO;
import model.storage.SQLDAOFactory;
import model.storage.SQLUtils;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides the functionality of accessing the stored information for an item.
 */
public class SQLItemDAO implements IStorageDAO {

	private SQLDAOFactory _factory = new SQLDAOFactory();
	private ItemVault _vault = ItemVault.getInstance();

    /* (non-Javadoc)
     * @see model.storage.IStorageDAO#insert(model.common.IModel)
     */
	@Override
	public Result insert(IModel model) {
		PreparedStatement statement;
		Item item = (Item) model;
		try {
			String query = "INSERT INTO item (productId,barcode,entryTime,exitTime,deleted,id" +
                    ") VALUES (?,?,?,?,?,?);";
			statement = _factory.getConnection().prepareStatement(query);
			fillStatementFromItem(statement, item);
			statement.executeUpdate();
		} catch (SQLException e) {
			return new Result(false, e.getMessage());
		}
		return new Result(true);
	}

	private void fillStatementFromItem(PreparedStatement statement, Item item) throws SQLException {
        int i=1;
		statement.setInt(i++, item.getProductId());
		statement.setString(i++, item.getBarcodeString());
        statement.setString(i++, SQLUtils.DateToLongString(item.getEntryDate()));
        statement.setString(i++, SQLUtils.DateToLongString(item.getExitDate()));
		statement.setBoolean(i++, item.getDeleted());
        statement.setInt(i++, item.getId());
	}

	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#update(model.common.IModel)
	 */
	@Override
	public Result update(IModel model) {
		PreparedStatement statement;
		Item item = (Item) model;
		try {
			String query = "UPDATE item SET productId=?,barcode=?,entryTime=?,exitTime=?,deleted=? where id=?";
			statement = _factory.getConnection().prepareStatement(query);
			fillStatementFromItem(statement, item);
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
			String query = "DELETE FROM item WHERE id=?;";
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
			String query = "SELECT id,productId,entryTime,exitTime,deleted," +
                    "barcode FROM item WHERE id=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
                item = fillItemFromResultSet(rSet);
			}
		} catch (SQLException e) {
            item = null;
		}
		return item;
	}

	@Override
	public Result loadAllData() {
		_vault.clear();
		PreparedStatement statement;
		try {
			String query = "SELECT id,productId,entryTime,exitTime,deleted,barcode FROM item;";
			statement = _factory.getConnection().prepareStatement(query);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
				Item item = fillItemFromResultSet(rSet);
				Result result = item.save();
				assert(result.getStatus());
			}
		} catch (SQLException e) {
			return new Result(false, e.getMessage());
		}
		return new Result(true);
	}

	private Item fillItemFromResultSet(ResultSet rSet) throws SQLException {
		Item item = new Item();
        int i = 1;
		item.setId(rSet.getInt(i++));
		item.setProductId(rSet.getInt(i++));
        item.setEntryDate(SQLUtils.DateFromLongString(rSet.getString(i++)));
        item.setExitDate(SQLUtils.DateFromLongString(rSet.getString(i++)));
        item.setDeleted(rSet.getBoolean(i++));
		item.generateBarcodeFromString(rSet.getString(i++));
		item.setValid(true);
		return item;
	}

	@Override
	public Result saveAllData() {
		return new Result(true);
	}
}
