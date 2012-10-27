package common.command;

import static org.junit.Assert.*;

import model.item.Item;
import model.item.ItemVault;
import model.productcontainer.StorageUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		Item i = new Item().generateTestData();
		StorageUnit su = new StorageUnit().generateTestData();
		AddItemCommand cmd = new AddItemCommand(i, su);
		
		cmd.execute();
		assertEquals(1, _vault.size());
		assertTrue(_vault.find("BarcodeString = " + i.getBarcodeString()) != null);
		
		cmd.undo();
		assertEquals(0, _vault.size());
		assertEquals(null, _vault.find("BarcodeString = " + i.getBarcodeString()));
	}

}
