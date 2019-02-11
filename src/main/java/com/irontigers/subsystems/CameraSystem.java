/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CameraSystem extends Subsystem implements InvertibleSystem {
  private static CameraSystem instance = new CameraSystem();
  public static CameraSystem instance(){
    return instance;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void streamCamera(){

  }

  @Override
  public void enableStandardControl(){

  }

  @Override
  public void enableInvertedControl(){
    
  }
}
