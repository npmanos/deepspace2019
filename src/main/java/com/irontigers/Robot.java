/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.       Real Code + one change     This is practice                         */
/* the project.       Real Code + one change     coding practice              */
/*----------------------------------------------------------------------------*/

package com.irontigers;

import com.irontigers.subsystems.CameraSystem;
import com.irontigers.commands.SpearOut;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.DriverController;
import com.irontigers.subsystems.DumpTruckSystem;
import com.irontigers.subsystems.ElevatorSystem;
import com.irontigers.subsystems.InvertibleSystem;
import com.irontigers.subsystems.NavigatorController;
import com.irontigers.subsystems.HatchManipSystem;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public enum ControlState {
    STANDARD,
    INVERTED
  }

  // Add to here any subsystems that should be inverted when the driver
  // decides to invert the robot controls
  private ControlState controlState = ControlState.STANDARD;
  private static InvertibleSystem[] INVERTIBLE_SYSTEMS = new InvertibleSystem[]{
    DriveSystem.instance(),
    CameraSystem.instance()
  };

  private static Robot instance;
  public static Robot instance(){
    return instance;
  }

  // private DriverStation.Alliance ourAlliance;
  // private Command automousCommand;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    instance = this;


    DriveSystem.instance();
    CameraSystem.instance();

    CameraSystem.instance().hatchCam.setConnectionStrategy(RobotMap.Cameras.KEEP_OPEN);
    CameraSystem.instance().ballCam.setConnectionStrategy(RobotMap.Cameras.KEEP_OPEN);
    DashboardPublisher.instance();
    DriverController.instance();
    DriveSystem.instance();
    DumpTruckSystem.instance();
    ElevatorSystem.instance();
    NavigatorController.instance();
    
    enableStandardControl();

    // We do not need to provide an option to select the TeleopDrive because it
    // is the default command for DriveSystem
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    DashboardPublisher.instance().putDebug("Camera Source", CameraSystem.instance().getCurrentSource());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    if(DriverStation.getInstance().getMatchType() == MatchType.Practice){
      Shuffleboard.setRecordingFileNameFormat(RobotMap.Dashboard.PRACTICE_FORMAT);
      Shuffleboard.startRecording();
    }else if(DriverStation.getInstance().isFMSAttached()){
      Shuffleboard.setRecordingFileNameFormat(RobotMap.Dashboard.MATCH_FORMAT);
      Shuffleboard.startRecording();
    }
    Shuffleboard.addEventMarker("Match Start", EventImportance.kNormal);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();

  }

  @Override
  public void teleopInit() {
    Shuffleboard.addEventMarker("Teleop Start", EventImportance.kNormal);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
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

  public void toggleControlState(){
    switch(controlState){
      case STANDARD:
        enableInvertedControl();
        break;
      case INVERTED:
      default:
        enableStandardControl();
        break;
    }
  }

  public void enableStandardControl(){
    controlState = ControlState.STANDARD;
    for(InvertibleSystem system : INVERTIBLE_SYSTEMS){
      system.enableStandardControl();
    }

    DashboardPublisher.instance().putDebug("Control State", controlState.toString());
  }

  public void enableInvertedControl(){
    controlState = ControlState.INVERTED;
    for(InvertibleSystem system : INVERTIBLE_SYSTEMS){
      system.enableInvertedControl();
    }

    
    DashboardPublisher.instance().putDebug("Control State", controlState.toString());
  }

  @Override
  public void disabledInit() {
    Shuffleboard.addEventMarker("Match End", EventImportance.kNormal);
    Shuffleboard.stopRecording();
  }
  
  @Override
  public void disabledPeriodic(){

  }
}
