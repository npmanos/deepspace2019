/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.       Real Code + one change     This is practice                         */
/* the project.       Real Code + one change     coding practice              */
/*----------------------------------------------------------------------------*/

package com.irontigers;

import com.irontigers.subsystems.AlignmentSystem;
import com.irontigers.subsystems.DriverJoystick;
import com.irontigers.subsystems.LidarSystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import com.irontigers.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.Spark;

import com.irontigers.subsystems.DriverJoystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.irontigers.pulsedLightLIDAR;
//import com.irontigers.LidarLiteSensor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public WPI_TalonSRX talon;
  public static Spark spark = new Spark(0);

  // DriveSystem d = DriveSystem.instance();

  // private DriverStation.Alliance ourAlliance;
  // private Command automousCommand;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    //DriverJoystick.instance();
    //AlignmentSystem.instance();

    LidarSystem.instance();

    // pulsedLightLIDAR.pulsedLightLIDAR2();
    // talon = new WPI_TalonSRX(3);

    // private TalonSRX talon;
    // TODO: setup SmartDashboard options and choosers

    // We do not need to provide an option to select the TeleopDrive because it
    // is the default command for DriveSystem
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // // talon.set(.5);
    // pulsedLightLIDAR.start();

    // if (pulsedLightLIDAR.getDistanceIn() > 10){
    // spark.setSpeed(.5);
    // }
    // else {
    // talon.set(.5);
    // }
    // pulsedLightLIDAR.update();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // DriverJoystick joystick = DriverJoystick.instance();
    // DriveSystem.instance().drive(joystick.yScaledSpeed(),
    // joystick.xScaledSpeed(), joystick.zScaledRotation());
    // talon.set(0.2);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }

}
