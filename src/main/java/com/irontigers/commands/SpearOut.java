package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.irontigers.subsystems.*;

public class SpearOut extends Command {

  public SpearOut(){
    // TODO: add all systems this command will use
     requires(HatchManipSystem.instance());
  }

  @Override
  protected void execute() {
    HatchManipSystem.instance().extend();

  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return false;
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