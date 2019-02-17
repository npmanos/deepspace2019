package com.irontigers.commands;

import com.irontigers.subsystems.ElevatorSystem;
import com.irontigers.subsystems.HatchManipSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorUp extends Command {

  public ElevatorUp() {
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    ElevatorSystem.instance().move(.65);
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}