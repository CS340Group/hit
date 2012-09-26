/**
 * 
 */
package model.product;

import static org.junit.Assert.*;

import model.common.Barcode;
import model.common.Size;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author beebe
 *
 */
public class ProductVaultTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Create a storage Unit with a empty product group
		StorageUnit su = new StorageUnit();
		su.setName("Test");
        su.validate();
        su.save();

        ProductGroup pg = new ProductGroup();
        pg.setName("Group1");
        pg.setParentId(0);
        pg.validate();
        pg.save();
        
        ProductGroup pg2 = new ProductGroup();
        pg2.setName("Group2");
        pg2.setParentId(0);
        pg2.validate();
        pg2.save();
        
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	/*
     * This tests that "At Most one product container 
     * in a Storage Unit may contain a particular Product"
     */
    @Test
    public void testOneProductPer(){
    	//Create one product, put it in the root container
    	Product p = new Product();
    	p.setToBlankProduct();
    	p.setStorageUnitId(0);
    	p.setContainerId(0);
    	p.validate();
    	p.save();
    	
    	//Create another product with the same barcode
    	//put it in the sub group
    	Product p2 = new Product();
    	p2.setToBlankProduct();
    	p2.setStorageUnitId(0);
    	p2.setContainerId(1);
    	p2.validate();
    	assertFalse(p2.save().getStatus());
        
        
    }
    /*
     * This tests that the creation date is 
     * not different from the first product put in the list
     */
    @Test
    public void testCreationDate(){
    	//Create one product, set the creation date
    	Product p = new Product();
    	p.setToBlankProduct();
    	p.setStorageUnitId(0);
    	p.setContainerId(0);
    	p.setCreationDate(new DateTime(2012,1,1,1,1));
    	p.validate();
    	p.save();
    	
    	//Create the same product but change the creation date
    	Product p2 = new Product();
    	p2.setToBlankProduct();
    	p2.setStorageUnitId(0);
    	p2.setContainerId(1);
    	p2.setCreationDate(new DateTime(2015,1,1,1,1));
    	p2.validate();
    	assertFalse("The Creation date is invalid, it's not" +
    			"= to the oldest product. ",p2.save().getStatus());     	
    }
}
