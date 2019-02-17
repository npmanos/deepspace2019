package com.irontigers.subsystems;

import java.time.Duration;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.PeriodicExecutor;
import com.irontigers.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
public class ElevatorSystem extends Subsystem {

  private static ElevatorSystem instance = new ElevatorSystem();
  public static ElevatorSystem instance(){
    return instance;
  }

  private WPI_TalonSRX elevatorTalon;

  // Write elevator info every 5 milliseconds
  private PeriodicExecutor periodicExecutor = new PeriodicExecutor("elevator_position", Duration.ofMillis(5), () -> {
    DashboardPublisher.instance().put("Elevator Position", getRawPosition());
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
      DumpTruckSystem.instance().unDump();
    }
  }

  public void zeroEncoder(){
    while(!elevatorTalon.getSensorCollection().isRevLimitSwitchClosed()){
      move(-.5);
    }
    move(0); 

    try{
      elevatorTalon.setSelectedSensorPosition(0);
      Thread.sleep(10);
      elevatorTalon.setSelectedSensorPosition(0);
      System.out.println("Zeroed");
    }
    catch(Throwable e){
      System.out.println(e);
    }
    }
  

  /**
   * Complete stop driving
   */
  public void stop(){
    move(0);
  }
  public boolean isLowerLimitSwitch() { 
    return elevatorTalon.getSensorCollection().isRevLimitSwitchClosed();
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

  @Override
  protected void initDefaultCommand() {
    // nothing
  }
}
