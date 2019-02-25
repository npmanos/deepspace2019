package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.HatchManipSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class SpearIn extends Command {
  public SpearIn(){
     requires(HatchManipSystem.instance());
  }

  @Override
  protected void execute() {
    HatchManipSystem.instance().retract();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DashboardPublisher.instance().putDriver("Extended", false);
    Shuffleboard.addEventMarker("Spear retracted", RobotMap.Dashboard.TRIVIAL);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}