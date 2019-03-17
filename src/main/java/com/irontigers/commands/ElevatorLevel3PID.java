package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorLevel3PID extends Command {

  private int goalPosition = -RobotMap.Elevator.MAX_HEIGHT;
  private double leeway = .1;
  private int offset = ElevatorSystem.instance().getOffSet();

  public ElevatorLevel3PID() {
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", true);
    Shuffleboard.addEventMarker("ElevatorLevel3 started", RobotMap.Dashboard.LOW);
    ElevatorSystem.instance().setPercentTolerance(leeway);
    ElevatorSystem.instance().setSetpoint(goalPosition - offset);
    ElevatorSystem.instance().enable();
  }

  @Override
  protected boolean isFinished() {
    return ElevatorSystem.instance().onTarget(); //|| ElevatorSystem.instance().isUpperLimitSwitch();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().disable();
    DashboardPublisher.instance().putDriver("Moving to position", false);
    Shuffleboard.addEventMarker("ElevatorLevel3 ended", RobotMap.Dashboard.LOW);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}