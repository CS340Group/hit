package common.command;

import static org.junit.Assert.*;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Result;

public class TransferItemCommandTest {
	
	StorageUnit su1, su2;
	ProductGroup pg1;
	Product p1, p2;
	Item i1;
	Result result;
	private ItemVault _itemVault = ItemVault.getInstance();
	
	@Before
	public void setUp() throws Exception {
		su1 = new StorageUnit().generateTestData();
		su1.setName("Spam");
		su2 = new StorageUnit().generateTestData();
		su2.setName("Eggs");
		
		// Clear out the vaults.
		su1._itemVault.clear();
		su1._productGroupVault.clear();
		su1._productVault.clear();
		su1._storageUnitVault.clear();
		
		result = su1.save();
		result = su2.save();
		
		pg1 = new ProductGroup();
		pg1.setParentId(su2.getId());
		pg1.setName("Target Product Group");
		
		p1 = new Product().generateTestData();
		p1.setBarcode("foo");
		p1.setStorageUnitId(su1.getId());
		p1.save();
		p2 = new Product().generateTestData();
		p2.setBarcode("foo");
		p2.setStorageUnitId(su2.getId());
		p2.setContainerId(pg1.getId());
		p2.save();
		
		i1 = new Item().generateTestData();
		i1.setProduct(p1);
		i1.save();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void targetHasProduct() {
		assertEquals(su1.getId(), i1.getProductStorageUnitIdInt());
		assertEquals(p1.getId(), i1.getProductId()); 
		assertEquals(-1, i1.getProduct().getProductContainerId());
		assertNotNull(_itemVault.find("Id = %o", i1.getId()));
		
		TransferItemCommand command = new TransferItemCommand(su2, i1);
		
		command.execute();
		assertEquals(su2.getId(), i1.getProductStorageUnitIdInt());
		assertEquals(p2.getId(), i1.getProductId()); 
		assertEquals(pg1.getId(), i1.getProduct().getProductContainerId());
		
		command.undo();
		assertEquals(su1.getId(), i1.getProductStorageUnitIdInt());
		assertEquals(p1.getId(), i1.getProductId()); 
		assertEquals(-1, i1.getProduct().getProductContainerId());
		
		command.execute();
		assertEquals(su2.getId(), i1.getProductStorageUnitIdInt());
		assertEquals(p2.getId(), i1.getProductId()); 
		assertEquals(pg1.getId(), i1.getProduct().getProductContainerId());
	}
	
	@Test
	public void targetHasNoProduct() {
		Product p3 = new Product().generateTestData();
		p3.setStorageUnitId(su1.getId());
		p3.setBarcode("Spam");
		p3.save();
		Item i2 = new Item().generateTestData();
		i2.setProduct(p3);
		
		assertEquals(su1.getId(), i2.getProductStorageUnitIdInt());
		assertEquals(p3.getId(), i2.getProductId()); 
		assertEquals(-1, i1.getProduct().getProductContainerId());
		
		TransferItemCommand command = new TransferItemCommand(su2, i2);
		
		command.execute();
		assertEquals(su2.getId(), i2.getProductStorageUnitIdInt());
		assertFalse(p3.getId() == i2.getProductId()); 
		assertEquals(-1 , i2.getProduct().getProductContainerId());
		
		command.undo();
		assertEquals(su1.getId(), i2.getProductStorageUnitIdInt());
		assertEquals(p3.getId(), i2.getProductId()); 
		assertEquals(-1, i2.getProduct().getProductContainerId());
		
		command.execute();
		assertEquals(su2.getId(), i2.getProductStorageUnitIdInt());
		assertFalse(p3.getId() == i2.getProductId()); 
		assertEquals(-1, i2.getProduct().getProductContainerId());
	}

}
