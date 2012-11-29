package model.storage.SQLDAOs;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.common.IModel;
import model.item.Item;
import model.storage.IStorageDAO;
import model.storage.SQLDAOFactory;
import model.storage.serialization.ItemDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Result;

public class ItemDAOTest {
	private SQLDAOFactory _factory = new SQLDAOFactory();
	private IStorageDAO _dao = _factory.getItemDAO();
	private Connection _connection;
	private Item _item = new Item().generateTestData();

	@Before
	public void setUp() throws Exception {
		_factory.initializeConnection();
		_factory.startTransaction();
		_connection = _factory.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		_factory.endTransaction(true);
	}

	@Test
	public void testAdd() {
		Result r = _dao.insert(_item);
		assertTrue(r.getMessage(), r.getStatus());
		r = _dao.delete(_item);
	}
	
	@Test
	public void testUpdate() {
		_item.setProductId(5);
		Result r = _dao.insert(_item);
		assertTrue(r.getMessage(), r.getStatus());
		_item.setProductId(10);
		r = _dao.update(_item);
		assertTrue(r.getMessage(), r.getStatus());
		r = _dao.delete(_item);
	}
		
	@Test
	public void testGet() {
		_item.setProductId(6);
		Result r = _dao.insert(_item);
		assertTrue(r.getMessage(), r.getStatus());
		IModel newItem = _dao.get(_item.getId());
		assertEquals(_item.getProductId(), ((Item)newItem).getProductId());
		r = _dao.delete(_item);
	}
}
