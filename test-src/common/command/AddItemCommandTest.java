package common.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.productcontainer.StorageUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Result;

public class AddItemCommandTest {
	
	ItemVault _vault; 

	@Before
	public void setUp() throws Exception {
		_vault = ItemVault.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		_vault.clear();
	}

	@Test
	public void test() {
		
		assertEquals(0, _vault.size());
		
		/* Create the item and SU to add */
		ArrayList<Item> itemList = new ArrayList<Item>();
		Item i = new Item().generateTestData();
		Product p = new Product().generateTestData();
		StorageUnit su = new StorageUnit().generateTestData();
		su.save();
		
		p.setStorageUnitId(su.getId());
		i.setProduct(p);
		itemList.add(i);
		
		AddItemCommand cmd = new AddItemCommand(itemList, p, su);
		
		Result r = cmd.execute();
		assertEquals("", r.getMessage());
		assertEquals(1, _vault.size());
		assertTrue(_vault.find("BarcodeString = " + i.getBarcodeString()) != null);
		
		cmd.undo();
		assertEquals(0, _vault.size());
		assertEquals(null, _vault.find("BarcodeString = " + i.getBarcodeString()));
	}

}
