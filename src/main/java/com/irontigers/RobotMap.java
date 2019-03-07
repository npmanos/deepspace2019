/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers;

import edu.wpi.cscore.VideoSource;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;

/**
/ * RobotMap statically declares the ports / channels used by various pieces of
/ * hardware (sensors, actuators, etc...) connected to the robot. If a physical
/ * connection location is changed then this is the place where a value will
/ * need to be updated. All code making use of these hardware items must use
/ * the variables here to refer to the port / channel.
/ *
/ * https://www.chiefdelphi.com/forums/showpost.php?p=1003245&postcount=8
/ */
public class RobotMap {

  public static class XBoxController {
    public static final int DRIVER_ID = 0;
    public static final int NAVIGATOR_ID = 1;

    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS = 1;
    
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;

    public static final int LEFT_TRIGGER = 2;
    public static final int RIGHT_TRIGGER = 3;    

    public static int LEFT_BUMPER = 5;
    public static int RIGHT_BUMPER = 6;
    
    public static int A_BUTTON = 1;
    public static int B_BUTTON = 2;
    public static int X_BUTTON = 3;
    public static int Y_BUTTON = 4;
    public static int BACK = 7;
    public static int START = 8;
//    public static int LEFT_JOYSTICK = 9;
    public static int RIGHT_AXIS_BUTTON = 10;
    public static int LEFT_AXIS_BUTTON = 9;
    //dpad is excluded as it is buggy to the point of being unable to be used
  }
  public static class Manipulators{
    public static int ELEVATOR = 5;
  }
  public static class DriveTrain {
    // drive train motors
    public static int LEFT_FRONT = 4;
    public static int LEFT_BACK = 1;

    public static int RIGHT_FRONT = 3;
    public static int RIGHT_BACK = 2;

    //drive train rotational encoders
    // public static int LEFT_ENCODER_A = 2;
    // public static int LEFT_ENCODER_B = 3;
  }

  public static class Navigation {
    // public static SPI.Port NAVIGATION_BUS = SPI.Port.kMXP;
  }

  public static class Cameras{
    public static int BALL_CAMERA = 0;
    public static String LIMELIGHT_URL = "limelight.local:5800";

    public static ConnectionStrategy KEEP_OPEN = VideoSource.ConnectionStrategy.kKeepOpen;
  }

  public static class Constants {
		//Greyhill 63R encoder ticks
		// public static int Greyhill128 = 128;
		// public static int Greyhill256 = 256;
		
		//Wheel/pulley diameters
		// public static double DTWheelDia = 6.5;
		// public static double PulleyPDia = 1.128;
		
		//Gear ratios
		// public static double DTGearing = 9.4697;
		// public static double ElevatorGearing = 21.3068;
		// public static double EncoderGearing = 3; //Elevator encoder uses VEX Ball-Shifter Hardware kit for hookup, has a 3:1 speed increase
		
		//PID Values for Drivetrain
		// public static double P = .12;
		// public static double I = .012;
		// public static double D = 0;
		// public static double TOL = .1;
	}
  public static class HatchManip {
    public static int Spark = 1;
  }
  public static class DumpTruck {
     public static int Spark = 0;
  }

  public static class Elevator{
    public static int LEVEL_1 = 1879;
    public static int LEVEL_2 = 37175;
    public static int MAX_HEIGHT = 66799;

    public static int OFFSET = 1499;
  }

  public static class Dashboard{
    public static String DRIVER_PREFIX = "Driver Dashboard/";
    public static String DEBUG_PREFIX = "debug/";

    public static String PRACTICE_FORMAT = "PRACTICE-${date}-${time}";
    public static String MATCH_FORMAT = matchFormat();

    /**
     * Critical importance. 
     * 
     * Use for when things go wrong in a bigly way (e.g. The elevator has wound backwards)
     */
    public static EventImportance CRITICAL = EventImportance.kCritical;

    /**
     * High importance. 
     * 
     * Use for when a command that generally should not have to be interreputed was
     */
    public static EventImportance HIGH = EventImportance.kHigh;

    /**
     * Normal importance. 
     * 
     * Use for when a routine thing occurs that would be useful to note in a recording 
     * 
     * e.g. Match changes mode
     */
    public static EventImportance NORMAL = EventImportance.kNormal;

    /**
     * Low importance. 
     * 
     * Use for when an important command begins or ends
     */
    public static EventImportance LOW = EventImportance.kLow;

    /**
     * Trivial importance
     * 
     * Use for when something common and unlikely to be useful for debugging occurs
     */
    public static EventImportance TRIVIAL = EventImportance.kTrivial;
  
    private static String matchFormat(){
      StringBuffer sb = new StringBuffer();

      sb.append(DriverStation.getInstance().getEventName());
      sb.append("-");
      sb.append(DriverStation.getInstance().getMatchType().toString());
      sb.append("-");
      sb.append(DriverStation.getInstance().getMatchNumber());
      sb.append("-");
      sb.append("${time}");

      return sb.toString();
    }
  }
}
