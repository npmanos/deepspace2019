package com.irontigers.commands;

import com.irontigers.subsystems.HatchManipSystem;

import edu.wpi.first.wpilibj.command.Command;

public class SpearStop extends Command {

  public SpearStop(){
    // TODO: add all systems this command will use
     requires(HatchManipSystem.instance());
  }

  @Override
  protected void execute() {
    HatchManipSystem.instance().hatchSpark.setSpeed(0);
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