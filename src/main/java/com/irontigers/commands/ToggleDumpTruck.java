package com.irontigers.commands;

import com.irontigers.RobotMap;
import com.irontigers.subsystems.DumpTruckSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class ToggleDumpTruck extends Command {

  public ToggleDumpTruck(){
    requires(DumpTruckSystem.instance());
  }

  @Override
  protected void initialize() {
    Shuffleboard.addEventMarker("Dump truck toggled", RobotMap.Dashboard.TRIVIAL);
  }

  @Override
  protected void execute() {
    DumpTruckSystem.instance().invert();
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

}