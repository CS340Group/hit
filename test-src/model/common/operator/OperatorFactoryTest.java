package model.common.operator;

import model.common.operator.Operator;
import model.common.operator.OperatorFactory;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.joda.time.DateTime;

import static model.common.operator.OperatorFactory.getOperator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/** 
* OperatorFactory Tester. 
* 
* @author <Nick Ethier>
* @since <pre>Nov 3, 2012</pre> 
* @version 1.0 
*/ 
public class OperatorFactoryTest { 


    @Test
    public void testEqualToStringOperator() throws Exception {
        String string1 = "hello";
        String string2 = "world";
        String string3 = "hello ";

        Operator op = getOperator("=", string1.getClass());
        assertTrue(op.execute(string1,string1));
        assertFalse(op.execute(string1,string2));
        assertFalse(op.execute(string1,string3));

    }

    @Test
    public void testNotEqualToStringOperator() throws Exception {
        String string1 = "hello";
        String string2 = "world";
        String string3 = "hello ";

        Operator op = getOperator("!=", string1.getClass());
        assertFalse(op.execute(string1, string1));
        assertTrue(op.execute(string1, string2));
        assertTrue(op.execute(string1, string3));
    }

    @Test
    public void testEqualToDateTimeOperator() throws Exception {
        DateTime time1 = DateTime.now();
        DateTime time2 = time1.minusSeconds(1);

        Operator op = getOperator("=", time1.getClass());
        assertTrue(op.execute(time1,time1));
        assertFalse(op.execute(time1, time2));
    }

    @Test
    public void testLessThanDateTimeOperator() throws Exception {
        DateTime time1 = DateTime.now();
        DateTime time2 = time1.minusSeconds(1);

        Operator op = getOperator("<", time1.getClass());
        assertTrue(op.execute(time2,time1));
        assertFalse(op.execute(time1, time1));
    }

    @Test
    public void testGreaterThanDateTimeOperator() throws Exception {
        DateTime time1 = DateTime.now();
        DateTime time2 = time1.minusSeconds(1);

        Operator op = getOperator(">", time1.getClass());
        assertTrue(op.execute(time1,time2));
        assertFalse(op.execute(time1, time1));
    }

    @Test
    public void testEqualToNumberOperator() throws Exception {
        int i = 1;
        float f = 1;
        double d = 1.01;

        Operator op1 = getOperator("=", ((Integer) i).getClass());
        Operator op2 = getOperator("=", ((Double)d).getClass());

        assertTrue(op1.execute(i,f));
        assertFalse(op1.execute(i, d));
        assertFalse(op1.execute(f, d));

        assertTrue(op2.execute(i, f));
        assertFalse(op2.execute(i,d));
        assertFalse(op2.execute(f,d));
    }

    @Test
    public void testNotEqualToNumberOperator() throws Exception {
        int i = 1;
        float f = 1;
        double d = 1.01;

        Operator op1 = getOperator("!=", ((Integer)i).getClass());
        Operator op2 = getOperator("!=", ((Double)d).getClass());

        assertFalse(op1.execute(i,f));
        assertTrue(op1.execute(i,d));
        assertTrue(op1.execute(f,d));

        assertFalse(op2.execute(i,f));
        assertTrue(op2.execute(i,d));
        assertTrue(op2.execute(f,d));
    }

    @Test
    public void testLessThanNumberOperator() throws Exception {
        int i = 1;
        float f = 1;
        double d = 1.01;

        Operator op = getOperator("<", ((Object)i).getClass());

        assertFalse(op.execute(i,f));
        assertFalse(op.execute(f,i));
        assertTrue(op.execute(i, d));
        assertTrue(op.execute(f, d));
        assertFalse(op.execute(d, i));
        assertFalse(op.execute(d,f));
    }

    @Test
    public void testGreaterThanNumberOperator() throws Exception {
        int i = 1;
        float f = 1;
        double d = 1.01;

        Operator op = getOperator(">", ((Object)i).getClass());

        assertFalse(op.execute(i, f));
        assertFalse(op.execute(f, i));
        assertFalse(op.execute(i, d));
        assertFalse(op.execute(f, d));
        assertTrue(op.execute(d, i));
        assertTrue(op.execute(d, f));
    }

    @Test
    public void testEqualToBooleanOperator() throws Exception {
        Operator op = getOperator("=", ((Object)false).getClass());

        assertTrue(op.execute(false, false));
        assertTrue(op.execute(true, true));
        assertFalse(op.execute(false,true));
    }

    @Test
    public void testNotEqualToBooleanOperator() throws Exception {
        Operator op = getOperator("!=", ((Object)false).getClass());

        assertFalse(op.execute(false, false));
        assertFalse(op.execute(true, true));
        assertTrue(op.execute(false,true));
    }

    @Test
    public void testNullOperator() throws Exception {
        Operator op = getOperator("=", Object.class);
        assertNull(op);
    }



} 
