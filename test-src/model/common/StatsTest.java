package model.common;

import model.common.Stats;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;

public class StatsTest { 
    Stats s;
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

/** 
* 
* Method: getMax() 
* 
*/ 
@Test
public void testGetMax() throws Exception { 
    s = new Stats();
    s.insert(1);
    s.insert(2);
    s.insert(3);
    s.insert(4);
    s.insert(5);
    s.insert(6);
    s.insert(7);
    s.insert(8);
    s.insert(9);
    s.insert(10);
    assertEquals(10, s.getMax(), 0);

    s = new Stats();
    assertEquals(0,s.getMax(),0);
}

/** 
* 
* Method: getMin() 
* 
*/ 
@Test
public void testGetMin() throws Exception {
    s = new Stats();
    s.insert(10);
    s.insert(2);
    s.insert(3);
    s.insert(4);
    s.insert(5);
    s.insert(6);
    s.insert(7);
    s.insert(8);
    s.insert(9);
    s.insert(1);
    assertEquals(1, s.getMin(), 0);

    s = new Stats();
    assertEquals(0,s.getMin(),0);
} 

/** 
* 
* Method: getMean() 
* 
*/ 
@Test
public void testGetMean() throws Exception {
    s = new Stats();
    s.insert(1);
    s.insert(2);
    s.insert(3);
    s.insert(4);
    s.insert(5);
    s.insert(6);
    s.insert(7);
    s.insert(8);
    s.insert(9);
    s.insert(10);
    assertEquals(5.5, s.getMean(), 0);

    s = new Stats();
    assertEquals(0,s.getMean(),0);
} 


} 
