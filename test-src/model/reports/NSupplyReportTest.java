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
public class NSupplyReportTest {
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
			productGroup.set3MonthSupply(new Size(10000,"oz"));
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
		
		NSupplyReport report = new NSupplyReport(1);
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
	public void testSecondTableHeader() {
		assertTrue("Table header is wrong.",object.getTable(1).getCell(0, 0).equals("Product Group"));
		assertTrue("Table header is wrong.",object.getTable(1).getCell(0, 1).equals("Storage Unit"));
		assertTrue("Table header is wrong.",object.getTable(1).getCell(0, 2).equals("1-Month Supply"));
		assertTrue("Table header is wrong.",object.getTable(1).getCell(0, 3).equals("Current Supply"));
	}
	@Test
	public void testFirstRowSecondTable() {
		assertTrue("1-Month Supply should be "+"3333"+" instead its "+object.getTable(0).getCell(1, 2)
				,object.getTable(1).getCell(1, 2).equals("3333"));
		assertTrue("Current supply should be "+"25.0"+" instead its "+object.getTable(0).getCell(1, 3)
				,object.getTable(1).getCell(1, 3).equals("25.0"));
	}
	
	@Test
	public void testMultipleWeights(){
	}
	}
	
	
}
