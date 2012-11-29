/**
 * 
 */
package model.storage.SQLDAOs;

import model.common.IModel;
import model.common.Size;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.storage.IStorageDAO;

import common.Result;
import model.storage.SQLDAOFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides the functionality of accessing the stored information for a product group.
 */
public class SQLProductGroupDAO implements IStorageDAO {

    private SQLDAOFactory _factory = new SQLDAOFactory();
    private ProductGroupVault _vault = ProductGroupVault.getInstance();

    /* (non-Javadoc)
	 * @see model.storage.IStorageDAO#insert(model.common.IModel)
	 */
	@Override
	public Result insert(IModel model) {
        PreparedStatement statement;
        ProductGroup pg = (ProductGroup) model;
        try {
            String query = "INSERT INTO productGroup (id,name,rootParentId,parentId,MonthSupplyAmount, MonthSupplyUnit) VALUES (?,?,?,?,?,?);";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, pg.getId());
            statement.setString(2, pg.getName());
            statement.setInt(3, pg.getRootParentId());
            statement.setInt(4, pg.getParentId());
            statement.setFloat(5, pg.get3MonthSupply().getAmount());
            statement.setString(6, pg.get3MonthSupply().getUnit());
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
        ProductGroup pg = (ProductGroup) model;
        try {
            String query = "UPDATE storageUnit SET name=?,rootParentId=?,parentId=?,MonthSupplyAmount=?,MonthSupplyUnit=? where id=?";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setString(1, pg.getName());
            statement.setInt(2, pg.getRootParentId());
            statement.setInt(3, pg.getParentId());
            statement.setFloat(4, pg.get3MonthSupply().getAmount());
            statement.setString(5, pg.get3MonthSupply().getUnit());
            statement.setInt(6, pg.getId());
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
        ProductGroup pg = (ProductGroup) model;
        try {
            String query = "DELETE FROM storageUnit WHERE id=?;";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, pg.getId());
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
        ProductGroup pg = null;
        try {
            String query = "SELECT name,rootParentId,parentId,MonthSupplyAmount,MonthSupplyUnit WHERE id=?;";
            statement = _factory.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rSet = statement.executeQuery();
            while(rSet.next()){
                pg = new ProductGroup();
                pg.setId(id);
                pg.setName(rSet.getString(1));
                pg.setRootParentId(rSet.getInt(2));
                pg.setParentId(rSet.getInt(3));
                pg.set3MonthSupply(new Size(rSet.getFloat(4), rSet.getString(5)));
                pg.setValid(true);
            }
        } catch (SQLException e) {
            return null;
        }
        return pg;
	}

	@Override
	public Result loadAllData() {
        _vault.clear();
        PreparedStatement statement;
        try {
            String query = "SELECT id,name,rootParentId,parentId,MonthSupplyAmount,MonthSupplyUnit FROM productGroup;";
            statement = _factory.getConnection().prepareStatement(query);
            ResultSet rSet = statement.executeQuery();
            while(rSet.next()){
                ProductGroup pg = new ProductGroup();
                pg.setId(rSet.getInt(1));
                pg.setName(rSet.getString(2));
                pg.setRootParentId(rSet.getInt(3));
                pg.setParentId(rSet.getInt(4));
                pg.set3MonthSupply(new Size(rSet.getFloat(5), rSet.getString(6)));
                pg.setValid(true);
                Result result = pg.save();
                assert(result.getStatus());
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
