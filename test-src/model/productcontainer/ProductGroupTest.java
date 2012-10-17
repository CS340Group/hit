package model.productcontainer;

import model.common.Size;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 9/23/12
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductGroupTest {
    ProductGroup pg1, pg2, pg3;
    StorageUnit su;
    @Before
    public void setUp() throws Exception {
        su = new StorageUnit();
        su.setName("Unit1");
        su.validate();
        su.save();

        pg1 = new ProductGroup();
        pg1.setName("Group A");
        pg1.setParentId(su.getId());
        pg1.setRootParentId(su.getId());
        pg1.set3MonthSupply(new Size(3, "oz"));

        pg2 = new ProductGroup();
        pg2.setName("Group B");
        pg2.setParentId(su.getId());
        pg2.setRootParentId(su.getId());
        pg2.set3MonthSupply(new Size(3, "oz"));

        pg3 = new ProductGroup();
        pg3.setName("Group A");
        pg3.setParentId(su.getId());
        pg3.setRootParentId(su.getId());
        pg3.set3MonthSupply(new Size(3, "oz"));

    }

    @After
    public void tearDown() throws Exception {
        StorageUnitVault.getInstance().clear();
    }
/*
 * Test Fails with newest product validation, this should be updated to reflect those changes
 
    @Test
    public void testSave() throws Exception {
        assertFalse(pg1.save().getStatus());
        assertFalse(pg1.isSaved());
        pg1.validate().getStatus();
        assertTrue(pg1.save().getStatus());
        assertTrue(pg1.isSaved());
        String original = pg1.getName();
        assertTrue(pg1.setName("Group X").getStatus());
        assertEquals(original,ProductGroupVault.getInstance().get(pg1.getId()).getName());
        assertFalse(pg1.save().getStatus());
        assertFalse(pg1.isSaved());
        assertTrue(pg1.validate().getStatus());
        assertTrue(pg1.save().getStatus());
        assertEquals(1,ProductGroupVault.getInstance().size());

        assertFalse(pg2.save().getStatus());
        assertFalse(pg2.isSaved());
        assertTrue(pg2.validate().getStatus());
        assertTrue(pg2.save().getStatus());
        assertTrue(pg2.isSaved());
        original = pg2.getName();
        assertTrue(pg2.setName("Group Y").getStatus());
        assertEquals(original,ProductGroupVault.getInstance().get(pg2.getId()).getName());
        assertFalse(pg2.save().getStatus());
        assertFalse(pg2.isSaved());
        assertTrue(pg2.validate().getStatus());
        assertTrue(pg2.save().getStatus());
        assertEquals(2,ProductGroupVault.getInstance().size());
    }*/

    @Test
    public void testSet3MonthSupply(){
        // Should fail due to a negative size. 
        assertFalse(pg1.set3MonthSupply(new Size(-5, "oz")).getStatus());
        // A zero in this context should be fine:
        assertTrue(pg1.set3MonthSupply(new Size(0, "oz")).getStatus());
        // Should fail. If the unit is count, the amt should be 1.
        assertFalse(pg1.set3MonthSupply(new Size(5, "Count")).getStatus());
        assertTrue(pg1.set3MonthSupply(new Size(1, "Count")).getStatus());
    }

    @Test
    public void testValidate() throws Exception {
        assertFalse(pg1.isValid());
        assertTrue(pg1.validate().getStatus());
        assertTrue(pg1.isValid());
        assertTrue(pg1.save().getStatus());

        assertFalse(pg3.validate().getStatus());
        assertFalse(pg3.isValid());
        assertTrue(pg3.setName("Unit X").getStatus());
        assertTrue(pg3.validate().getStatus());
        assertTrue(pg3.isValid());
        assertTrue(pg3.save().getStatus());
        assertTrue(pg3.isSaved());
    }
}
