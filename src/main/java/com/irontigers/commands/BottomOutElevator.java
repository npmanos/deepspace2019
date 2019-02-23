package com.irontigers.commands;

import com.irontigers.subsystems.DashboardPublisher;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class BottomOutElevator extends Command {

  private int goalPosition = 0;
  private double leeway = 300;

  public BottomOutElevator(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void execute() {
    ElevatorSystem.instance().move(-.5);
  }

  @Override
  protected boolean isFinished() {
    return ElevatorSystem.instance().bottomedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
    ElevatorSystem.instance().stop();
    DashboardPublisher.instance().putDriver("Elevator Level", 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}