package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;
import edu.wpi.first.wpilibj.Spark;

public class HatchManipSystem extends Subsystem {

 private PlaceMethod{
  }

//   private static HatchManipSystem instance = new PlaceMethod();
  public static Spark hatchSpark = new Spark(RobotMap.HatchManip.Spark);
 
  public static HatchManipSystem instance(){
    return instance;
  }

  // Drive and controllers

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command
  }

  public void extend(){
      hatchSpark.setSpeed(.5);
      publisher(true);
  }

  public void retract(){
      hatchSpark.setSpeed(-.5);
      publisher(false);

  private void publisher(boolean extended){
      DashboardPublisher.instance().put("Demigorgon Extended?", extended);
  }
}