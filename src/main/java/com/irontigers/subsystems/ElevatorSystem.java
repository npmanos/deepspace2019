package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
public class ElevatorSystem extends Subsystem {

  private static ElevatorSystem instance = new ElevatorSystem();
  public static ElevatorSystem instance(){
    return instance;
  }

  private ElevatorSystem(){
    elevatorTalon = new WPI_TalonSRX(RobotMap.Manipulators.ELEVATOR);
  }

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command

    //setDefaultCommand(new TeleopDrive());
  }


  public void elevate(double elevateSpeed){

    DashboardPublisher.instance().put("Elevator Speed", elevateSpeed);

    elevatorTalon.set(elevateSpeed);
  }

  /**
   * Complete stop driving
   */
  public void stop(){
    elevate(0);
  }

}
