package common.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void test() {
		System.out.println("heyu");
		ItemVault vault = new ItemVault();
		Item item = new Item();
		Product prod = new Product();
		prod.setName("john");
		item.setName("brendon");
		item.setProduct(prod);
		vault.add(item);
		try {
			Item model = vault.find("Name = john");
		} catch (IllegalArgumentException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
