package model.productcontainer;

import common.Result;
import model.productcontainer.ProductContainer;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: murphyra
 * Date: 9/21/12
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductContainerTest {

    ProductContainer foo;
    Result res;

    @Before
    public void setUp() throws Exception {
        foo = new ProductContainer();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValidate() throws Exception {
        Assert.assertFalse(foo.validate().getStatus());
        foo.setName("bar");
        Assert.assertTrue(foo.validate().getStatus());
    }

    @Test
    public void testSave() throws Exception {
        res = foo.save();
        Assert.assertFalse(foo.save().getStatus());
        foo.setName("bar");
        foo.validate();
        Assert.assertTrue(foo.save().getStatus());
        foo.setName("foo");
        Assert.assertFalse(foo.save().getStatus());
        Assert.assertTrue(foo.validate().getMessage(), foo.validate().getStatus());
    }
}
