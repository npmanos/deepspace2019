package com.irontigers.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.RobotMap;
import com.irontigers.commands.TeleopDrive;
import edu.wpi.first.wpilibj.Spark;

public class DumpTruckSystem extends Subsystem {

  public interface DumpMethod{
    public void dump();
    private void unDump();
    public void changePos();

    private boolean isDown = false;
  }

  private static DumpTruckSystem instance = new DumpMethod();
  public static Spark dumpTruck = new Spark(RobotMap.DumpTruck.Spark);
 
  public static DumpTruckSystem instance(){
    return instance;
  }

  // Drive and controllers


  private DumpMethod dumpMethod;

  private DumpTruckSystem(){
      //don't forget to put stuff here
  }

  @Override
  public void initDefaultCommand(){
    // If there is no other ACTIVE command claiming a requirement on the DriveTrain then we will always
    // default to TelopDrive command

    setDefaultCommand(new TeleopDrive());
  }

  public void changePos(){

    if (isDown)
    {
        dumpMethod.unDump();
        
    }

    else 
    {
        dumpMethod.dump();
        
    }
    DashboardPublisher.instance().put("Dump Truck is down?", isDown);
  }

  public void dump(){
      dumpTruck.setSpeed(.5);
      isDown = true;
      
  }

  private void unDump(){
      dumpTruck.setSpeed(-.5);
      isDown = false;
  }
}
s