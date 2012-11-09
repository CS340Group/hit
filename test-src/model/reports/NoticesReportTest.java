package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.common.Size;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
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
	ProductVault pv = ProductVault.getInstance();
	ProductGroupVault pgv = ProductGroupVault.getInstance();
	StorageUnitVault suv = StorageUnitVault.getInstance();
	
	static IPrintObject object;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		builder = new ObjectReportBuilder();
		StorageUnit storageUnit = new StorageUnit();
		storageUnit.generateTestData();
		ProductGroup productGroup = new ProductGroup();
			productGroup.set3MonthSupply(new Size(10000,"gallon"));
			productGroup.setName("pg1");
			productGroup.setParentId(storageUnit.getId());
			productGroup.validate();
			productGroup.save();
		Product product2 = new Product();
			product2.setContainerId(productGroup.getId());
			product2.generateTestData();
			product2.setBarcode("113");
			product2.setSize(new Size(10000,"pint"));
			product2.validate();
			Result result =product2.save();
		Item item2 = new Item();
			item2.generateTestData();
			item2.setProduct(product2);
			result = item2.save();
			
		Product product = new Product();
		product.setContainerId(productGroup.getId());
		product.generateTestData();
		result = product.save();
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
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ItemVault.getInstance().clear();
		ProductVault.getInstance().clear();
		StorageUnitVault.getInstance().clear();
		ProductGroupVault.getInstance().clear();
		
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
				"(10000.0 gallon) that is inconsistent with the following products:"));
		text = object.getTextBlock(1).toString();
		assertTrue("Text block two is wrong.",text.equals("- pg1::Spam and eggs(size: 3.0 oz)"));
	}

	
	
}
