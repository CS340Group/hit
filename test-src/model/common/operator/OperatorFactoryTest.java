package model.common.operator;

import model.common.operator.Operator;
import model.common.operator.OperatorFactory;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static model.common.operator.OperatorFactory.getOperator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** 
* OperatorFactory Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 3, 2012</pre> 
* @version 1.0 
*/ 
public class OperatorFactoryTest { 


    @Test
    public void testEqualToStringOperator() throws Exception {
        String string1 = "hello";
        String string2 = "world";

        Operator op = getOperator("=", string1.getClass());
        assertTrue(op.execute(string1,string1));

    }

    @Test
    public void testNotEqualToStringOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testEqualToDateTimeOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testLessThanDateTimeOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testGreaterThanDateTimeOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testEqualToNumberOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testNotEqualToNumberOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testLessThanNumberOperator() throws Exception {
        //TODO: Test goes here...
    }

    @Test
    public void testGreaterThanNumberOperator() throws Exception {
        //TODO: Test goes here...
    }




} 
