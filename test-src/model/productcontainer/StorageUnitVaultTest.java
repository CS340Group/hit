package model.productcontainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
        StorageUnitVault.clear();
    }

    @Test
    public void testFind() throws Exception {
        assertEquals(su1.getId(), StorageUnitVault.find("Name = "+su1.getName()).getId());
        assertNull(StorageUnitVault.find("Name = blah"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals(1,StorageUnitVault.findAll("Name = "+su1.getName()).size());
        assertEquals(0,StorageUnitVault.findAll("Name = blah").size());
    }

    @Test
    public void testGet() throws Exception {
        assertNotSame(su1, StorageUnitVault.get(su1.getId()));
        assertEquals(su1.getId(), StorageUnitVault.get(su1.getId()).getId());
    }
}
