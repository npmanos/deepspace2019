package com.irontigers.commands;

import com.irontigers.Robot;
import com.irontigers.subsystems.InvertibleSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleInvertedControl extends Command {

  @Override
  protected void execute() {
    // Call invert on all invertible systems
    for(InvertibleSystem system : Robot.INVERTIBLE_SYSTEMS){
      system.invert();
    }
  }

  @Override
  protected boolean isFinished() {
    // This should execute exactly once
    return true;
  }

}