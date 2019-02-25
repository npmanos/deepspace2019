package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ElevatorLevel2 extends Command {

  private int goalPosition = 37175;
  private double leeway = .01;

  public ElevatorLevel2() {
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    DashboardPublisher.instance().putDriver("Moving to position", true);
    Shuffleboard.addEventMarker("ElevatorLevel2 started", RobotMap.Dashboard.LOW);
  }

  @Override
  protected void execute() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());

    if (currentPosition > (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 + leeway)) {
      ElevatorSystem.instance().move(-.7);
    } else if (currentPosition < (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 - leeway)) {
      ElevatorSystem.instance().move(.7);
    } else {
      ElevatorSystem.instance().stop();
    }
  }

  @Override
  protected boolean isFinished() {
    double currentPosition = Math.abs(ElevatorSystem.instance().getRawPosition());
    double minPosition = (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 - leeway);
    double maxPosition = (goalPosition + ElevatorSystem.instance().getOffSet()) * (1 + leeway);
    boolean inRange = (currentPosition > minPosition) && (currentPosition < maxPosition);
    return (inRange || ElevatorSystem.instance().wrongWay());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    ElevatorSystem.instance().stop();
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