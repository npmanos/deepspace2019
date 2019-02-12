/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.vision.VisionDriveX;
import com.irontigers.subsystems.vision.VisionDriveY;
import com.irontigers.subsystems.vision.VisionDriving;
import com.irontigers.subsystems.vision.VisionX;
import com.irontigers.subsystems.vision.VisionY;

public class PIDAlign extends Command {
  PIDController driveAlignX;
  PIDController driveAlignY;

  VisionX seeX;
  VisionY seeY;
  VisionDriveX driveX;
  VisionDriveY driveY;

  public PIDAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(DriveSystem.instance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    VisionDriving.instance().initialize(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    VisionDriving.instance().driving();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return VisionDriving.instance().isComplete();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    VisionDriving.instance().disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
