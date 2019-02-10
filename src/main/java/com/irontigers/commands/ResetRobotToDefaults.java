package com.irontigers.commands;

import com.irontigers.subsystems.DriveSystem;
import com.irontigers.subsystems.DumpTruckSystem;
import com.irontigers.subsystems.ElevatorSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ResetRobotToDefaults extends Command {

  public ResetRobotToDefaults(){
    requires(ElevatorSystem.instance());
    requires(DumpTruckSystem.instance());
    requires(DriveSystem.instance());
  }

  @Override
  protected void execute() {
    DriveSystem.instance().enableStandardControl();
    DumpTruckSystem.instance().dump();
    ElevatorSystem.instance().resetElevator();
    DumpTruckSystem.instance().unDump();
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // TODO: if needs an end command, code it here
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}