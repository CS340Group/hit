package model.common;

import model.common.Stats;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.assertEquals;

/** 
* Stats Tester. 
* 
* @author <Authors name> 
* @since <pre>Nov 6, 2012</pre> 
* @version 1.0 
*/ 
public class StatsTest {

/** 
* 
* Method: getMax() 
* 
*/ 
@Test
public void testGetMax() throws Exception {
    Stats stats = new Stats();
    int points = ((Number)(Math.random() * 1000)).intValue();
    int max = 0;
    for(int i = 0; i< points; i++){
        int n = ((Number)(Math.random() * 100000)).intValue();
        stats.insert(n);
        if(n > max)
            max = n;
    }
    assertEquals(max, stats.getMax(), 0.001);
} 

/** 
* 
* Method: getMin() 
* 
*/ 
@Test
public void testGetMin() throws Exception {
    Stats stats = new Stats();
    int points = ((Number)(Math.random() * 1000)).intValue();
    int min = 100000;
    for(int i = 0; i< points; i++){
        int n = ((Number)(Math.random() * 100000)).intValue();
        stats.insert(n);
        if(n < min)
            min = n;
    }
    assertEquals(min, stats.getMin(), 0.001);
} 

/** 
* 
* Method: getMean() 
* 
*/ 
@Test
public void testGetMean() throws Exception {
    Stats stats = new Stats();
    int points = ((Number)(Math.random() * 1000)).intValue();
    int total = 0;
    int mean = 0;
    for(int i = 0; i< points; i++){
        int n = ((Number)(Math.random() * 100000)).intValue();
        stats.insert(n);

        mean++;
    }
    assertEquals(mean, stats.getMean(), 0.001);
} 


} 
