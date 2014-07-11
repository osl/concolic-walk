package concolic;

import gov.nasa.jpf.symbc.Concrete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * This class serves as a way to calculate the median of a list of values.  It
 * is not threadsafe.
 */
public class StatCalculator implements Serializable
{
    static List values = new ArrayList();
    static double sum = 0;
    static double sumOfSquares = 0;
    static double mean = 0;
    static double deviation = 0;
    static int count = 0;
    
    public static void clear()
    {
        values.clear();
        sum = 0;
        sumOfSquares = 0;
        mean = 0;
        deviation = 0;
        count = 0;
    }

    public static void addValue(long newValue)
    {
        Number val = new Long(newValue);
        addValue(val);
    }

    public static void addValue(int newValue)
    {
    	Number val = new Integer(newValue);
        addValue(val);
        
    }

    public static void addValue(float newValue)
    {
        Number val = new Float(newValue);
        addValue(val);
    }

    public static void addValue(double newValue)
    {
        Number val = new Double(newValue);
        addValue(val);
    }

    public static Number getMedian()
    {
        return (Number) values.get(values.size() / 2);
    }
    
    public static double getMean()
    {
        return mean;
    }
    
    public static double getStandardDeviation()
    {
        return deviation;
    }
    
    public static Number getMin()
    {
        return (Number)values.get(0);
    }
    
    public static Number getMax()
    {
        return (Number)values.get(count-1);
    }
    
    public static int getCount()
    {
        return count;
    }

    public static void addValue(Number val)
    {
    //	if((double) val.intValue() > 10) {
    //		System.out.println("10");
    //	}
        int index = Collections.binarySearch(values, val);
        if (index >= 0 && index < values.size())
        {
            values.add(index, val);
        }
        else if (index == values.size() || values.size() == 0)
        {
            values.add(val);
        }
        else
        {;
            values.add((index * (-1)) - 1, val);
        }
        count++;
        System.out.println("stat ");
        double currentVal = val.doubleValue();
        sum += currentVal;
        sumOfSquares += currentVal * currentVal;
        mean = sum / count;
        deviation = Math.sqrt( (sumOfSquares / count) - (mean * mean) );
    }
    
}
