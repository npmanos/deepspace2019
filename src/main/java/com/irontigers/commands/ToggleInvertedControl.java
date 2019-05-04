package com.irontigers.commands;

import com.irontigers.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleInvertedControl extends Command {

  @Override
  protected void execute() {
    // Call invert on all invertible systems
    Robot.getInstance().toggleControlState();
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

}