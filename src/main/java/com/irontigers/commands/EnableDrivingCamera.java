package com.irontigers.commands;

import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class EnableDrivingCamera extends Command {

  public EnableDrivingCamera(){
  }

  @Override
  protected void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}