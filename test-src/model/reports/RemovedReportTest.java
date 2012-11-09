package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroupVault;
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
public class RemovedReportTest {
	static ObjectReportBuilder builder;
	ItemVault iv = ItemVault.getInstance();
	ProductVault pv = ProductVault.getInstance();
	ProductGroupVault pgv = ProductGroupVault.getInstance();
	StorageUnitVault suv = StorageUnitVault.getInstance();
	static IPrintObject object;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ItemVault.getInstance().clear();
		ProductVault.getInstance().clear();
		StorageUnitVault.getInstance().clear();
		ProductGroupVault.getInstance().clear();
		
		builder = new ObjectReportBuilder();
		Product product = new Product();
		product.generateTestData();
		Result result = product.save();
		for (int i = 0; i < 25; i++) {
			Item item = new Item();
			item.generateTestData();
			item.setProduct(product);
			result = item.save();
			item.delete();
		}
		
		RemovedItemsReport report = new RemovedItemsReport(new DateTime().minusDays(2));
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
	public void testTableHeader() {
		assertTrue("Table header is wrong.",object.getTable(0).getCell(0, 0).equals("Description"));
		assertTrue("Table header is wrong.",object.getTable(0).getCell(0, 1).equals("Size"));
		assertTrue("Table header is wrong.",object.getTable(0).getCell(0, 2).equals("Product Barcode"));
		assertTrue("Table header is wrong.",object.getTable(0).getCell(0, 3).equals("Removed"));
		assertTrue("Table header is wrong.",object.getTable(0).getCell(0, 4).equals("Current Supply"));
	}
	@Test
	public void testRow() {
		assertTrue("Items removed should be "+"25"+" instead its "+object.getTable(0).getCell(1, 3)
				,object.getTable(0).getCell(1, 3).equals("25"));
		assertTrue("Current supply should be "+"0"+" instead its "+object.getTable(0).getCell(1, 4)
				,object.getTable(0).getCell(1, 4).equals("0"));
	}
	
	
}
