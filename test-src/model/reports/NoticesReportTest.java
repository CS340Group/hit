package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.common.Size;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Result;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/3/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoticesReportTest {
	static ObjectReportBuilder builder;
	ItemVault iv = ItemVault.getInstance();
	static IPrintObject object;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		builder = new ObjectReportBuilder();
		StorageUnit storageUnit = new StorageUnit();
		storageUnit.generateTestData();
		ProductGroup productGroup = new ProductGroup();
			productGroup.set3MonthSupply(new Size(10000,"count"));
			productGroup.setName("pg1");
			productGroup.setParentId(storageUnit.getId());
			productGroup.validate();
			productGroup.save();
		
		
			
		Product product = new Product();
		product.setContainerId(productGroup.getId());
		product.generateTestData();
		Result result = product.save();
		for (int i = 0; i < 25; i++) {
			Item item = new Item();
			item.generateTestData();
			item.setProduct(product);
			result = item.save();
		}
		
		NoticesReport report = new NoticesReport();
		report.setBuilder(builder);
		report.constructReport();
		object = builder.returnObject();

	}
	
	
	@Before
	public void setUp() throws Exception {
		

	}

	
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testLines() {
		String text = object.getTextBlock(0).toString();
		assertTrue("Text block one is wrong.",text.equals(
				"Product group Test Storage Unit Name::pg1 has a 3-month supply "+
				"(10000.0 count) that is inconsistent with the following products:"));
		text = object.getTextBlock(1).toString();
		assertTrue("Text block two is wrong.",text.equals("- pg1::Spam and eggs(size: 3.0 oz)"));
	}

	
	
}
