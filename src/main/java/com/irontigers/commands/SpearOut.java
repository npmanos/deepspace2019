package com.irontigers.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.*;
import javax.swing.Timer;

public class SpearOut extends Command {
  Timer timer;
  public double i2;


  public SpearOut(){
    // TODO: add all systems this command will use
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
    DashboardPublisher.instance().putDriver("Extended", true);
    // TODO: if needs an end command, code it here
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
