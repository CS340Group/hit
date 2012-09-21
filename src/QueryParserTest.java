

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import model.item.Item;
import model.item.ItemVault;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void test() {
		System.out.println("heyu");
		ItemVault vault = new ItemVault();
		try {
			Item model = vault.find("Product.item = 0");
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
