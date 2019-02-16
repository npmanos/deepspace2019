package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ResetElevatorToDefault extends Command {

  public ResetElevatorToDefault(){
    // TODO: add all systems this command will use
    // requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    // TODO: implement
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
