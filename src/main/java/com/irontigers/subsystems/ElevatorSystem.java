package com.irontigers.subsystems;

import java.time.Duration;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
public class ElevatorSystem extends PeriodicSystem {

  private static ElevatorSystem instance = new ElevatorSystem();
  public static ElevatorSystem instance(){
    return instance;
  }

  private WPI_TalonSRX elevatorTalon;

  private ElevatorSystem(){
    super(Duration.ofMillis(5));
    elevatorTalon = new WPI_TalonSRX(RobotMap.Manipulators.ELEVATOR);
    start();

    clearEncoderPosition();
  }

  @Override
  protected void execute() {
    DashboardPublisher.instance().put("Elevator Position", elevatorTalon.getSelectedSensorPosition());
  }

  public void elevate(double elevateSpeed){

    DashboardPublisher.instance().put("Elevator Speed", elevateSpeed);

    elevatorTalon.set(elevateSpeed);
  }

  public void clearEncoderPosition(){
    try{
      elevatorTalon.setSelectedSensorPosition(0);
      Thread.sleep(10);
      elevatorTalon.setSelectedSensorPosition(0);
    }
    catch(Throwable e){
      
    }
  }

  /**
   * Complete stop driving
   */
  public void stop(){
    elevate(0);
  }

  @Override
  protected void initDefaultCommand() {
    // nothing
  }

}
