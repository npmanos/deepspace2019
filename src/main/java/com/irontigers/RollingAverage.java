package com.irontigers;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * RollingAverage will calculate an average value over a rolling set of values. It calculates
 * the average from a set of raw values no larger than {@link RollingAverage#windowSize}.
 * Calling {@link RollingAverage#addValue(BigDecimal)}, {@link RollingAverage#addValue(double))},
 * or {@link RollingAverage#addValue(int))} will add that value to the set of values and remove
 * remove the oldest value in the list if necessary to keep the value list size no larger than
 * the {@link RollingAverage#windowSize}.
 *
 * The current average can be retrieved by calling {@link RollingAverage#getAverage()}, which will
 * calculate the average for all values currently in the list even if the list is smaller than the
 * set {@link RollingAverage#windowSize}.
 *
 * Instances are thread-safe and can be modified and read from different threads.
 */
public class RollingAverage {

  private int windowSize;
  private LinkedList<BigDecimal> values;
  private double currentAverage;

  public RollingAverage(int windowSize){
    this.windowSize = windowSize;
    values = new LinkedList<>();
  }

  public void addValue(int value){
    addValue(new BigDecimal(value));
  }

  public void addValue(double value){
    addValue(new BigDecimal(value));
  }

  public synchronized void addValue(BigDecimal value){
    // Add to start of list
    values.addFirst(value);

    // if we've made the list longer than the rolling size then remove the last item
    if(values.size() > windowSize){
      values.removeLast();
    }

    currentAverage = values.stream().collect(Collectors.averagingDouble(BigDecimal::doubleValue));
  }

  public boolean isFull(){
    return values.size() >= windowSize;
  }

  public double getAverage(){
    return currentAverage;
  }

}
