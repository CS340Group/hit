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
		StorageUnit su2;
		VaultPickler vp = new VaultPickler();
		vp.SerializeMe();
		su2 = new StorageUnit();
        su2.setName("Test2s");
        su2.validate();
        su2.save();
        assertTrue("The vault never updated after serializing", vp.allVaults.storageUnitVault.size()==2);
        vp.DeSerializeMe();
        assertTrue("The vault didn't load again from file",vp.allVaults.storageUnitVault.size()==1);
    }

}
