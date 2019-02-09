package com.irontigers.commands;

import com.irontigers.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleControlState extends Command {

  @Override
  protected void execute() {
    Robot.instance().toggleControlState();
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

}