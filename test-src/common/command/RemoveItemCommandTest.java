package common.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.StorageUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RemoveItemCommandTest {

	ItemVault _vault;
	ArrayList<Item> _itemList;
	Product _product;
	StorageUnit _su;

	@Before
	public void setUp() throws Exception {
		_vault = ItemVault.getInstance();
		
		assertEquals(0, _vault.size());
		
		/* Create the item and SU to add */
		_itemList = new ArrayList<Item>();
		_product = new Product().generateTestData();
		_su = new StorageUnit().generateTestData();
		_su.save();
		_product.setStorageUnitId(_su.getId());
		for (int i = 0; i < 10; i++) {
			Item it = new Item().generateTestData();
			it.setProduct(_product);
			it.save();
			_itemList.add(it);
		}

	}

	@After
	public void tearDown() throws Exception {
		_vault.clear();
	}

	@Test
	public void testCreatedProduct() {
		Item firstItem = _itemList.remove(0);
		RemoveItemCommand cmd = new RemoveItemCommand(firstItem);
		
		assertEquals(10, _vault.size());
		cmd.execute();
		assertEquals(9, _vault.size());
		cmd.undo();
		assertEquals(10, _vault.size());
		cmd.execute();
		assertEquals(9, _vault.size());
		for(Item item : _itemList)
			new RemoveItemCommand(item).execute();
		assertEquals(0, _vault.size());
	}
}
