/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers;

//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Joystick.AxisType;

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
    public static final int ID = 0;

    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS = 1;
    
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;

    public static final int LEFT_TRIGGER = 2;
    public static final int RIGHT_TRIGGER = 3;    

    // Controller buttons - apply for both driver and elevator controllers
    // public static int BUTTON_A = 1;
    // public static int BUTTON_B = 2;
    // public static int BUTTON_X = 3;
    // public static int BUTTON_Y = 4;
    // public static int LEFT_BUMPER = 5;
    // public static int RIGHT_BUMPER = 6;
    // public static int BACK = 7;
    // public static int START = 8;
    // public static int LEFT_JOYSTICK = 9;
    // public static int RIGHT_JOYSTICK = 10;

    // public static int LEFT_JOYSTICK_X = 0;
    // public static int LEFT_JOYSTICK_Y = 1;
    // public static int RIGHT_JOYSTICK_X = 4;
    // public static int RIGHT_JOYSTICK_Y = 5;
    // public static int LEFT_TRIGGER = 2;
    // public static int RIGHT_TRIGGER = 3;

    //dpad is excluded as it is buggy to the point of being unable to be used
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
}
