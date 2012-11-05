package model.item;

import model.common.Barcode;
import model.common.Size;
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
        p1.setBarcode("94890002844");
        p1.setContainerId(-1);
        p1.setCreationDate(new DateTime(2012,6,1,12,0));
        p1.setShelfLife(2);
        p1.setSize(new Size(3, "oz"));
        p1.setStorageUnitId(-1);
        p1.validate();
        p1.save();

        p2 = new Product();
        p2.setDescription("MyProduct2");
        p2.set3MonthSupply(2);
        p2.setBarcode("76063005135");
        p2.setContainerId(-1);
        p2.setCreationDate(new DateTime(2012,6,2,12,0));
        p2.setShelfLife(2);
        p2.setSize(new Size(2, "oz"));
        p2.setStorageUnitId(-1);
        p2.validate();
        p2.save();

        item1 = new Item();
        item1.setBarcode(new Barcode("18312201052"));
        item1.setProductId(p1.getId());
        item1.setEntryDate(new DateTime(2012,6,1,12,0));
        item1.setExitDate(DateTime.now());
        item1.validate();
        item1.save();

        item2 = new Item();
        item2.setBarcode(new Barcode("53039901985"));
        item2.setProductId(p1.getId());
        item2.setEntryDate(new DateTime(2012,6,5,12,0));
        item2.setExitDate(DateTime.now());
        item2.validate();
        item2.save();

        item3 = new Item();
        item3.setBarcode(new Barcode("41130359224"));
        item3.setProductId(p2.getId());
        item3.setEntryDate(new DateTime(2012,6,2,12,0));
        item3.setExitDate(DateTime.now().minusHours(6));
        item3.validate();
        item3.save();

        item4 = new Item();
        item4.setBarcode(new Barcode("05487033885"));
        item4.setProductId(p2.getId());
        item4.setEntryDate(new DateTime(2012,6,3,12,0));
        item4.setExitDate(DateTime.now().minusHours(4));
        item4.validate();
        item4.save();
    }

    @After
    public void tearDown() throws Exception {
        ItemVault.getInstance().clear();
    }

    @Test
    public void testFind() throws Exception {
        assertEquals("Find by product id", item1.getId(), ItemVault.getInstance().find("ProductId = %o", p1.getId()).getId());
        assertEquals("Find by entry date", item2.getId(), ItemVault.getInstance().find("EntryDate = %o", item2.getEntryDate()).getId());
        assertEquals("Find by exit date", item3.getId(), ItemVault.getInstance().find("ExitDate = %o", item3.getExitDate()).getId());
        assertEquals("Find by expiration date", item4.getId(), ItemVault.getInstance().find("ExpirationDate = %o", item4.getExpirationDate()).getId());
        assertEquals("Find by product barcode", item1.getId(), ItemVault.getInstance().find("ProductBarcode = %o", p1.getBarcode().toString()).getId());
        assertEquals("Find by product description", item3.getId(), ItemVault.getInstance().find("ProductDescription = %o", p2.getDescription()).getId());
        assertEquals("Find by product storage unit id", item1.getId(), ItemVault.getInstance().find("ProductStorageUnitId = %o", p1.getStorageUnitId()).getId());
    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        assertEquals("Ids match", item1.getId(), ItemVault.getInstance().get(item1.getId()).getId());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals("Size is correct", 4, ItemVault.getInstance().size());
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
