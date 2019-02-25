package com.irontigers.subsystems;

import com.irontigers.RobotMap;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchManipSystem extends Subsystem {

  private static HatchManipSystem instance = new HatchManipSystem();
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
      hatchSpark.setSpeed(.75);
      publisher(true);
  }

  public void retract(){
      hatchSpark.setSpeed(-.75);
      publisher(false);
  }
  private void publisher(boolean extended){
      DashboardPublisher.instance().putDriver("Hatch Manipulator", extended);
  }
}