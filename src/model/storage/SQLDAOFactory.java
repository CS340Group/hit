/**
 * 
 */
package model.storage;

import model.storage.SQLDAOs.SQLItemDAO;
import model.storage.SQLDAOs.SQLProductDAO;
import model.storage.SQLDAOs.SQLProductGroupDAO;
import model.storage.SQLDAOs.SQLStorageUnitDAO;

import common.Result;
import java.sql.*;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import sun.font.TrueTypeFont;


/**
 * Concrete factory for creating DAOs that use SQL for storage of models.
 */
public class SQLDAOFactory implements IDAOFactory {
	
	private static Connection _connection;

	@Override
	public IStorageDAO getItemDAO() {
		return new SQLItemDAO();
	}

	@Override
	public IStorageDAO getProductDAO() {
		return new SQLProductDAO();
	}

	@Override
	public IStorageDAO getProductGroupDAO() {
		return new SQLProductGroupDAO();
	}

	@Override
	public IStorageDAO getStorageUnitDAO() {
		return new SQLStorageUnitDAO();
	}

	@Override
	public Result startTransaction() {
		String connectionURL = "jdbc:sqlite:grp5db.sqlite";
		try {
			assert(_connection == null);
			_connection = DriverManager.getConnection(connectionURL);
			_connection.setAutoCommit(false);
			return new Result(true);
		} catch (SQLException e) {
			return new Result(false, e.getMessage());
		}
	}

	@Override
	public Result endTransaction(boolean commit) {
		Result result = new Result();
		try {
			if(commit){
				_connection.commit();
			}else{
				_connection.rollback();
			}
		} catch (SQLException e) {
			result.setMessage(e.getMessage());
			result.setStatus(false);
		} finally {
			result = closeConnection();
		}
		return result;
	}

	private Result closeConnection(){
		if(_connection != null){
			try {
				_connection.close();
			} catch (SQLException e) {
				return new Result(false, e.getMessage());
			}
		}
		return new Result(true);
	}

	@Override
	public Connection getConnection() {
		return _connection;
	}

	@Override
	public Result initializeConnection() {
		try {
			String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			return new Result(true);
		} catch (ClassNotFoundException e) {
			return new Result(false, e.getMessage());
		}
	}

}
