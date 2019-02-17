package com.irontigers.commands;

import com.irontigers.subsystems.DumpTruckSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleDumpTruck extends Command {

  public ToggleDumpTruck(){
    requires(DumpTruckSystem.instance());
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