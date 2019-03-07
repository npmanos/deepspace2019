package com.irontigers.subsystems;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.irontigers.PeriodicExecutor;
import com.irontigers.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardPublisher extends Subsystem {

  private static DashboardPublisher instance = new DashboardPublisher();
  public static DashboardPublisher instance(){
    return instance;
  }

  private Map<String, String> stringValues = new HashMap<String, String>();
  private Map<String, Double> numberValues = new HashMap<String, Double>();
  private Map<String, Boolean> booleanValues = new HashMap<String, Boolean>();

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("dashboard_publisher", Duration.ofMillis(50), () -> {
    publishToDashboard();
  });

  public DashboardPublisher(){
    stringValues.clear();
    numberValues.clear();
    booleanValues.clear();

    periodicExecutor.start();
  }

  public void stop(){
    
  }

  private void publishToDashboard(){
    for (String key:stringValues.keySet()){
      SmartDashboard.putString(key, stringValues.get(key));
    }

    for (String key:numberValues.keySet()){
      SmartDashboard.putNumber(key, numberValues.get(key));
    }

    for (String key:booleanValues.keySet()){
      SmartDashboard.putBoolean(key, booleanValues.get(key));
    }

    // Actually send the values
    SmartDashboard.updateValues();
  }

  public void put(String key, double value){
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd.setScale(3, RoundingMode.HALF_UP);

    numberValues.put(key, bd.doubleValue());
  }
  public void put(String key, String value){
    stringValues.put(key, value);
  }
  public void put(String key, Boolean state){
    booleanValues.put(key, state);
  }
  
  public void putDebug(String key, double value){
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd.setScale(3, RoundingMode.HALF_UP);

    numberValues.put(RobotMap.Dashboard.DEBUG_PREFIX + key, bd.doubleValue());
  }
  public void putDebug(String key, String value){
    stringValues.put(RobotMap.Dashboard.DEBUG_PREFIX + key, value);
  }
  public void putDebug(String key, Boolean state){
    booleanValues.put(RobotMap.Dashboard.DEBUG_PREFIX + key, state);
  }
  
  public void putDriver(String key, double value){
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd.setScale(3, RoundingMode.HALF_UP);

    numberValues.put(RobotMap.Dashboard.DRIVER_PREFIX + key, bd.doubleValue());
  }
  public void putDriver(String key, String value){
    stringValues.put(RobotMap.Dashboard.DRIVER_PREFIX + key, value);
  }
  public void putDriver(String key, Boolean state){
    booleanValues.put(RobotMap.Dashboard.DRIVER_PREFIX + key, state);
  }

  @Override
  protected void initDefaultCommand() {
    // nothing
  }
}