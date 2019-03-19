package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorLevel1PID extends Command {

  private int goalPosition = -1879;
  private double leeway = 2;
  private double offset = ElevatorSystem.instance().getOffSet();

  public ElevatorLevel1PID(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", true);
    Shuffleboard.addEventMarker("ElevatorLevel1 started", RobotMap.Dashboard.LOW);
    ElevatorSystem.instance().getPIDController().setPID(0.0002, 0.00001335, 0.0000181);
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
    Shuffleboard.addEventMarker("ElevatorLevel1 ended", RobotMap.Dashboard.LOW);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}