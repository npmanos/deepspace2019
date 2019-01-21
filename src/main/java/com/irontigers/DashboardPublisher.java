package com.irontigers;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardPublisher {

  // TODO: remove this
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
    SmartDashboard.putString("Dashboard Data", state);
    SmartDashboard.updateValues();
  }

  /**
   * Publish all values we want displayed on the Smartdashboard
   */
  private void publishToDashboard(){
    //System.out.println("I am about to print values");

    // Navigation values
    put("Example", "The example value");

    // Actually send the values
    SmartDashboard.updateValues();
  }

  private void put(String key, double value){
    // put a formatted version of the double value
    // for example, will turn 1.2345678 into 1.234
    SmartDashboard.putString(key, df.format(value));
  }
  private void put(String key, String name){
    SmartDashboard.putString(key, name);
  }
  private void put(String key, Boolean state){
    SmartDashboard.putString(key, state.toString());
  }
  public void putR(String key, double value) {
    put(key, value);
    SmartDashboard.updateValues();
  }

}