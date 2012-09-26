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
<<<<<<< HEAD
		StorageUnit su2;
		VaultPickler vp = new VaultPickler();
		vp.SerializeMe();
		su2 = new StorageUnit();
        su2.setName("Test2s");
        su2.validate();
        su2.save();
        assertTrue("", vp.allVaults.storageUnitVault.size()==2);
        vp.DeSerializeMe();
        assertTrue("",vp.allVaults.storageUnitVault.size()==1);
=======
		VaultPickler vp = new VaultPickler();
		vp.SerializeMe();
		su = new StorageUnit();
        su.setName("Test2");
        su.validate();
        su.save();
		vp.DeSerializeMe();
		assertTrue(vp.allVaults.storageUnitVault.size() == 1);
		assertTrue(vp.allVaults.storageUnitVault.get(0).getName().equals("Test"));
>>>>>>> 83dc0c8c1b13af4a79f382803d2c2c10fa7da8c6
    }

}
