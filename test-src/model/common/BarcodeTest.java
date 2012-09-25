package model.common;

import common.Result;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

/**
 * Created with IntelliJ IDEA.
 * User: murphyra
 * Date: 9/24/12
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeTest {
	Barcode foo;
	Result r;

    @Before
    public void setUp() throws Exception {
        foo = new Barcode();
    }

	@Test
	public void testSetCode() throws Exception {
		Result r = foo.setCode("123123123123"); // Should fail due to check bit.
		Assert.assertFalse(r.getMessage(), r.getStatus());
		r = foo.setCode("0360002914524356"); // Should fail due to length.
		Assert.assertFalse(r.getMessage(), r.getStatus());
		r = foo.setCode("asdf"); // Should fail due to letters.
		Assert.assertFalse(r.getMessage(), r.getStatus());
		r = foo.setCode("036000291452"); // Should succeed.
		Assert.assertTrue(r.getMessage(), r.getStatus());
	}

	@Test
	public void testsetCodeFromId() throws Exception {
		r = foo.setCodeFromId("14252512312312412312313412");
		Assert.assertFalse(r.getMessage(), r.getStatus());
		r = foo.setCodeFromId("asdf");
		Assert.assertFalse(r.getMessage(), r.getStatus());
		r = foo.setCodeFromId("1425");
		Assert.assertTrue(r.getMessage(), r.getStatus());
	}

	@Test
	public void testIsSet() throws Exception {
		Assert.assertFalse(foo.isSet());
		foo.setCodeFromId("123");
		Assert.assertTrue(foo.isSet());
	}	
}
