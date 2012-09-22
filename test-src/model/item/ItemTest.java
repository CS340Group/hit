package model.item;

import model.common.Barcode;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {

    public static Item item;
    @BeforeClass
    public static void setup(){
        item = new Item();
    }

    @Test
    public void testItemCreation(){
        item.setBarcode(new Barcode(111));
        item.setProductId(-1);
        item.setEntryDate(new DateTime());
        item.setExitDate(new DateTime());
        item.setExpirationDate(new DateTime());
        assertEquals("Id should be -1", -1, item.getId());
        assertEquals("Item is not saved", false, item.isSaved());
        assertEquals("Item is not valid", false, item.isValid());
        assertEquals("Item should be saveable because its not valid",
                false, item.save().getStatus());
        assertEquals("Item should pass validation", true, item.validate().getStatus());
        assertEquals("Item should save", true, item.save().getStatus());
        assertEquals("Id should be 0", 0, item.getId());
        assertEquals("Item is saved", true, item.isSaved());
        assertEquals("Item is Valid", true, item.isValid());
    }
}
