/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import com.irontigers.RobotMap;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CameraSystem extends Subsystem implements InvertibleSystem {
  public HttpCamera limelight = CameraServer.getInstance().addAxisCamera("Vision Tracking Camera", RobotMap.Cameras.LIMELIGHT_URL);
  public UsbCamera hatchCam = CameraServer.getInstance().startAutomaticCapture("Hatch Camera", 1);
  public UsbCamera ballCam = CameraServer.getInstance().startAutomaticCapture("Ball Camera", 0);
  private VideoSink server = CameraServer.getInstance().addSwitchedCamera("Camera Stream");

  private static CameraSystem instance = new CameraSystem();
  public static CameraSystem instance(){
    // hatchCam[0] = RobotMap.Cameras.LIMELIGHT_URL;
    // ballCam[0] = "http://roboRIO-4176-FRC.local:1181/?action=stream";
    // ballCam[1] = "http://10.41.76.2:1181/?action=stream";
    return instance;
  }

  // private static String[] hatchCam = new String[1];
  // private static String[] ballCam = new String[2];

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public String getCurrentSource(){
    return server.getSource().getName();
  }

  @Override
  public void enableStandardControl(){
    server.setSource(hatchCam);
    DashboardPublisher.instance().putDriver("Camera", "Hatch Camera");
  }

  @Override
  public void enableInvertedControl(){
    server.setSource(ballCam);
    DashboardPublisher.instance().putDriver("Camera", "Ball Camera");
  }
}
