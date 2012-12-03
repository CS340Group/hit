/**
 *
 */
package model.storage.SQLDAOs;

import common.Result;
import model.common.IModel;
import model.common.Size;
import model.product.Product;
import model.product.ProductVault;
import model.storage.IStorageDAO;
import model.storage.SQLDAOFactory;
import model.storage.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    "                     id" +
                    "                     )" +
                    "                     VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            statement = _factory.getConnection().prepareStatement(query);
            FillStatementFromProduct(statement, product);
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
                    "            storageUnitId=?," +
                    "            parentId=?," +
                    "            barcode=?," +
                    "            MonthSupply=?," +
                    "            sizeAmount=?," +
                    "            sizeUnit=?," +
                    "            deleted=?," +
                    "            description=?," +
                    "            shelfLife=?," +
                    "            creationDate=? where id=?";
            statement = _factory.getConnection().prepareStatement(query);
            FillStatementFromProduct(statement, product);
            statement.executeUpdate();
        } catch (SQLException e) {
            assert false;
            return new Result(false, e.getMessage());
        }
        return new Result(true);
    }

    private void FillStatementFromProduct(PreparedStatement statement, Product product)
            throws SQLException {
        int i = 1;
        statement.setInt(i++, product.getStorageUnitId());
        statement.setInt(i++, product.getProductContainerId());
        statement.setString(i++, product.getBarcode());
        statement.setInt(i++, product.get3MonthSupply());
        statement.setFloat(i++, product.getSize().getAmount());
        statement.setString(i++, product.getSize().getSizeType());
        statement.setBoolean(i++, product.getDeleted());
        statement.setString(i++, product.getDescription());
        statement.setInt(i++, product.getShelfLife());
        statement.setString(i++, SQLUtils.DateToLongString(product.getCreationDate()));
        statement.setInt(i++, product.getId());
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
            while (rSet.next()) {
                product = new Product();
                fillProductFromResultSet(product, rSet);
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
                    " FROM product;";
            statement = _factory.getConnection().prepareStatement(query);
            ResultSet rSet = statement.executeQuery();
            while (rSet.next()) {
                product = new Product();
                fillProductFromResultSet(product, rSet);
                product.save();
            }
        } catch (SQLException e) {
            return new Result(false, e.getMessage());
        }
        return new Result(true);
    }

    private void fillProductFromResultSet(Product product, ResultSet rSet) throws SQLException {
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
        product.setCreationDate(SQLUtils.DateFromLongString(rSet.getString(11)));
    }

    @Override
    public Result saveAllData() {
        return new Result(true);
    }
}
