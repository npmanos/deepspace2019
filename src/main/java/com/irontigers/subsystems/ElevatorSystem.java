package com.irontigers.subsystems;

import java.time.Duration;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.PeriodicExecutor;
import com.irontigers.RobotMap;
import com.irontigers.commands.ElevatorManualControl;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
public class ElevatorSystem extends Subsystem {

  private static ElevatorSystem instance = new ElevatorSystem();
  public static ElevatorSystem instance(){
    return instance;
  }

  private WPI_TalonSRX elevatorTalon;
  private double offSet = RobotMap.Elevator.OFFSET;

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("elevator_position", Duration.ofMillis(5), () -> {
    DashboardPublisher.instance().putDebug("Elevator Encoder Position", getRawPosition());
  });

  private ElevatorSystem(){
    elevatorTalon = new WPI_TalonSRX(RobotMap.Manipulators.ELEVATOR);

    periodicExecutor.start();
  }

  public int getRawPosition(){
    return elevatorTalon.getSelectedSensorPosition();
  }

  public void move(double speed){
    elevatorTalon.set(speed);
    if(Math.abs(getRawPosition()) > 10000) { 
      DumpTruckSystem.instance().dump();
    }
    if(wrongWay()){
      DashboardPublisher.instance().putDriver("Elevator Level", "WARNING: BELOW BOTTOM");
    }else if(isLowerLimitSwitch()){
      DashboardPublisher.instance().putDriver("Elevator Level", "Bottom");
    }else if(atLevel(RobotMap.Elevator.LEVEL_1, .02)){
      DashboardPublisher.instance().putDriver("Elevator Level", "Level 1");
    }else if(atLevel(RobotMap.Elevator.LEVEL_2, .01)){
      DashboardPublisher.instance().putDriver("Elevator Level", "Level 2");
    }else if(isUpperLimitSwitch()){
      DashboardPublisher.instance().putDriver("Elevator Level", "Top");
    }else{
      DashboardPublisher.instance().putDriver("Elevator Level", "Between Levels");
    }

    if(!isUpperLimitSwitch() && !isLowerLimitSwitch()){
      DashboardPublisher.instance().putDebug("Limit Swtich tripped", "None");
    }

    DashboardPublisher.instance().putDriver("Elevator Height", ((double) getRawPosition() / (double) -RobotMap.Elevator.MAX_HEIGHT) * 100);
  }

  public void zeroEncoder(){
    Shuffleboard.addEventMarker("Encoder zeroed", RobotMap.Dashboard.CRITICAL);
    while(!elevatorTalon.getSensorCollection().isRevLimitSwitchClosed()){
      move(-.5);
    }
    move(0); 

    try{
      elevatorTalon.setSelectedSensorPosition(0);
      Thread.sleep(10);
      elevatorTalon.setSelectedSensorPosition(0);
    }
    catch(Throwable e){
      System.out.println(e);
    }
    
    }
    public Boolean bottomedOut(){
      return elevatorTalon.getSensorCollection().isRevLimitSwitchClosed();
    }
  /**
   * Complete stop driving
   */
  public void stop(){
    move(0);
  }
  public boolean isLowerLimitSwitch() { 
    if(elevatorTalon.getSensorCollection().isRevLimitSwitchClosed()){  
      DashboardPublisher.instance().putDebug("Limit switch tripped", "Bottom limit switch");
      return true;
    }else{
      return false;
    }
  }

  public boolean isUpperLimitSwitch() {
    if(elevatorTalon.getSensorCollection().isFwdLimitSwitchClosed()){  
      DashboardPublisher.instance().putDebug("Limit switch tripped", "Top limit switch");
      return true;
    }else{
      return false;
    }
  }

  public void resetElevator(){
    /* This assumes the bottom limit switch is the reverse limit switch
    *  We need to test this assumption and adjust once wired
    */
    while(elevatorTalon.getSensorCollection().isRevLimitSwitchClosed() == false){
      move(-.5);
    }
    stop();
    zeroEncoder();
  }

  /**
   * @return the offSet
   */
  public double getOffSet() {
    return offSet;
  }

  public Boolean wrongWay(){
    if(getRawPosition() > 1000){
      Shuffleboard.addEventMarker("Elevator underrun",
                                  "The elevator has gone past the lower limit switch and has wound the wrong way.",
                                  RobotMap.Dashboard.CRITICAL);
      return true;
    }else{
      return false;
    }
  }

  public Boolean atLevel(double goalPosition, double leeway){
    double currentPosition = Math.abs(getRawPosition());
    double minPosition = (goalPosition + getOffSet()) * (1 - leeway);
    double maxPosition = (goalPosition + getOffSet()) * (1 + leeway);
    boolean inRange = (currentPosition > minPosition) && (currentPosition < maxPosition);
    return inRange;
  }

  @Override
  protected void initDefaultCommand() {
      setDefaultCommand(new ElevatorManualControl());
    }  }

