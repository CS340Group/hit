package model.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/5/12
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Stats {
    List<Number> data;
    public Stats () {
        data = new ArrayList<Number>();
    }

    public void insert(Number n){
        data.add(n.doubleValue());
    }

    public double getMax(){
        if(data.isEmpty())
            return 0;
        double max = data.get(0).doubleValue();
        for(Number n : data){
            if(n.doubleValue() > max)
                max = n.doubleValue();
        }
        return max;
    }

    public double getMin(){
        if(data.isEmpty())
            return 0;
        double min = data.get(0).doubleValue();
        for(Number n : data){
            if(n.doubleValue() < min)
                min = n.doubleValue();
        }
        return min;
    }

    public double getMean(){
        if(data.isEmpty())
            return 0;
        double mean = 0;
        for(Number n : data){
            mean += n.doubleValue();
        }
        return mean/data.size();
    }


}
