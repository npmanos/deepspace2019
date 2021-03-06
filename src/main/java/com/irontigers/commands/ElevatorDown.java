package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorDown extends Command {

  public ElevatorDown(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    Shuffleboard.addEventMarker("ElevatorDown started", RobotMap.Dashboard.LOW);
    DashboardPublisher.instance().putDriver("Moving to position", true);
  }

  @Override
  protected void execute() {
    ElevatorSystem.instance().move(-.65);
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return ElevatorSystem.instance().isLowerLimitSwitch();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Moving to position", false);
    Shuffleboard.addEventMarker("ElevatorDown ended", RobotMap.Dashboard.LOW);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}