package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorLevel2PID extends Command {

  private int goalPosition = -37175;
  private double leeway = 1;
  private int offset = ElevatorSystem.instance().getOffSet();

  public ElevatorLevel2PID() {
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", true);
    Shuffleboard.addEventMarker("ElevatorLevel2 started", RobotMap.Dashboard.LOW);
    ElevatorSystem.instance().getPIDController().setPID(0.0005, 0.0000333522835, 0.0000457371357);
    ElevatorSystem.instance().setPercentTolerance(leeway);
    ElevatorSystem.instance().setSetpoint(goalPosition - offset);
    ElevatorSystem.instance().enable();
  }

  @Override
  protected boolean isFinished() {
    return ElevatorSystem.instance().onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().disable();
    DashboardPublisher.instance().putDriver("Moving to position", false);
    Shuffleboard.addEventMarker("ElevatorLevel2 ended", RobotMap.Dashboard.LOW);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}