package model.common;

import static org.junit.Assert.*;

import model.productcontainer.StorageUnit;

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
		VaultPickler vp = new VaultPickler();
		vp.SerializeMe();
		su = new StorageUnit();
        su.setName("Test2");
        su.validate();
        su.save();
		vp.DeSerializeMe();
		assertTrue(vp.allVaults.storageUnitVault.size() == 1);
		assertTrue(vp.allVaults.storageUnitVault.get(0).getName().equals("Test"));
    }

}
