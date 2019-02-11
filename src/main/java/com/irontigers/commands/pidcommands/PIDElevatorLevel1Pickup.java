package com.irontigers.commands.pidcommands;

import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class PIDElevatorLevel1Pickup extends Command {

  private int goalPosition = 3500;

  public PIDElevatorLevel1Pickup(){
    requires(ElevatorSystem.instance());
  }

  @Override
  protected void initialize() {
    ElevatorSystem.instance().moveToPoint(goalPosition);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return ElevatorSystem.instance().atPoint();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
    ElevatorSystem.instance().stopPID();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}