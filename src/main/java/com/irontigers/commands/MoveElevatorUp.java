package com.irontigers.commands;

import com.irontigers.Robot;
import com.irontigers.subsystems.ElevatorSystem;
import com.irontigers.subsystems.InvertibleSystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveElevatorUp extends Command {

  public MoveElevatorUp() {
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    // Call invert on all invertible systems
    ElevatorSystem.instance().elevate(.65);
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
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