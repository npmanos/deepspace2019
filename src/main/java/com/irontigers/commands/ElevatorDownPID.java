package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorDownPID extends Command {

  public ElevatorDownPID(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    ElevatorSystem.instance().setAbsoluteTolerance(100);
    ElevatorSystem.instance().setSetpoint(0);
    ElevatorSystem.instance().enable();
    Shuffleboard.addEventMarker("ElevatorDown started", RobotMap.Dashboard.LOW);
    DashboardPublisher.instance().putDriver("Moving to position", true);
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return ElevatorSystem.instance().onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().disable();
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