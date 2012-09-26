package model.common;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.product.Product;
import model.productcontainer.StorageUnit;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SerializableTest {
	private static StorageUnit su;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		su = new StorageUnit();
        su.setName("Test");
        su.validate();
        su.save();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
    public void SerialTest(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("D:\\_Programs\\xampp\\htdocs\\github\\hit\\src\\employee.ser");
	         ObjectOutputStream out =
	                            new ObjectOutputStream(fileOut);
	         out.writeObject(su);
	         out.close();
	          fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
    }

}
