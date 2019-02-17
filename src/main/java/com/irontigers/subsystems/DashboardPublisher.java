package com.irontigers.subsystems;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.irontigers.PeriodicExecutor;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardPublisher extends Subsystem {

  private static DashboardPublisher instance = new DashboardPublisher();
  public static DashboardPublisher instance(){
    return instance;
  }

  private Map<String, String> values = new HashMap<String, String>();
  private static final DecimalFormat df = new DecimalFormat("#.###");

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("dashboard_publisher", Duration.ofMillis(50), () -> {
    publishToDashboard();
  });

  public DashboardPublisher(){
    values.clear();

    periodicExecutor.start();
  }

  public void stop(){
    
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