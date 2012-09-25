package common.util;

import org.junit.Ignore;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;

import org.junit.Test;

public class QueryParserTest {

    @Ignore
    public void test() {
        System.out.println("heyu");
        ItemVault vault = new ItemVault();
        Item item = new Item();
        Product prod = new Product();
        prod.setDescription("john");
        prod.save();
        item.setProductId(prod.getId());
        item.save();
        //vault.add(item);
        try {
            Item model = vault.find("Name = john");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fail("Not yet implemented");
    }

}
