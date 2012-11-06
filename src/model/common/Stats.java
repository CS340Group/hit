package model.common;

import java.text.DecimalFormat;
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

    DecimalFormat format;
    public Stats () {
        data = new ArrayList<Number>();
        format = new DecimalFormat("#.##");
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
        return Double.valueOf(format.format(max));
    }

    public double getMin(){
        if(data.isEmpty())
            return 0;
        double min = data.get(0).doubleValue();
        for(Number n : data){
            if(n.doubleValue() < min)
                min = n.doubleValue();
        }
        return Double.valueOf(format.format(min));
    }

    public double getMean(){
        if(data.isEmpty())
            return 0;
        double mean = 0;
        for(Number n : data){
            mean += n.doubleValue();
        }
        return Double.valueOf(format.format(mean/data.size()));
    }


}
