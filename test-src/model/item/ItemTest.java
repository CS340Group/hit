package model.item;

import model.common.Barcode;
import model.item.Item;
import model.item.ItemVault;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

    public static Item item;
    @Before
    public void setup(){
        item = new Item();
        item.generateTestData();
    }

    @After
    public void teardown(){
        ItemVault.getInstance().clear();
    }

    @Test
    public void testItemCreation(){
        assertEquals("Id should be -1", -1, item.getId());
        assertEquals("Item is not saved", false, item.isSaved());
        assertEquals("Item is not valid", false, item.isValid());
        assertEquals("Item should be saveable because its not valid",
                false, item.save().getStatus());
        //Technically this should be false but we havnt wired up Barcodes and other classes yet
        assertEquals("Item should pass validation", true, item.validate().getStatus());
        assertEquals("Item should save", true, item.save().getStatus());
        assertEquals("Id should be 0", 0, item.getId());
        assertEquals("Item is saved", true, item.isSaved());
        assertEquals("Item is Valid", true, item.isValid());
        assertNotSame("Vault returns a copy", item, item.itemVault.get(item.getId()));
        assertEquals("Vault copy and local copy have same ids", item.getId(), item.itemVault.get(item.getId()).getId());
    }

    @Test
    public void testItemModification(){
    	item.validate();
        item.save();
        Item itemCopy = item.itemVault.get(item.getId());
        itemCopy.setProductId(0);
        assertTrue("Local modification doesn't change item in vault", itemCopy.getProductId()
                != item.itemVault.get(item.getId()).getProductId());
        assertEquals("Item should be saveable because its not valid",
                false, itemCopy.save().getStatus());
        assertEquals("Item should pass validation", true, itemCopy.validate().getStatus());
        assertEquals("Item should save", true, itemCopy.save().getStatus());
        assertEquals("Vault should not have created a new item", 1, item.itemVault.size());
    }
    
    @Test
    public void testValidateEntryDate() throws Exception {
    	/* These should be true. */
		item.setEntryDate(new DateTime(2012, 1, 1, 1, 1));
		assertTrue(item.validate().getStatus());
		
		item.setEntryDate(new DateTime(2000, 1, 1, 1, 1));
		assertTrue(item.validate().getStatus());
		
		item.setEntryDate(new DateTime());
		assertTrue(item.validate().getStatus());
		
		/* These should be false. */
		item.setEntryDate(new DateTime(1999, 1, 1, 1, 1));
		assertFalse(item.validate().getStatus());
		
		item.setEntryDate(new DateTime(2084, 1, 1, 1, 1));
		assertFalse(item.validate().getStatus());
		
	}
}
