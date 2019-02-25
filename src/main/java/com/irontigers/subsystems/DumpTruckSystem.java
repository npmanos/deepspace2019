package com.irontigers.subsystems;

import com.irontigers.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DumpTruckSystem extends Subsystem {


  private static DumpTruckSystem instance = new DumpTruckSystem();
  public static DumpTruckSystem instance(){
    return instance;
  }

  private Spark dumpTruck = new Spark(RobotMap.DumpTruck.Spark);
  private boolean isDumped = false;

  private DumpTruckSystem(){
      //don't forget to put stuff here
  }

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command
  }

  public void invert(){
    if(isDumped){
      unDump();
    }
    else{
      dump();
    }
  }

  public void dump(){   
      dumpTruck.setSpeed(.5);
      isDumped = true;
      DashboardPublisher.instance().putDriver("Dumped", isDumped);
  }

  public void unDump(){
      dumpTruck.setSpeed(-.5);
      isDumped = false;
      DashboardPublisher.instance().putDriver("Dumped", isDumped);
  }
}