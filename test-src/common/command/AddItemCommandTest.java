package common.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.StorageUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Result;

public class AddItemCommandTest {
	
	ItemVault _vault;
	private ProductVault _pvault; 
	ArrayList<Item> _itemList;
	Product _product;
	StorageUnit _su;

	@Before
	public void setUp() throws Exception {
		_vault = ItemVault.getInstance();
		_pvault = ProductVault.getInstance();
		
		assertEquals(0, _vault.size());
		
		/* Create the item and SU to add */
		_itemList = new ArrayList<Item>();
		_product = new Product().generateTestData();
		_su = new StorageUnit().generateTestData();
		_su.save();
		_product.setStorageUnitId(_su.getId());
	}

	@After
	public void tearDown() throws Exception {
		_vault.clear();
	}

	@Test
	public void testCreatedProduct() {
		Item i = new Item().generateTestData();
		
		i.setProduct(_product);
		_itemList.add(i);
		
		AddItemCommand cmd = new AddItemCommand(_itemList, _product, _su);
		
		Result r = cmd.execute();
		assertEquals("", r.getMessage());
		assertEquals(1, _vault.size());
		assertTrue(_vault.find("BarcodeString = " + i.getBarcodeString()) != null);
		assertEquals(1, _pvault.size());
		
		
		cmd.undo();
		assertEquals(0, _vault.size());
		assertEquals(null, _vault.find("BarcodeString = " + i.getBarcodeString()));
		assertEquals(0, _pvault.size());
	}
	
	@Test
	public void addMultipleItems() {
		for (int i = 0; i < 10; i++) {
			Item it = new Item().generateTestData();
			it.setProduct(_product);
			_itemList.add(it);
		}
		
		AddItemCommand cmd = new AddItemCommand(_itemList, _product, _su);
		
		Result r = cmd.execute();
		assertEquals("", r.getMessage());
		assertEquals(10, _vault.size());
		assertEquals(1, _pvault.size());
		
		cmd.undo();
		assertEquals(0, _vault.size());
		assertEquals(0, _pvault.size());
	}	
	
	@Test
	public void testProductExisting() {
		Item i = new Item().generateTestData();
		_product.save();
		
		i.setProduct(_product);
		_itemList.add(i);
		
		AddItemCommand cmd = new AddItemCommand(_itemList, null, _su);
		
		Result r = cmd.execute();
		assertEquals("", r.getMessage());
		assertEquals(1, _vault.size());
		assertTrue(_vault.find("BarcodeString = " + i.getBarcodeString()) != null);
		assertEquals(1, _pvault.size());
		
		
		cmd.undo();
		assertEquals(0, _vault.size());
		assertEquals(null, _vault.find("BarcodeString = " + i.getBarcodeString()));
		assertEquals(1, _pvault.size());
	}

}
