package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.*;
import javax.swing.Timer;

public class SpearOut extends Command {
  public double i2;


  public SpearOut(){
     requires(HatchManipSystem.instance());
      
  }

  @Override
  protected void initialize() {
    super.setTimeout(.5);
    Shuffleboard.addEventMarker("Spear out and drop started", RobotMap.Dashboard.TRIVIAL);
  }

  @Override
  protected void execute() {
    HatchManipSystem.instance().extend();
  }

  @Override
  protected boolean isFinished() {
    return super.isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    HatchManipSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Extended", true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
