package com.irontigers.subsystems;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardPublisher extends PeriodicSystem {

  private static DashboardPublisher instance = new DashboardPublisher();
  public static DashboardPublisher instance(){
    return instance;
  }

  private Map<String, String> values = new HashMap<String, String>();
  private static final DecimalFormat df = new DecimalFormat("#.###");

  public DashboardPublisher(){
    super(Duration.ofMillis(50));
    values.clear();

    start();
  }

  @Override
  public void start(){
    super.start();

    publishState(String.format("Every %dms", periodDuration.toMillis()));
  }

  public void stop(){
    super.stop();

    publishState("Not Publishing");
  }

  private void publishState(String state){
    //consider having publishState put the current state into the map instead of puString-ing it
    SmartDashboard.putString("Dashboard Publisher", state);
    SmartDashboard.updateValues();
  }
  
  @Override
  protected void execute() {
    publishToDashboard();
  }

  private void publishToDashboard(){
    for (String key:values.keySet()){
      SmartDashboard.putString(key, values.get(key));
    }

    // Actually send the values
    SmartDashboard.updateValues();
  }

  public void put(String key, double value){
    values.put(key, df.format(value));
  }
  public void put(String key, String value){
    values.put(key, value);
  }
  public void put(String key, Boolean state){
    values.put(key, state.toString());
  }
  
  @Override
  protected void initDefaultCommand() {
    // nothing
  }
}