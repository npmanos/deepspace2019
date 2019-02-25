/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;
import com.irontigers.subsystems.NavigatorController;

public class ElevatorManualControl extends Command {
  public ElevatorManualControl() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(ElevatorSystem.instance());
   }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", false);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    ElevatorSystem.instance().move(NavigatorController.instance().elevatorSpeed());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
