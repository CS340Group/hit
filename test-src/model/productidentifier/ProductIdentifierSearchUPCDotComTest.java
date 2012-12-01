package model.productidentifier;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.tools.java.Identifier;

public class ProductIdentifierSearchUPCDotComTest {
	
	ProductIdentifierSearchUPCDotCom _identifier;

	@Before
	public void setUp() throws Exception {
		_identifier = new ProductIdentifierSearchUPCDotCom(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String result = _identifier.identify("0783250622009");
		assertTrue(result, result != "");
	}

}
