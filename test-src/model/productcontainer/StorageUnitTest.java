package model.productcontainer;

import model.item.ItemVault;
import model.product.ProductVault;
import model.storage.SerializationDAOFactory;
import model.storage.StorageManager;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import common.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class StorageUnitTest {
    StorageUnit su1, su2;

    @Before
    public void setup(){
StorageManager.getInstance().setFactory(new  SerializationDAOFactory());
StorageManager.getInstance().hitStart();
    	
    	ItemVault.getInstance().clear();
		ProductVault.getInstance().clear();
		StorageUnitVault.getInstance().clear();
		ProductGroupVault.getInstance().clear();
        su1 = new StorageUnit();
        su1.setName("Unit A");

        su2 = new StorageUnit();
        su2.setName("");
       
    }

    @After
    public void teardown(){
        StorageUnitVault.getInstance().clear();
    }

    @Test
    public void testValidate() throws Exception {
        assertTrue(su1.validate().getStatus());
        // Test for enforced non-empty name -->
        assertFalse(su2.validate().getStatus());
        // ----||.
        su1.save();
        // Test for enforced uniqe name -->
        su2.setName(su1.getName());
        assertFalse(su2.validate().getStatus());
        // ----||.
        su2.setName("Good");
        assertTrue(su2.validate().getStatus());
    }

    @Test
    public void testSave() throws Exception {
    		Result r = su1.save();
        assertFalse(r.getMessage(), r.getStatus());
        su1.validate();
        assertTrue(su1.save().getStatus());
        assertFalse(su2.save().getStatus());
        assertEquals(1, StorageUnitVault.getInstance().size());
        String original = su1.getName();
        su1.setName("Unit X");
        assertEquals(original, StorageUnitVault.getInstance().get(su1.getId()).getName());
    }

    @Test
    public void testModified() throws Exception {
        su1.validate();
        su1.save();
        su1.setName("");
        assertFalse(su1.validate().getStatus());
        su1.setName("Unit X");
        assertTrue(su1.validate().getStatus());
        su1.setName("Unit Z");
        assertFalse(su1.save().getStatus());
        su1.validate();
        assertTrue(su1.save().getStatus());
    }

    @Test
    public void testIsDeleteable() throws Exception {
        assertTrue(su1.isDeleteable().getStatus());    }


}
