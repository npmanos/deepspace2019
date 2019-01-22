package com.irontigers;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardPublisher {

  private Map<String, String> values = new HashMap<String, String>();

  // TODO: make the put methos convert all inputs (booleans, integers, doubles, Strings) into strings, and put them into values
  private static DashboardPublisher instance;
  public static DashboardPublisher instance() {
    return instance;
  }
  
  private ScheduledExecutorService publishingExecutor;
  private Duration timeBetweenPublishes;

  // used to format double values to no more than 3 decimal places
  private static final DecimalFormat df = new DecimalFormat("#.###");

  public DashboardPublisher(Duration timeBetweenPublishes){
    publishingExecutor = Executors.newSingleThreadScheduledExecutor();
    this.timeBetweenPublishes = timeBetweenPublishes;
    this.instance = this;
  }

  public void start(){
    // create a single thread that will call publishToDashboard every time it is executed and
    // schedule the thread to run periodically based on the timeBetweenPublishes period
    // ie. If timeBetweenPublishes is 200ms then the thread will run every 200ms and data will
    //     be published to the dashboard every 200ms
    publishingExecutor.scheduleAtFixedRate(() -> publishToDashboard(), 0, timeBetweenPublishes.toMillis(), TimeUnit.MILLISECONDS);
    publishState(String.format("Publishing every %dms", timeBetweenPublishes.toMillis()));
  }

  public void stop(){
    // stop the periodic publishing of dashboard data
    if(!publishingExecutor.isShutdown()){
      publishingExecutor.shutdownNow();
    }

    publishState("Not publishing");
  }

  private void publishState(String state){
    //consider having publishState put the current state into the map instead of puString-ing it
    SmartDashboard.putString("Dashboard Data", state);
    SmartDashboard.updateValues();
  }

  /**
   * Publish all values we want displayed on the Smartdashboard
   */
  private void publishToDashboard(){
    //System.out.println("I am about to print values");

    for (String key:values.keySet())
    {
      SmartDashboard.putString(key, values.get(key));
    }

    // Actually send the values
    SmartDashboard.updateValues();
  }

  public void put(String key, double value){
    
    values.put(key, df.format(value));
    // put a formatted version of the double value
  }
  public void put(String key, String value){
    values.put(key, value);
  }
  public void put(String key, Boolean state){
    values.put(key, state.toString());
  }
  // public void putR(String key, double value) {
  //   put(key, value);
  //   SmartDashboard.updateValues();
  // }

}