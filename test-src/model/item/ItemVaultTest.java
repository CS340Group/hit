package model.item;

import model.common.Barcode;
import model.common.Unit;
import model.product.Product;
import model.product.ProductVault;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 9/22/12
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemVaultTest {
    Product p1,p2;

    Item item1,item2,item3,item4;
    @Before
    public void setUp() throws Exception {
        p1 = new Product();
        p1.setDescription("MyProduct1");
        p1.set3MonthSupply(1);
        p1.setBarcode(new Barcode(1234));
        p1.setContainerId(-1);
        p1.setCreationDate(new DateTime());
        p1.setShelfLife(2);
        p1.setSize(new Unit(3, "oz"));
        p1.setStorageUnitId(-1);
        p1.validate();
        p1.save();

        p2 = new Product();
        p2.setDescription("MyProduct2");
        p2.set3MonthSupply(2);
        p2.setBarcode(new Barcode(2345));
        p2.setContainerId(-1);
        p2.setCreationDate(new DateTime());
        p2.setShelfLife(2);
        p2.setSize(new Unit(2, "oz"));
        p2.setStorageUnitId(-1);
        p2.validate();
        p2.save();

        item1 = new Item();
        item1.setBarcode(new Barcode(1));
        item1.setProductId(p1.getId());
        item1.setEntryDate(new DateTime());
        item1.setExitDate(new DateTime());
        item1.setExpirationDate(new DateTime());
        item1.validate();
        item1.save();

        item2 = new Item();
        item2.setBarcode(new Barcode(2));
        item2.setProductId(p1.getId());
        item2.setEntryDate(new DateTime());
        item2.setExitDate(new DateTime());
        item2.setExpirationDate(new DateTime());
        item2.validate();
        item2.save();

        item3 = new Item();
        item3.setBarcode(new Barcode(3));
        item3.setProductId(p2.getId());
        item3.setEntryDate(new DateTime());
        item3.setExitDate(new DateTime());
        item3.setExpirationDate(new DateTime());
        item3.validate();
        item3.save();

        item4 = new Item();
        item4.setBarcode(new Barcode(4));
        item4.setProductId(p2.getId());
        item4.setEntryDate(new DateTime());
        item4.setExitDate(new DateTime());
        item4.setExpirationDate(new DateTime());
        item4.validate();
        item4.save();
    }

    @After
    public void tearDown() throws Exception {
        ItemVault.clear();
        ProductVault.clear();
    }

    @Test
    public void testFind() throws Exception {
        assertEquals("Find by product id", item1.getId(), ItemVault.find("ProductId = "+p1.getId()).getId());
        assertEquals("Find by entry date", item2.getId(), ItemVault.find("EntryDate = "+item2.getEntryDate()).getId());
        assertEquals("Find by exit date", item3.getId(), ItemVault.find("ExitDate = "+item3.getExitDate()).getId());
        assertEquals("Find by expiration date", item4.getId(), ItemVault.find("ExpirationDate = "+item4.getExpirationDate()).getId());
    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSize() throws Exception {

    }

    @Test
    public void testValidateNew() throws Exception {

    }

    @Test
    public void testValidateModified() throws Exception {

    }

    @Test
    public void testSaveNew() throws Exception {

    }

    @Test
    public void testSaveModified() throws Exception {

    }
}
