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
  private static CameraSystem instance = new CameraSystem();
  public static CameraSystem instance(){
    return instance;
  }

  public interface CameraStreamer{
    public void stream();
  }

  public static HttpCamera hatchCam = CameraServer.getInstance().addAxisCamera("Hatch Camera", RobotMap.Cameras.LIMELIGHT_URL);
  public static UsbCamera ballCam = CameraServer.getInstance().startAutomaticCapture("Ball Camera", RobotMap.Cameras.USB_CAMERA);
  public static VideoSink server = CameraServer.getInstance().getServer();

  private CameraStreamer camStreamer;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void enableStandardControl(){
    camStreamer = () -> server.setSource(hatchCam);
  }

  @Override
  public void enableInvertedControl(){
    camStreamer = () -> server.setSource(ballCam);
  }
}
