/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.common.Size;
import model.product.Product;
import model.product.ProductVault;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.SelfDescribing;
import org.joda.time.DateTime;

import model.storage.IStorageDAO;
import model.storage.SQLDAOFactory;

import common.Result;

/**
 * Provides the functionality of accessing the stored information for an product.
 */
public class SQLProductDAO implements IStorageDAO {

	private SQLDAOFactory _factory = new SQLDAOFactory();
	private Connection _connection;
	private ProductVault _vault = ProductVault.getInstance();
	
	/* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
		PreparedStatement statement;
		Product product = (Product) model;
		try {
			String query = "INSERT INTO product (" + 
					"                     id," + 
					"                     storageUnitId," + 
					"                     parentId," + 
					"                     barcode," +
					"                     MonthSupply," +
					"                     sizeAmount," + 
					"                     sizeUnit," + 
					"                     deleted," + 
					"                     description," + 
					"                     shelfLife," + 
					"                     creationDate," + 
					"                     )" + 
					"                     VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, product.getId());
			statement.setInt(2, product.getStorageUnitId());
			statement.setInt(3, product.getProductContainerId());
			statement.setString(4, product.getBarcode());
			statement.setInt(5, product.get3MonthSupply());
			statement.setFloat(6, product.getSize().getAmount());
			statement.setString(7, product.getSize().getSizeType());
			statement.setBoolean(8, product.getDeleted());
			statement.setString(9, product.getDescription());
			statement.setInt(10, product.getShelfLife());
			statement.setLong(11, new Long(product.getCreationDate().getMillis()));
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
		Product product = (Product) model;
		try {
			String query = "UPDATE product SET" + 
					"            id=?," + 
					"            storageUnitId=?," + 
					"            parentId=?," + 
					"            barcode=?," + 
					"            MonthSupply=?," +
					"            sizeAmount=?," + 
					"            sizeUnit=?," + 
					"            deleted=?," + 
					"            description=?," + 
					"            shelfLife=?," + 
					"            creationDate=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, product.getId());
			statement.setInt(2, product.getStorageUnitId());
			statement.setInt(3, product.getProductContainerId());
			statement.setString(4, product.getBarcode());
			statement.setInt(5, product.get3MonthSupply());
			statement.setFloat(6, product.getSize().getAmount());
			statement.setString(7, product.getSize().getSizeType());
			statement.setBoolean(8, product.getDeleted());
			statement.setString(9, product.getDescription());
			statement.setInt(10, product.getShelfLife());
			statement.setLong(11, new Long(product.getCreationDate().getMillis()));
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
		Product product = (Product) model;
		try {
			String query = "DELETE FROM product WHERE id=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, product.getId());
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
		Product product = null;
		try {
			String query = "SELECT " + 
					"    id," + 
					"    storageUnitId," + 
					"    parentId," + 
					"    barcode," + 
					"    MonthSupply," +
					"    sizeAmount," + 
					"    sizeUnit," + 
					"    deleted," + 
					"    description," + 
					"    shelfLife," + 
					"    creationDate" + 
					"FROM product WHERE id=?;";
			statement = _factory.getConnection().prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
				product = new Product();
				product.setId(rSet.getInt(1));
				product.setStorageUnitId(rSet.getInt(2));
				product.setProductContainerId(rSet.getInt(3));
				product.setBarcode(rSet.getString(4));
				product.set3MonthSupply(rSet.getInt(5));
				Size newSize = new Size(1, "");
				newSize.setAmount(rSet.getFloat(6));
				newSize.setUnit(rSet.getString(7));
				product.setSize(newSize);
				product.setDeleted(rSet.getBoolean(8));
				product.setDescription(rSet.getString(9));
				product.setShelfLife(rSet.getInt(10));
				product.setCreationDate(new DateTime(rSet.getLong(11)));
			}
		} catch (SQLException e) {
			return null;
		}
		return product;
	}

	@Override
	public Result loadAllData() {
		_vault.clear();
		PreparedStatement statement;
		Product product = null;
		try {
			String query = "SELECT " + 
					"    id," + 
					"    storageUnitId," + 
					"    parentId," + 
					"    barcode," + 
					"    MonthSupply," +
					"    sizeAmount," + 
					"    sizeUnit," + 
					"    deleted," + 
					"    description," + 
					"    shelfLife," + 
					"    creationDate" + 
					"FROM product;";
			statement = _factory.getConnection().prepareStatement(query);
			ResultSet rSet = statement.executeQuery();
			while(rSet.next()){
				product = new Product();
				product.setId(rSet.getInt(1));
				product.setStorageUnitId(rSet.getInt(2));
				product.setProductContainerId(rSet.getInt(3));
				product.setBarcode(rSet.getString(4));
				product.set3MonthSupply(rSet.getInt(5));
				Size newSize = new Size(1, "");
				newSize.setAmount(rSet.getFloat(6));
				newSize.setUnit(rSet.getString(7));
				product.setSize(newSize);
				product.setDeleted(rSet.getBoolean(8));
				product.setDescription(rSet.getString(9));
				product.setShelfLife(rSet.getInt(10));
				product.setCreationDate(new DateTime(rSet.getLong(11)));
				product.save();
			}
		} catch (SQLException e) {
			return new Result(false, e.getMessage());
		}
		return new Result(true);
	}

	@Override
	public Result saveAllData() {
		return new Result(true);
	}
}
