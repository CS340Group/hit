package model.productcontainer;

import model.common.Unit;
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
public class ProductGroupVaultTest {
    ProductGroup pg1,pg2;
    StorageUnit su;

    @Before
    public void setUp() throws Exception {
        su = new StorageUnit();
        su.setName("Unit1");
        su.validate();
        su.save();

        pg1 = new ProductGroup();
        pg1.setName("Group A");
        pg1.setParentId(-1);
        pg1.setRootParentId(su.getId());
        pg1.set3MonthSupply(new Unit(3, "oz"));
        pg1.validate();
        pg1.save();

        pg2 = new ProductGroup();
        pg2.setName("Group B");
        pg2.setParentId(pg1.getId());
        pg2.setRootParentId(su.getId());
        pg2.set3MonthSupply(new Unit(3, "oz"));
    }

    @After
    public void tearDown() throws Exception {
        StorageUnitVault.clear();
        ProductGroupVault.clear();
    }

    @Test
    public void testFind() throws Exception {
        assertEquals(pg1.getId(), ProductGroupVault.find("Name = "+pg1.getName()).getId());
        assertEquals(pg1.getId(), ProductGroupVault.find("ParentId = "+pg1.getParentId()).getId());
        assertEquals(pg1.getId(), ProductGroupVault.find("RootParentId = "+pg1.getRootParentId()).getId());
        assertEquals(pg1.getId(), ProductGroupVault.find("3MonthSupply = "+pg1.get3MonthSupply()).getId());
        assertNull(ProductGroupVault.find("Name = blah"));
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals(1,ProductGroupVault.findAll("Name = "+pg1.getName()).size());
        assertEquals(0,ProductGroupVault.findAll("Name = blah").size());
    }

    @Test
    public void testGet() throws Exception {
        assertNotSame(pg1, ProductGroupVault.get(pg1.getId()));
        assertEquals(pg1.getId(), ProductGroupVault.get(pg1.getId()).getId());
    }
}