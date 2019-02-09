package com.irontigers.commands;

import com.irontigers.Robot;
import com.irontigers.subsystems.DumpTruckSystem;

import edu.wpi.first.wpilibj.command.Command;

public class EnableStandardControl extends Command {

  @Override
  protected void execute() {
    DumpTruckSystem.instance().changePos();
    }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

}