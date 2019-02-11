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
    seeX = new VisionX();
    driveAlignX = new PIDController(1, 0, 0, seeX, driveX);
    driveAlignY = new PIDController(1, 0, 0, seeY, driveY);
    driveAlignX.reset();
    driveAlignY.reset();
    driveAlignX.setContinuous(false);
    driveAlignY.setContinuous(false);
    driveAlignX.setOutputRange(-.35, .35);
    driveAlignY.setOutputRange(-.35, .35);
    driveAlignX.setPercentTolerance(1);
    driveAlignY.setPercentTolerance(1);
    driveAlignX.setSetpoint(0);
    driveAlignY.setSetpoint(0);
    driveAlignX.enable();
    driveAlignY.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    VisionDriving.instance().driving();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return driveAlignX.onTarget() && driveAlignY.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    driveAlignX.disable();
    VisionDriving.instance().setX(0);
    driveAlignY.disable();
    VisionDriving.instance().setY(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
