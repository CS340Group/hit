package model.productcontainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 9/23/12
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class StorageUnitVaultTest {
    StorageUnit su1,su2;

    @Before
    public void setUp() throws Exception {
        su1 = new StorageUnit();
        su1.setName("Unit A");
        su1.validate();
        su1.save();

        su2 = new StorageUnit();
        su2.setName("Unit B");
        su2.validate();
        su2.save();
    }

    @After
    public void tearDown() throws Exception {
        StorageUnitVault.getInstance().clear();
    }

    @Test
    public void testFind() throws Exception {
        assertEquals(su1.getId(), StorageUnitVault.getInstance().find("Name = %o", su1.getName()).getId());
        assertNull(StorageUnitVault.getInstance().find("Name = blah"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals(1,StorageUnitVault.getInstance().findAll("Name = %o", su1.getName()).size());
        assertEquals(0,StorageUnitVault.getInstance().findAll("Name = blah").size());
    }

    @Test
    public void testGet() throws Exception {
        assertNotSame(su1, StorageUnitVault.getInstance().get(su1.getId()));
        assertEquals(su1.getId(), StorageUnitVault.getInstance().get(su1.getId()).getId());
    }
    
    /*
     * Make sure duplicate Storage Unit names can't exist
     */
    @Test
    public void testDuplicateNames() throws Exception{
    	su2.setName("Unit A");
    	assertFalse("The Vault is allowing for duplicate Storage Unit Names",su2.validate().getStatus());
    }
    
    /*
     * Check modified save
     */
    @Test
    public void testModifiedSave() throws Exception{
    	su1.setName("NewName");
    	su1.validate();
    	su1.save();
    	StorageUnit su3 = su1._storageUnitVault.find("Name = NewName");
    	su1 = su1._storageUnitVault.find("Name = %o", "Unit A");
    	assertTrue("Save modified is not working",su1 == null);
    	assertTrue("Save modified is not working",su3 != null);
    }
}
